
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](_display_(/*2.2*/main("VisualPython")/*2.22*/ {_display_(Seq[Any](format.raw/*2.24*/("""
    """),format.raw/*3.5*/("""<section class="hero">
        <h1>VisualPython</h1>
        <p class="tagline">Visualize and explore Python code in your browser.</p>
        <a href="#" class="btn">Get Started</a>
    </section>

    <section class="features">
        <div class="feature">
            <h2>Interactive</h2>
            <p>Run and visualize Python snippets instantly.</p>
        </div>
        <div class="feature">
            <h2>Fast</h2>
            <p>Powered by a high-performance Scala backend.</p>
        </div>
        <div class="feature">
            <h2>Open</h2>
            <p>Built on open web standards.</p>
        </div>
    </section>
""")))}),format.raw/*23.2*/("""
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/index.scala.html
                  HASH: 68bf2e7abdde8fc2ccdf12f571d357c1e8b70470
                  MATRIX: 722->1|818->5|846->25|885->27|916->32|1588->674
                  LINES: 21->1|26->2|26->2|26->2|27->3|47->23
                  -- GENERATED --
              */
          