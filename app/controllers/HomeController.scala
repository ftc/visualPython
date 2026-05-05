package controllers

import com.example.DebuggerClient
import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.FutureConverters._

@Singleton
class HomeController @Inject()(
  val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext) extends BaseController {

  @volatile private var activeClient: Option[DebuggerClient] = None

  private val httpClient = HttpClient.newHttpClient()

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

  /** POST /ask — body: JSON with query, code, lineNumber, locals, model */
  def ask: Action[JsValue] = Action(parse.json).async { implicit request =>
    val body       = request.body
    val query      = (body \ "query").asOpt[String].getOrElse("")
    val code       = (body \ "code").asOpt[String].getOrElse("")
    val lineNumber = (body \ "lineNumber").asOpt[Int]
    val locals     = (body \ "locals").asOpt[JsObject]
    val model      = (body \ "model").asOpt[String].getOrElse("llama3.2")

    val stateParts = Seq(
      lineNumber.map(ln => s"Currently paused at line $ln."),
      locals.map(l => s"Variables in scope: ${Json.prettyPrint(l)}")
    ).flatten

    val stateSection = if (stateParts.nonEmpty)
      s"\nExecution state:\n${stateParts.mkString("\n")}"
    else ""

    val prompt =
      s"""You are a Python debugging assistant. Answer questions about the code and its current execution state.
         |
         |Code:
         |```python
         |$code
         |```$stateSection
         |
         |Question: $query""".stripMargin

    val payload = Json.stringify(
      Json.obj("model" -> model, "prompt" -> prompt, "stream" -> false)
    )

    val httpRequest = HttpRequest.newBuilder()
      .uri(URI.create("http://localhost:11434/api/generate"))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(payload))
      .build()

    httpClient.sendAsync(httpRequest, BodyHandlers.ofString()).asScala
      .map { response =>
        val json = Json.parse(response.body())
        (json \ "response").asOpt[String] match {
          case Some(text) => Ok(Json.obj("response" -> text))
          case None =>
            val errMsg = (json \ "error").asOpt[String]
              .getOrElse(s"Unexpected response from Ollama: ${response.body()}")
            InternalServerError(Json.obj("error" -> errMsg))
        }
      }
      .recover { case e: Exception =>
        InternalServerError(Json.obj("error" -> s"Could not reach Ollama: ${e.getMessage}"))
      }
  }
}
