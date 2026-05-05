
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
"""),format.raw/*3.1*/("""<div class="debugger">
    <div class="panel code-panel">
        <label for="code-input">Python Program</label>
        <textarea id="code-input" spellcheck="false" placeholder="# Enter your Python program here&#10;x = 1&#10;y = x + 2&#10;print(y)"></textarea>
        <div class="btn-row">
            <button id="btn-run" class="btn">Run</button>
            <button id="btn-step" class="btn btn-secondary" disabled>Next Line</button>
        </div>
        <div id="status" class="status"></div>
    </div>

    <div class="panel memory-panel">
        <label>Program Memory</label>
        <div id="line-indicator" class="line-indicator hidden">Line: <span id="line-number">—</span></div>
        <pre id="memory-display" class="memory-display">—</pre>
    </div>
</div>

<script>
(function () """),format.raw/*22.14*/("""{"""),format.raw/*22.15*/("""
    """),format.raw/*23.5*/("""const codeInput   = document.getElementById('code-input');
    const btnRun      = document.getElementById('btn-run');
    const btnStep     = document.getElementById('btn-step');
    const statusEl    = document.getElementById('status');
    const lineNum     = document.getElementById('line-number');
    const lineInd     = document.getElementById('line-indicator');
    const memDisplay  = document.getElementById('memory-display');

    function setStatus(msg, isError) """),format.raw/*31.38*/("""{"""),format.raw/*31.39*/("""
        """),format.raw/*32.9*/("""statusEl.textContent = msg;
        statusEl.className = 'status' + (isError ? ' status-error' : ' status-ok');
    """),format.raw/*34.5*/("""}"""),format.raw/*34.6*/("""

    """),format.raw/*36.5*/("""function renderState(data) """),format.raw/*36.32*/("""{"""),format.raw/*36.33*/("""
        """),format.raw/*37.9*/("""if (data.done) """),format.raw/*37.24*/("""{"""),format.raw/*37.25*/("""
            """),format.raw/*38.13*/("""lineInd.classList.add('hidden');
            lineNum.textContent = '—';
            memDisplay.textContent = '(program finished)';
            btnStep.disabled = true;
            setStatus('Program finished.', false);
            return;
        """),format.raw/*44.9*/("""}"""),format.raw/*44.10*/("""
        """),format.raw/*45.9*/("""lineInd.classList.remove('hidden');
        lineNum.textContent = data.lineNumber;
        memDisplay.textContent = JSON.stringify(data.locals, null, 2);
        btnStep.disabled = false;
        setStatus('Paused at line ' + data.lineNumber + '.', false);
    """),format.raw/*50.5*/("""}"""),format.raw/*50.6*/("""

    """),format.raw/*52.5*/("""async function fetchJSON(url, options) """),format.raw/*52.44*/("""{"""),format.raw/*52.45*/("""
        """),format.raw/*53.9*/("""const res = await fetch(url, options);
        const text = await res.text();
        let data;
        try """),format.raw/*56.13*/("""{"""),format.raw/*56.14*/("""
            """),format.raw/*57.13*/("""data = JSON.parse(text);
        """),format.raw/*58.9*/("""}"""),format.raw/*58.10*/(""" """),format.raw/*58.11*/("""catch (_) """),format.raw/*58.21*/("""{"""),format.raw/*58.22*/("""
            """),format.raw/*59.13*/("""throw new Error('Server returned non-JSON response (status ' + res.status + '). Check server logs.');
        """),format.raw/*60.9*/("""}"""),format.raw/*60.10*/("""
        """),format.raw/*61.9*/("""if (data.error) throw new Error(data.error);
        return data;
    """),format.raw/*63.5*/("""}"""),format.raw/*63.6*/("""

    """),format.raw/*65.5*/("""btnRun.addEventListener('click', async function () """),format.raw/*65.56*/("""{"""),format.raw/*65.57*/("""
        """),format.raw/*66.9*/("""const code = codeInput.value.trim();
        if (!code) """),format.raw/*67.20*/("""{"""),format.raw/*67.21*/(""" """),format.raw/*67.22*/("""setStatus('Please enter a Python program.', true); return; """),format.raw/*67.81*/("""}"""),format.raw/*67.82*/("""

        """),format.raw/*69.9*/("""btnRun.disabled = true;
        btnStep.disabled = true;
        setStatus('Starting…', false);
        memDisplay.textContent = '—';
        lineInd.classList.add('hidden');

        try """),format.raw/*75.13*/("""{"""),format.raw/*75.14*/("""
            """),format.raw/*76.13*/("""const data = await fetchJSON('/start', """),format.raw/*76.52*/("""{"""),format.raw/*76.53*/("""
                """),format.raw/*77.17*/("""method: 'POST',
                headers: """),format.raw/*78.26*/("""{"""),format.raw/*78.27*/(""" """),format.raw/*78.28*/("""'Content-Type': 'text/plain' """),format.raw/*78.57*/("""}"""),format.raw/*78.58*/(""",
                body: code
            """),format.raw/*80.13*/("""}"""),format.raw/*80.14*/(""");
            renderState(data);
        """),format.raw/*82.9*/("""}"""),format.raw/*82.10*/(""" """),format.raw/*82.11*/("""catch (e) """),format.raw/*82.21*/("""{"""),format.raw/*82.22*/("""
            """),format.raw/*83.13*/("""setStatus('Error: ' + e.message, true);
        """),format.raw/*84.9*/("""}"""),format.raw/*84.10*/(""" """),format.raw/*84.11*/("""finally """),format.raw/*84.19*/("""{"""),format.raw/*84.20*/("""
            """),format.raw/*85.13*/("""btnRun.disabled = false;
        """),format.raw/*86.9*/("""}"""),format.raw/*86.10*/("""
    """),format.raw/*87.5*/("""}"""),format.raw/*87.6*/(""");

    btnStep.addEventListener('click', async function () """),format.raw/*89.57*/("""{"""),format.raw/*89.58*/("""
        """),format.raw/*90.9*/("""btnStep.disabled = true;
        setStatus('Stepping…', false);

        try """),format.raw/*93.13*/("""{"""),format.raw/*93.14*/("""
            """),format.raw/*94.13*/("""const data = await fetchJSON('/step');
            renderState(data);
        """),format.raw/*96.9*/("""}"""),format.raw/*96.10*/(""" """),format.raw/*96.11*/("""catch (e) """),format.raw/*96.21*/("""{"""),format.raw/*96.22*/("""
            """),format.raw/*97.13*/("""setStatus('Error: ' + e.message, true);
        """),format.raw/*98.9*/("""}"""),format.raw/*98.10*/("""
    """),format.raw/*99.5*/("""}"""),format.raw/*99.6*/(""");
"""),format.raw/*100.1*/("""}"""),format.raw/*100.2*/(""")();
</script>
""")))}),format.raw/*102.2*/("""
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
                  HASH: 9c6f3797fc337e24fe18a0520f7a1f46993d7ee3
                  MATRIX: 722->1|818->5|846->25|885->27|912->28|1739->827|1768->828|1800->833|2303->1308|2332->1309|2368->1318|2511->1434|2539->1435|2572->1441|2627->1468|2656->1469|2692->1478|2735->1493|2764->1494|2805->1507|3079->1754|3108->1755|3144->1764|3432->2025|3460->2026|3493->2032|3560->2071|3589->2072|3625->2081|3761->2189|3790->2190|3831->2203|3891->2236|3920->2237|3949->2238|3987->2248|4016->2249|4057->2262|4194->2372|4223->2373|4259->2382|4356->2452|4384->2453|4417->2459|4496->2510|4525->2511|4561->2520|4645->2576|4674->2577|4703->2578|4790->2637|4819->2638|4856->2648|5072->2836|5101->2837|5142->2850|5209->2889|5238->2890|5283->2907|5352->2948|5381->2949|5410->2950|5467->2979|5496->2980|5565->3021|5594->3022|5663->3064|5692->3065|5721->3066|5759->3076|5788->3077|5829->3090|5904->3138|5933->3139|5962->3140|5998->3148|6027->3149|6068->3162|6128->3195|6157->3196|6189->3201|6217->3202|6305->3262|6334->3263|6370->3272|6475->3349|6504->3350|6545->3363|6650->3441|6679->3442|6708->3443|6746->3453|6775->3454|6816->3467|6891->3515|6920->3516|6952->3521|6980->3522|7011->3525|7040->3526|7087->3542
                  LINES: 21->1|26->2|26->2|26->2|27->3|46->22|46->22|47->23|55->31|55->31|56->32|58->34|58->34|60->36|60->36|60->36|61->37|61->37|61->37|62->38|68->44|68->44|69->45|74->50|74->50|76->52|76->52|76->52|77->53|80->56|80->56|81->57|82->58|82->58|82->58|82->58|82->58|83->59|84->60|84->60|85->61|87->63|87->63|89->65|89->65|89->65|90->66|91->67|91->67|91->67|91->67|91->67|93->69|99->75|99->75|100->76|100->76|100->76|101->77|102->78|102->78|102->78|102->78|102->78|104->80|104->80|106->82|106->82|106->82|106->82|106->82|107->83|108->84|108->84|108->84|108->84|108->84|109->85|110->86|110->86|111->87|111->87|113->89|113->89|114->90|117->93|117->93|118->94|120->96|120->96|120->96|120->96|120->96|121->97|122->98|122->98|123->99|123->99|124->100|124->100|126->102
                  -- GENERATED --
              */
          