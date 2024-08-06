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

import play.api.http.Status
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.PerformanceRequests._
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.Requests200._
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.Requests400._

class EisStubControllerISpec extends BaseISpec {

  "POST /cau/validatecustomsauth/v1" should {
    "return 200 on a single Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(req200_single),
        validHeaders
      ).status mustBe Status.OK
    }

    // Performance testing
    "return 200 on single Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(perfTest_1Eori),
        validHeaders
      ).status mustBe Status.OK
    }

    "return 200 on 100 Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(perfTest_100Eori),
        validHeaders
      ).status mustBe Status.OK
    }

    "return 200 on 500 Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(perfTest_500Eori),
        validHeaders
      ).status mustBe Status.OK
    }

    "return 200 on 1000 Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(perfTest_1000Eori),
        validHeaders
      ).status mustBe Status.OK
    }

    "return 200 on 3000 Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(perfTest_3000Eori),
        validHeaders
      ).status mustBe Status.OK
    }

    "return 403 on a missing authorization in the Header" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(req200_single),
        invalidHeaders1
      ).status mustBe Status.FORBIDDEN
    }

    "return 403 on a invalid authorization in the Header" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(req200_single),
        invalidHeaders2
      ).status mustBe Status.FORBIDDEN
    }

    "return 403 on a missing Header" in {
      postRequestWithoutHeader(
        authorisationUrl,
        getRequestJson(req200_single)
      ).status mustBe Status.FORBIDDEN
    }

    "return 400 on multiple Eoris with multiple errors" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(req400_multipleEori),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }

    "return 400 on wrong Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(req400_singleEori),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }

    "return 500 on invalid Body" in {
      postRequestWithHeader(
        authorisationUrl,
        invalidBody,
        validHeaders
      ).status mustBe Status.INTERNAL_SERVER_ERROR
    }
  }
  "GET /cau/validatecustomsauth/v1" should {
    "return 405 on a GET request" in {
      getRequestWithHeader(authorisationUrl, validHeaders).status mustBe Status.METHOD_NOT_ALLOWED
    }
  }

  "Dummy post request" should {
    "return 403 on a dummy POST Request" in {
      postRequestWithHeader(
        authorisationUrl,
        getRequestJson(req403_single),
        validHeaders
      ).status mustBe Status.FORBIDDEN
    }
  }

}
