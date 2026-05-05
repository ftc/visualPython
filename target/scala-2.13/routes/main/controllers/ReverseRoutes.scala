// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:2
package controllers {

  // @LINE:2
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:2
    def index: Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:5
    def startDebugger: Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "start")
    }
  
    // @LINE:6
    def stepDebugger: Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "step")
    }
  
    // @LINE:9
    def ask: Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "ask")
    }
  
  }

  // @LINE:12
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
