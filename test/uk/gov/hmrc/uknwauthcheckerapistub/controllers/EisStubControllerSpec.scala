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

  private val fakeRequest_single      = fakePostReq.withJsonBody(getJsonFile("requests/authRequest200_single.json"))
  private val fakeRequest_multiple    = fakePostReq.withJsonBody(getJsonFile("requests/authRequest200_multiple.json"))
  private val fakeBadRequest_single   = fakePostReq.withJsonBody(getJsonFile("requests/authRequest400_multiple.json"))
  private val fakeBadRequest_multiple = fakePostReq.withJsonBody(getJsonFile("requests/authRequest400_multiple.json"))

  private val controller = new EisStubController(Helpers.stubControllerComponents())

  "POST /authorisations" should {
    "return 200 on a single Eori" in {
      val result = controller.authorisations()(fakeRequest_single)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe getJsonFile("responses/eisAuthResponse200_valid_single.json")
    }

    "return 200 on a multiple Eoris" in {
      val result = controller.authorisations()(fakeRequest_multiple)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe getJsonFile("responses/eisAuthResponse200_valid_multiple.json")
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

    "return 400 on a wrong date" in {
      val result = controller.authorisations()(fakeBadRequest_single)
      status(result) shouldBe Status.BAD_REQUEST
    }

    "return 400 on a wrong date, authType and eori" in {
      val result = controller.authorisations()(fakeBadRequest_multiple)
      status(result)        shouldBe Status.BAD_REQUEST
      contentAsJson(result) shouldBe getJsonFile("responses/eisAuthResponse400_multiple.json")
    }
  }
}
