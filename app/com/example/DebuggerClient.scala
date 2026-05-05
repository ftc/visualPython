package com.example

import java.io.{BufferedReader, BufferedWriter, InputStreamReader, OutputStreamWriter}
import java.net.Socket
import play.api.libs.json._

case class DebuggerState(locals: JsObject, lineNumber: Int)

/**
 * Controls a Python script via the Python debugger.
 *
 * Launches debugger_server.py as a subprocess, connects to it over TCP,
 * and exposes start / step operations.
 *
 * @param scriptPath absolute path to the Python script to debug
 * @param serverScript path to debugger_server.py (defaults to project root)
 */
class DebuggerClient(
    scriptPath: String,
    serverScript: String = s"${System.getProperty("user.dir")}/debugger_server.py"
) extends AutoCloseable {

  private val process: Process =
    new ProcessBuilder("python3", serverScript)
      .inheritIO()
      .redirectOutput(ProcessBuilder.Redirect.PIPE)
      .start()

  private val port: Int = {
    val stdout = new BufferedReader(new InputStreamReader(process.getInputStream))
    stdout.readLine().trim.toInt
  }

  private val socket = new Socket("127.0.0.1", port)
  private val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
  private val writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))

  /**
   * Runs the script in debug mode and stops before the first line executes.
   *
   * @return state at the first line, or None if the script has no stoppable lines
   */
  def start(): Option[DebuggerState] =
    send(Json.obj("command" -> "start", "path" -> scriptPath))

  /**
   * Advances one source line.
   *
   * @return state at the new line, or None when the script finishes
   */
  def step(): Option[DebuggerState] =
    send(Json.obj("command" -> "step"))

  override def close(): Unit = {
    scala.util.Try(socket.close())
    scala.util.Try(process.destroy())
  }

  // --- internals ---

  private def send(req: JsObject): Option[DebuggerState] = {
    writer.write(Json.stringify(req))
    writer.write("\n")
    writer.flush()

    val resp = Json.parse(reader.readLine()).as[JsObject]

    (resp \ "error").asOpt[String].foreach { msg =>
      throw new RuntimeException(s"Python debugger error: $msg")
    }

    (resp \ "locals").toOption match {
      case Some(JsNull) => None
      case Some(obj: JsObject) =>
        val lineno = (resp \ "lineno").as[Int]
        Some(DebuggerState(obj, lineno))
      case _ => None
    }
  }
}
