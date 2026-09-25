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

import java.time.LocalDate

import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers
import play.api.test.Helpers._
import uk.gov.hmrc.uknwauthcheckerapistub.EoriGenerator
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.EisAuthorisationRequest
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{EisAuthorisationResponseError, EisAuthorisationsResponse, ErrorDetails}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants._
import uk.gov.hmrc.uknwauthcheckerapistub.utils.EoriResultBuilder

class EisStubControllerSpec extends BaseSpec, EoriGenerator {
  
  private val localNow:      LocalDate         = LocalDate.now()
  private val controller:    EisStubController = injected[EisStubController]
  private val myEoriBuilder: EoriResultBuilder = new EoriResultBuilder

  "POST /cau/validatecustomsauth/v1" should {
    "return 200 on a single Eori" in {
      val eoris: Seq[String] = useEoriGenerator(1, Some(1))
      val expectedEoris = myEoriBuilder.makeResults(eoris)

      val request = createRequest(
        body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris))
      )
      val result = controller.authorisations()(request)

      val response = contentAsJson(result).as[EisAuthorisationsResponse]

      status(result) shouldBe Status.OK
      response.authType shouldBe "UKNW"
      response.results shouldBe expectedEoris
    }

    "return 200 on a multiple Eoris" in {
      val eoris: Seq[String] = useEoriGenerator(2, Some(1))
      val expectedEoris = myEoriBuilder.makeResults(eoris)

      val request = createRequest(
        body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris))
      )
      val result = controller.authorisations()(request)

      val response = contentAsJson(result).as[EisAuthorisationsResponse]

      status(result) shouldBe Status.OK
      response.authType shouldBe "UKNW"
      response.results shouldBe expectedEoris
    }

    "return 403 on a missing authorization Header" in {
      val eoris: Seq[String] = useEoriGenerator(1, Some(1))

      val request = createRequest(headers = invalidHeaders1, body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris)))
      val result  = controller.authorisations()(request)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 403 on a wrong Header" in {
      val eoris: Seq[String] = useEoriGenerator(1, Some(1))

      val request = createRequest(headers = invalidHeaders2, body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris)))
      val result  = controller.authorisations()(request)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 403 on a missing Header" in {
      val eoris: Seq[String] = useEoriGenerator(1, Some(1))

      val request = createRequest(headers = Nil, body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris)))
      val result  = controller.authorisations()(request)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 403 to trigger a 403 error in the Checker API" in {
      val eoris: Seq[String] = Seq(mock403Eori)

      val request = createRequest(body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris)))
      val result  = controller.authorisations()(request)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 500 on a body-less POST Request" in {
      val request = createRequest(body = Json.toJson("{}"))
      val result = controller.authorisations()(request)

      val response = contentAsJson(result).as[EisAuthorisationResponseError]

      status(result) shouldBe Status.INTERNAL_SERVER_ERROR
      response.errorDetails.errorCode shouldBe 500
      response.errorDetails.errorMessage shouldBe "An internal error has occurred"
    }

    "return 500 to trigger a 500 error in the Checker API" in {
      val eoris: Seq[String] = Seq(mock500Eori)

      val request = createRequest(body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris)))
      val result = controller.authorisations()(request)

      val response = contentAsJson(result).as[EisAuthorisationResponseError]

      status(result) shouldBe Status.INTERNAL_SERVER_ERROR
      response.errorDetails.errorCode shouldBe 500
      response.errorDetails.errorMessage shouldBe "An internal error has occurred"
    }

    "return 503 to emulate EIS being down" in {
      val eoris: Seq[String] = Seq(mock503Eori)

      val request = createRequest(body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoris)))
      val result  = controller.authorisations()(request)

      status(result)          shouldBe Status.SERVICE_UNAVAILABLE
      contentAsString(result) shouldBe body503
    }

  }

}
