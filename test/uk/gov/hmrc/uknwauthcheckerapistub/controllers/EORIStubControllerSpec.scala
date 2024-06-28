package uk.gov.hmrc.uknwauthcheckerapistub.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.uknwauthcheckerapistub.tools.Helper

class EORIStubControllerSpec extends AnyWordSpec with Matchers  with Helper{

  private val fakeRequest = FakeRequest("GET", "/check-eori")
  private val controller = new EORIStubController(Helpers.stubControllerComponents())


  "GET /check-eori" should {
    "return 200" in {

      val result = controller.checkEORI()(fakeRequest)
      status(result) shouldBe Status.OK

      assert(contentAsJson(result) === Json.toJson(defaultEORI))
      assert(contentAsJson(result) !== Json.toJson(anotherEORI))
    }
  }
}
