package uk.gov.hmrc.uknwauthcheckerapistub

import play.api.http.Status

class EisStubControllerISpec extends BaseISpec {

"POST /authorisations" should {
  "return 200 on a single Eori" in {
    postRequestWithoutHeader(s"http://localhost:$port/authorisations",getJsonFile("requests/authRequest200_single.json")).status mustBe Status.OK
    }
  }


}



