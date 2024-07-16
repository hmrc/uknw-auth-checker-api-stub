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

  "POST /cau/validatecustomsauth/v1" should {
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
        getJsonFile("requests/authRequest400_wrongDate.json"),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }

    "return 400 on multiple Eoris with multiple errors" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest400_wrongAll.json"),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }

    "return 400 on wrong Auth" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest400_wrongAuth.json"),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }

    "return 400 on wrong Date" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest400_wrongDate.json"),
        validHeaders
      ).status mustBe Status.BAD_REQUEST
    }

    "return 400 on wrong Eori" in {
      postRequestWithHeader(
        authorisationUrl,
        getJsonFile("requests/authRequest400_wrongEori.json"),
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
        getJsonFile("dummies/authRequest401_api-test-only.json"),
        validHeaders
      ).status mustBe Status.UNAUTHORIZED
    }
  }

}
