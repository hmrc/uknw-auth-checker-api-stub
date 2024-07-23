/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.uknwauthcheckerapistub.controllers

import play.api.http.Status
import play.api.test.Helpers
import play.api.test.Helpers._

class EisStubControllerSpec extends BaseSpec {

  private val fakeRequest_single       = fakePostReq.withJsonBody(getJsonFile("authRequest200_single.json"))
  private val fakeRequest_multiple     = fakePostReq.withJsonBody(getJsonFile("authRequest200_multiple.json"))
  private val fakeBadRequest_wrongAll  = fakePostReq.withJsonBody(getJsonFile("authRequest400_wrongAll.json"))
  private val fakeBadRequest_wrongAuth = fakePostReq.withJsonBody(getJsonFile("authRequest400_wrongAuth.json"))
  private val fakeBadRequest_wrongDate = fakePostReq.withJsonBody(getJsonFile("authRequest400_wrongDate.json"))
  private val fakeBadRequest_wrongEori = fakePostReq.withJsonBody(getJsonFile("authRequest400_wrongEori.json"))
  private val fakeForbidden_dummy      = fakePostReq.withJsonBody(getJsonFile("authRequest403_api-test-only.json"))

  // Performance testing
  private val fakePerfRequest1Eori    = fakePostReq.withJsonBody(getJsonFile("perfTestRequest_1Eori.json"))
  private val fakePerfRequest100Eori  = fakePostReq.withJsonBody(getJsonFile("perfTestRequest_100Eori.json"))
  private val fakePerfRequest500Eori  = fakePostReq.withJsonBody(getJsonFile("perfTestRequest_500Eori.json"))
  private val fakePerfRequest1000Eori = fakePostReq.withJsonBody(getJsonFile("perfTestRequest_1000Eori.json"))
  private val fakePerfRequest3000Eori = fakePostReq.withJsonBody(getJsonFile("perfTestRequest_3000Eori.json"))

  private val controller = new EisStubController(Helpers.stubControllerComponents())

  "POST /cau/validatecustomsauth/v1" should {
    "return 200 on a single Eori" in {
      val result = controller.authorisations()(fakeRequest_single)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe getJsonFile("eisAuthResponse200_valid_single.json")
    }

    "return 200 on a multiple Eoris" in {
      val result = controller.authorisations()(fakeRequest_multiple)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe getJsonFile("eisAuthResponse200_valid_multiple.json")
    }

    "return 403 on a missing authorization Header" in {
      val result = controller.authorisations()(fakePostReqForbiddenHeader1)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 403 on a wrong Header" in {
      val result = controller.authorisations()(fakePostReqForbiddenHeader2)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 403 on a missing Header" in {
      val result = controller.authorisations()(fakeHeadlessPostReq)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 400 on a wrong date, authType and eori" in {
      val result = controller.authorisations()(fakeBadRequest_wrongAll)
      status(result)        shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe getJsonFile("eisAuthResponse400_wrongAll.json")
    }

    "return 400 on a wrong auth" in {
      val result = controller.authorisations()(fakeBadRequest_wrongAuth)
      status(result)        shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe getJsonFile("eisAuthResponse400_wrongAuth.json")
    }

    "return 400 on a wrong date" in {
      val result = controller.authorisations()(fakeBadRequest_wrongDate)
      status(result)        shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe getJsonFile("eisAuthResponse400_wrongDate.json")
    }

    "return 400 on a wrong eori" in {
      val result = controller.authorisations()(fakeBadRequest_wrongEori)
      status(result)        shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe getJsonFile("eisAuthResponse400_wrongEori.json")
    }

    "return 500 on a body-less POST Request" in {
      val result = controller.authorisations()(fakeNoBodyPostReq)
      status(result) shouldBe Status.INTERNAL_SERVER_ERROR
    }

  }

  "GET /cau/validatecustomsauth/v1" should {
    "return 405 on a get Request" in {
      val result = controller.authorisations()(fakeGetRequest)
      status(result) shouldBe Status.METHOD_NOT_ALLOWED
    }
  }

  "Dummy post request" should {
    "return 403 on a dummy POST Request" in {
      val result = controller.authorisations()(fakeForbidden_dummy)
      status(result) shouldBe Status.FORBIDDEN
    }
  }

  // Performance Testing
  "return 200 on a single Eori" in {
    val result = controller.authorisations()(fakePerfRequest1Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getJsonFile("perfTestResponse_1Eori.json")
  }

  "return 200 on 100 Eori" in {
    val result = controller.authorisations()(fakePerfRequest100Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getJsonFile("perfTestResponse_100Eori.json")
  }

  "return 200 on 500 Eori" in {
    val result = controller.authorisations()(fakePerfRequest500Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getJsonFile("perfTestResponse_500Eori.json")
  }

  "return 200 on 1000 Eori" in {
    val result = controller.authorisations()(fakePerfRequest1000Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getJsonFile("perfTestResponse_1000Eori.json")
  }

  "return 200 on 3000 Eori" in {
    val result = controller.authorisations()(fakePerfRequest3000Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getJsonFile("perfTestResponse_3000Eori.json")
  }

}
