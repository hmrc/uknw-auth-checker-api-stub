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

class EisStubControllerISpec extends BaseISpec {

  "POST /authorisations" should {
    "return 200 on a single Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest200_single.json"),
        validHeaders
      ).status mustBe Status.OK
    }

    "return 403 on a missing authorization in the Header" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest200_single.json"),
        invalidHeaders1
      ).status mustBe Status.FORBIDDEN
    }

    "return 403 on a invalid authorization in the Header" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest200_single.json"),
        invalidHeaders2
      ).status mustBe Status.FORBIDDEN
    }

    "return 403 on a missing Header" in {
      postRequestWithoutHeader(authorisationUrl, getJsonFile("requests/authRequest200_single.json")).status mustBe Status.FORBIDDEN
    }

    "return 400 on a single Eoris with an invalid date" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest400_single.json"),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }

    "return 400 on multiple Eoris with multiple errors" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest400_multiple.json"),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }
  }

  "return 405 on a GET request" in {
    getRequestWithHeader(authorisationUrl,validHeaders).status mustBe Status.METHOD_NOT_ALLOWED
  }

}
