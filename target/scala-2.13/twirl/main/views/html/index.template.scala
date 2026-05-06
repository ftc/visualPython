
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


Seq[Any](_display_(/*2.2*/main("AI Debugger")/*2.21*/ {_display_(Seq[Any](format.raw/*2.23*/("""
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

<div class="panel ask-panel">
    <label for="ask-input">Explain This Step</label>
    <div class="ask-row">
        <input id="ask-input" type="text" placeholder="e.g. Why is x equal to 3 here?" />
        <input id="model-input" type="text" placeholder="Model (llama3.2)" style="width:140px" />
        <button id="btn-ask" class="btn">Ask</button>
    </div>
    <pre id="ask-response" class="memory-display ask-response">—</pre>
</div>

<script>
(function () """),format.raw/*32.14*/("""{"""),format.raw/*32.15*/("""
    """),format.raw/*33.5*/("""const codeInput   = document.getElementById('code-input');
    const btnRun      = document.getElementById('btn-run');
    const btnStep     = document.getElementById('btn-step');
    const statusEl    = document.getElementById('status');
    const lineNum     = document.getElementById('line-number');
    const lineInd     = document.getElementById('line-indicator');
    const memDisplay  = document.getElementById('memory-display');
    const askInput    = document.getElementById('ask-input');
    const modelInput  = document.getElementById('model-input');
    const btnAsk      = document.getElementById('btn-ask');
    const askResponse = document.getElementById('ask-response');

    let currentState  = null;

    function setStatus(msg, isError) """),format.raw/*47.38*/("""{"""),format.raw/*47.39*/("""
        """),format.raw/*48.9*/("""statusEl.textContent = msg;
        statusEl.className = 'status' + (isError ? ' status-error' : ' status-ok');
    """),format.raw/*50.5*/("""}"""),format.raw/*50.6*/("""

    """),format.raw/*52.5*/("""function renderState(data) """),format.raw/*52.32*/("""{"""),format.raw/*52.33*/("""
        """),format.raw/*53.9*/("""currentState = data;
        if (data.done) """),format.raw/*54.24*/("""{"""),format.raw/*54.25*/("""
            """),format.raw/*55.13*/("""lineInd.classList.add('hidden');
            lineNum.textContent = '—';
            memDisplay.textContent = '(program finished)';
            btnStep.disabled = true;
            setStatus('Program finished.', false);
            return;
        """),format.raw/*61.9*/("""}"""),format.raw/*61.10*/("""
        """),format.raw/*62.9*/("""lineInd.classList.remove('hidden');
        lineNum.textContent = data.lineNumber;
        memDisplay.textContent = JSON.stringify(data.locals, null, 2);
        btnStep.disabled = false;
        setStatus('Paused at line ' + data.lineNumber + '.', false);
    """),format.raw/*67.5*/("""}"""),format.raw/*67.6*/("""

    """),format.raw/*69.5*/("""async function fetchJSON(url, options) """),format.raw/*69.44*/("""{"""),format.raw/*69.45*/("""
        """),format.raw/*70.9*/("""const res = await fetch(url, options);
        const text = await res.text();
        let data;
        try """),format.raw/*73.13*/("""{"""),format.raw/*73.14*/("""
            """),format.raw/*74.13*/("""data = JSON.parse(text);
        """),format.raw/*75.9*/("""}"""),format.raw/*75.10*/(""" """),format.raw/*75.11*/("""catch (_) """),format.raw/*75.21*/("""{"""),format.raw/*75.22*/("""
            """),format.raw/*76.13*/("""throw new Error('Server returned non-JSON response (status ' + res.status + '). Check server logs.');
        """),format.raw/*77.9*/("""}"""),format.raw/*77.10*/("""
        """),format.raw/*78.9*/("""if (data.error) throw new Error(data.error);
        return data;
    """),format.raw/*80.5*/("""}"""),format.raw/*80.6*/("""

    """),format.raw/*82.5*/("""btnRun.addEventListener('click', async function () """),format.raw/*82.56*/("""{"""),format.raw/*82.57*/("""
        """),format.raw/*83.9*/("""const code = codeInput.value.trim();
        if (!code) """),format.raw/*84.20*/("""{"""),format.raw/*84.21*/(""" """),format.raw/*84.22*/("""setStatus('Please enter a Python program.', true); return; """),format.raw/*84.81*/("""}"""),format.raw/*84.82*/("""

        """),format.raw/*86.9*/("""btnRun.disabled = true;
        btnStep.disabled = true;
        setStatus('Starting…', false);
        memDisplay.textContent = '—';
        lineInd.classList.add('hidden');
        currentState = null;

        try """),format.raw/*93.13*/("""{"""),format.raw/*93.14*/("""
            """),format.raw/*94.13*/("""const data = await fetchJSON('/start', """),format.raw/*94.52*/("""{"""),format.raw/*94.53*/("""
                """),format.raw/*95.17*/("""method: 'POST',
                headers: """),format.raw/*96.26*/("""{"""),format.raw/*96.27*/(""" """),format.raw/*96.28*/("""'Content-Type': 'text/plain' """),format.raw/*96.57*/("""}"""),format.raw/*96.58*/(""",
                body: code
            """),format.raw/*98.13*/("""}"""),format.raw/*98.14*/(""");
            renderState(data);
        """),format.raw/*100.9*/("""}"""),format.raw/*100.10*/(""" """),format.raw/*100.11*/("""catch (e) """),format.raw/*100.21*/("""{"""),format.raw/*100.22*/("""
            """),format.raw/*101.13*/("""setStatus('Error: ' + e.message, true);
        """),format.raw/*102.9*/("""}"""),format.raw/*102.10*/(""" """),format.raw/*102.11*/("""finally """),format.raw/*102.19*/("""{"""),format.raw/*102.20*/("""
            """),format.raw/*103.13*/("""btnRun.disabled = false;
        """),format.raw/*104.9*/("""}"""),format.raw/*104.10*/("""
    """),format.raw/*105.5*/("""}"""),format.raw/*105.6*/(""");

    btnStep.addEventListener('click', async function () """),format.raw/*107.57*/("""{"""),format.raw/*107.58*/("""
        """),format.raw/*108.9*/("""btnStep.disabled = true;
        setStatus('Stepping…', false);

        try """),format.raw/*111.13*/("""{"""),format.raw/*111.14*/("""
            """),format.raw/*112.13*/("""const data = await fetchJSON('/step');
            renderState(data);
        """),format.raw/*114.9*/("""}"""),format.raw/*114.10*/(""" """),format.raw/*114.11*/("""catch (e) """),format.raw/*114.21*/("""{"""),format.raw/*114.22*/("""
            """),format.raw/*115.13*/("""setStatus('Error: ' + e.message, true);
        """),format.raw/*116.9*/("""}"""),format.raw/*116.10*/("""
    """),format.raw/*117.5*/("""}"""),format.raw/*117.6*/(""");

    btnAsk.addEventListener('click', async function () """),format.raw/*119.56*/("""{"""),format.raw/*119.57*/("""
        """),format.raw/*120.9*/("""const query = askInput.value.trim();
        if (!query) return;

        btnAsk.disabled = true;
        askResponse.textContent = 'Thinking…';

        const body = """),format.raw/*126.22*/("""{"""),format.raw/*126.23*/("""
            """),format.raw/*127.13*/("""query,
            code: codeInput.value,
            model: modelInput.value.trim() || 'llama3.2'
        """),format.raw/*130.9*/("""}"""),format.raw/*130.10*/(""";
        if (currentState && !currentState.done) """),format.raw/*131.49*/("""{"""),format.raw/*131.50*/("""
            """),format.raw/*132.13*/("""body.lineNumber = currentState.lineNumber;
            body.locals     = currentState.locals;
        """),format.raw/*134.9*/("""}"""),format.raw/*134.10*/("""

        """),format.raw/*136.9*/("""try """),format.raw/*136.13*/("""{"""),format.raw/*136.14*/("""
            """),format.raw/*137.13*/("""const data = await fetchJSON('/ask', """),format.raw/*137.50*/("""{"""),format.raw/*137.51*/("""
                """),format.raw/*138.17*/("""method: 'POST',
                headers: """),format.raw/*139.26*/("""{"""),format.raw/*139.27*/(""" """),format.raw/*139.28*/("""'Content-Type': 'application/json' """),format.raw/*139.63*/("""}"""),format.raw/*139.64*/(""",
                body: JSON.stringify(body)
            """),format.raw/*141.13*/("""}"""),format.raw/*141.14*/(""");
            askResponse.textContent = data.response;
        """),format.raw/*143.9*/("""}"""),format.raw/*143.10*/(""" """),format.raw/*143.11*/("""catch (e) """),format.raw/*143.21*/("""{"""),format.raw/*143.22*/("""
            """),format.raw/*144.13*/("""askResponse.textContent = 'Error: ' + e.message;
        """),format.raw/*145.9*/("""}"""),format.raw/*145.10*/(""" """),format.raw/*145.11*/("""finally """),format.raw/*145.19*/("""{"""),format.raw/*145.20*/("""
            """),format.raw/*146.13*/("""btnAsk.disabled = false;
        """),format.raw/*147.9*/("""}"""),format.raw/*147.10*/("""
    """),format.raw/*148.5*/("""}"""),format.raw/*148.6*/(""");
"""),format.raw/*149.1*/("""}"""),format.raw/*149.2*/(""")();
</script>
""")))}),format.raw/*151.2*/("""
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
                  HASH: 77ba52c996e6347513561fa747b6b266f8fba993
                  MATRIX: 722->1|818->5|845->24|884->26|911->27|2179->1267|2208->1268|2240->1273|3025->2030|3054->2031|3090->2040|3233->2156|3261->2157|3294->2163|3349->2190|3378->2191|3414->2200|3486->2244|3515->2245|3556->2258|3830->2505|3859->2506|3895->2515|4183->2776|4211->2777|4244->2783|4311->2822|4340->2823|4376->2832|4512->2940|4541->2941|4582->2954|4642->2987|4671->2988|4700->2989|4738->2999|4767->3000|4808->3013|4945->3123|4974->3124|5010->3133|5107->3203|5135->3204|5168->3210|5247->3261|5276->3262|5312->3271|5396->3327|5425->3328|5454->3329|5541->3388|5570->3389|5607->3399|5852->3616|5881->3617|5922->3630|5989->3669|6018->3670|6063->3687|6132->3728|6161->3729|6190->3730|6247->3759|6276->3760|6345->3801|6374->3802|6444->3844|6474->3845|6504->3846|6543->3856|6573->3857|6615->3870|6691->3918|6721->3919|6751->3920|6788->3928|6818->3929|6860->3942|6921->3975|6951->3976|6984->3981|7013->3982|7102->4042|7132->4043|7169->4052|7275->4129|7305->4130|7347->4143|7453->4221|7483->4222|7513->4223|7552->4233|7582->4234|7624->4247|7700->4295|7730->4296|7763->4301|7792->4302|7880->4361|7910->4362|7947->4371|8143->4538|8173->4539|8215->4552|8350->4659|8380->4660|8459->4710|8489->4711|8531->4724|8661->4826|8691->4827|8729->4837|8762->4841|8792->4842|8834->4855|8900->4892|8930->4893|8976->4910|9046->4951|9076->4952|9106->4953|9170->4988|9200->4989|9286->5046|9316->5047|9408->5111|9438->5112|9468->5113|9507->5123|9537->5124|9579->5137|9664->5194|9694->5195|9724->5196|9761->5204|9791->5205|9833->5218|9894->5251|9924->5252|9957->5257|9986->5258|10017->5261|10046->5262|10093->5278
                  LINES: 21->1|26->2|26->2|26->2|27->3|56->32|56->32|57->33|71->47|71->47|72->48|74->50|74->50|76->52|76->52|76->52|77->53|78->54|78->54|79->55|85->61|85->61|86->62|91->67|91->67|93->69|93->69|93->69|94->70|97->73|97->73|98->74|99->75|99->75|99->75|99->75|99->75|100->76|101->77|101->77|102->78|104->80|104->80|106->82|106->82|106->82|107->83|108->84|108->84|108->84|108->84|108->84|110->86|117->93|117->93|118->94|118->94|118->94|119->95|120->96|120->96|120->96|120->96|120->96|122->98|122->98|124->100|124->100|124->100|124->100|124->100|125->101|126->102|126->102|126->102|126->102|126->102|127->103|128->104|128->104|129->105|129->105|131->107|131->107|132->108|135->111|135->111|136->112|138->114|138->114|138->114|138->114|138->114|139->115|140->116|140->116|141->117|141->117|143->119|143->119|144->120|150->126|150->126|151->127|154->130|154->130|155->131|155->131|156->132|158->134|158->134|160->136|160->136|160->136|161->137|161->137|161->137|162->138|163->139|163->139|163->139|163->139|163->139|165->141|165->141|167->143|167->143|167->143|167->143|167->143|168->144|169->145|169->145|169->145|169->145|169->145|170->146|171->147|171->147|172->148|172->148|173->149|173->149|175->151
                  -- GENERATED --
              */
          