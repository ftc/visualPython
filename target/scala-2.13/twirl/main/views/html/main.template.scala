
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>"""),_display_(/*7.13*/title),format.raw/*7.18*/("""</title>
    <link rel="stylesheet" href=""""),_display_(/*8.35*/routes/*8.41*/.Assets.versioned("stylesheets/main.css")),format.raw/*8.82*/("""">
</head>
<body>
    <header>
        <nav>
            <a href=""""),_display_(/*13.23*/routes/*13.29*/.HomeController.index),format.raw/*13.50*/("""" class="brand">VisualPython</a>
        </nav>
    </header>
    <main>
        """),_display_(/*17.10*/content),format.raw/*17.17*/("""
    """),format.raw/*18.5*/("""</main>
    <footer>
        <p>VisualPython &mdash; Built with Scala Play</p>
    </footer>
</body>
</html>
"""))
      }
    }
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/main.scala.html
                  HASH: 72a8dedd802380e782e36fc320403e5f8d6b09a2
                  MATRIX: 733->1|857->32|1037->186|1062->191|1131->234|1145->240|1206->281|1300->348|1315->354|1357->375|1466->457|1494->464|1526->469
                  LINES: 21->1|26->2|31->7|31->7|32->8|32->8|32->8|37->13|37->13|37->13|41->17|41->17|42->18
                  -- GENERATED --
              */
          