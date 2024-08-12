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
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.PerformanceRequests._
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.Requests200._
import uk.gov.hmrc.uknwauthcheckerapistub.services.StubDataService

class EisStubControllerSpec extends BaseSpec {

  private val fakeRequest_single   = fakePostReq.withJsonBody(getRequestJson(req200_single))
  private val fakeRequest_multiple = fakePostReq.withJsonBody(getRequestJson(req200_multiple))

  // Performance testing
  private val fakePerfRequest1Eori    = fakePostReq.withJsonBody(getRequestJson(perfTest_1Eori))
  private val fakePerfRequest100Eori  = fakePostReq.withJsonBody(getRequestJson(perfTest_100Eori))
  private val fakePerfRequest500Eori  = fakePostReq.withJsonBody(getRequestJson(perfTest_500Eori))
  private val fakePerfRequest1000Eori = fakePostReq.withJsonBody(getRequestJson(perfTest_1000Eori))
  private val fakePerfRequest3000Eori = fakePostReq.withJsonBody(getRequestJson(perfTest_3000Eori))

  private val stubDataService: StubDataService   = new StubDataService()
  private val controller:      EisStubController = new EisStubController(stubDataService, Helpers.stubControllerComponents())

  "POST /cau/validatecustomsauth/v1" should {
    "return 200 on a single Eori" in {
      val result = controller.authorisations()(fakeRequest_single)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe getResponseJson(req200_single)
    }

    "return 200 on a multiple Eoris" in {
      val result = controller.authorisations()(fakeRequest_multiple)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe getResponseJson(req200_multiple)
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

  // Performance Testing
  "return 200 on a single Eori" in {
    val result = controller.authorisations()(fakePerfRequest1Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getResponseJson(perfTest_1Eori)
  }

  "return 200 on 100 Eori" in {
    val result = controller.authorisations()(fakePerfRequest100Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getResponseJson(perfTest_100Eori)
  }

  "return 200 on 500 Eori" in {
    val result = controller.authorisations()(fakePerfRequest500Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getResponseJson(perfTest_500Eori)
  }

  "return 200 on 1000 Eori" in {
    val result = controller.authorisations()(fakePerfRequest1000Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getResponseJson(perfTest_1000Eori)
  }

  "return 200 on 3000 Eori" in {
    val result = controller.authorisations()(fakePerfRequest3000Eori)
    status(result)        shouldBe Status.OK
    contentAsJson(result) shouldBe getResponseJson(perfTest_3000Eori)
  }
}
