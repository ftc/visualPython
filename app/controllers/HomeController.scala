package controllers

import com.example.DebuggerClient
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import javax.inject._
import play.api.libs.json._
import play.api.mvc._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

  @volatile private var activeClient: Option[DebuggerClient] = None

  def index: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index())
  }

  /** POST /start  — body: plain-text Python script content */
  def startDebugger: Action[String] = Action(parse.text) { implicit request =>
    try {
      activeClient.foreach(_.close())
      activeClient = None

      val tmp = Files.createTempFile("pyscript", ".py")
      tmp.toFile.deleteOnExit()
      Files.write(tmp, request.body.getBytes(StandardCharsets.UTF_8))

      val client = new DebuggerClient(tmp.toAbsolutePath.toString)
      activeClient = Some(client)

      client.start() match {
        case Some(state) =>
          Ok(Json.obj("lineNumber" -> state.lineNumber, "locals" -> state.locals))
        case None =>
          Ok(Json.obj("done" -> true))
      }
    } catch {
      case e: Exception =>
        InternalServerError(Json.obj("error" -> e.getMessage))
    }
  }

  /** GET /step — advance one line; returns locals + line number, or done */
  def stepDebugger: Action[AnyContent] = Action { implicit request =>
    activeClient match {
      case None =>
        BadRequest(Json.obj("error" -> "No active debug session. POST /start first."))
      case Some(client) =>
        try {
          client.step() match {
            case Some(state) =>
              Ok(Json.obj("lineNumber" -> state.lineNumber, "locals" -> state.locals))
            case None =>
              Ok(Json.obj("done" -> true))
          }
        } catch {
          case e: Exception =>
            InternalServerError(Json.obj("error" -> e.getMessage))
        }
    }
  }
}
