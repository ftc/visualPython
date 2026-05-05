package com.example

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET /" should {
    "return 200 OK" in {
      val request = FakeRequest(GET, "/")
      val result  = route(app, request).value
      status(result) mustBe OK
    }

    "return HTML content type" in {
      val request = FakeRequest(GET, "/")
      val result  = route(app, request).value
      contentType(result) mustBe Some("text/html")
    }

    "contain the app name in the body" in {
      val request = FakeRequest(GET, "/")
      val result  = route(app, request).value
      contentAsString(result) must include("VisualPython")
    }
  }
}
