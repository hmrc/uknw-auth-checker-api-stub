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

package uk.gov.hmrc.uknwauthcheckerapistub

import java.time.LocalDate

import play.api.http.Status
import play.api.libs.json.Json
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.ApiCheckerRequest

class EisStubControllerISpec extends BaseISpec {
  private val localNow: LocalDate = LocalDate.now()

  "POST /cau/validatecustomsauth/v1" should {
    "return 200 on a single Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        Json.toJson(ApiCheckerRequest(localNow.toString, eoris = eoriRq_1_valid)),
        validHeaders
      ).status mustBe Status.OK
    }

    "return 403 on a missing authorization in the Header" in {
      postRequestWithHeader(
        authorisationUrl,
        Json.toJson(ApiCheckerRequest(localNow.toString, eoris = eoriRq_1_valid)),
        invalidHeaders1
      ).status mustBe Status.FORBIDDEN
    }

    "return 403 on a invalid authorization in the Header" in {
      postRequestWithHeader(
        authorisationUrl,
        Json.toJson(ApiCheckerRequest(localNow.toString, eoris = eoriRq_1_valid)),
        invalidHeaders2
      ).status mustBe Status.FORBIDDEN
    }

    "return 403 on a missing Header" in {
      postRequestWithoutHeader(
        authorisationUrl,
        Json.toJson(ApiCheckerRequest(localNow.toString, eoris = eoriRq_1_valid))
      ).status mustBe Status.FORBIDDEN
    }

    "return 500 on invalid Body" in {
      postRequestWithHeader(
        authorisationUrl,
        invalidBody,
        validHeaders
      ).status mustBe Status.INTERNAL_SERVER_ERROR
    }
  }
}
