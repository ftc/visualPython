
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
    <label for="ask-input">Ask Ollama</label>
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
                  HASH: 521ca544ea98cd9c0185b500e5a074422c819d94
                  MATRIX: 722->1|818->5|845->24|884->26|911->27|2172->1260|2201->1261|2233->1266|3018->2023|3047->2024|3083->2033|3226->2149|3254->2150|3287->2156|3342->2183|3371->2184|3407->2193|3479->2237|3508->2238|3549->2251|3823->2498|3852->2499|3888->2508|4176->2769|4204->2770|4237->2776|4304->2815|4333->2816|4369->2825|4505->2933|4534->2934|4575->2947|4635->2980|4664->2981|4693->2982|4731->2992|4760->2993|4801->3006|4938->3116|4967->3117|5003->3126|5100->3196|5128->3197|5161->3203|5240->3254|5269->3255|5305->3264|5389->3320|5418->3321|5447->3322|5534->3381|5563->3382|5600->3392|5845->3609|5874->3610|5915->3623|5982->3662|6011->3663|6056->3680|6125->3721|6154->3722|6183->3723|6240->3752|6269->3753|6338->3794|6367->3795|6437->3837|6467->3838|6497->3839|6536->3849|6566->3850|6608->3863|6684->3911|6714->3912|6744->3913|6781->3921|6811->3922|6853->3935|6914->3968|6944->3969|6977->3974|7006->3975|7095->4035|7125->4036|7162->4045|7268->4122|7298->4123|7340->4136|7446->4214|7476->4215|7506->4216|7545->4226|7575->4227|7617->4240|7693->4288|7723->4289|7756->4294|7785->4295|7873->4354|7903->4355|7940->4364|8136->4531|8166->4532|8208->4545|8343->4652|8373->4653|8452->4703|8482->4704|8524->4717|8654->4819|8684->4820|8722->4830|8755->4834|8785->4835|8827->4848|8893->4885|8923->4886|8969->4903|9039->4944|9069->4945|9099->4946|9163->4981|9193->4982|9279->5039|9309->5040|9401->5104|9431->5105|9461->5106|9500->5116|9530->5117|9572->5130|9657->5187|9687->5188|9717->5189|9754->5197|9784->5198|9826->5211|9887->5244|9917->5245|9950->5250|9979->5251|10010->5254|10039->5255|10086->5271
                  LINES: 21->1|26->2|26->2|26->2|27->3|56->32|56->32|57->33|71->47|71->47|72->48|74->50|74->50|76->52|76->52|76->52|77->53|78->54|78->54|79->55|85->61|85->61|86->62|91->67|91->67|93->69|93->69|93->69|94->70|97->73|97->73|98->74|99->75|99->75|99->75|99->75|99->75|100->76|101->77|101->77|102->78|104->80|104->80|106->82|106->82|106->82|107->83|108->84|108->84|108->84|108->84|108->84|110->86|117->93|117->93|118->94|118->94|118->94|119->95|120->96|120->96|120->96|120->96|120->96|122->98|122->98|124->100|124->100|124->100|124->100|124->100|125->101|126->102|126->102|126->102|126->102|126->102|127->103|128->104|128->104|129->105|129->105|131->107|131->107|132->108|135->111|135->111|136->112|138->114|138->114|138->114|138->114|138->114|139->115|140->116|140->116|141->117|141->117|143->119|143->119|144->120|150->126|150->126|151->127|154->130|154->130|155->131|155->131|156->132|158->134|158->134|160->136|160->136|160->136|161->137|161->137|161->137|162->138|163->139|163->139|163->139|163->139|163->139|165->141|165->141|167->143|167->143|167->143|167->143|167->143|168->144|169->145|169->145|169->145|169->145|169->145|170->146|171->147|171->147|172->148|172->148|173->149|173->149|175->151
                  -- GENERATED --
              */
          