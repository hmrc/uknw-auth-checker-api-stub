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

import java.time.{LocalDate, LocalTime, ZoneId, ZonedDateTime}
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers
import play.api.test.Helpers.*
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.EisAuthorisationRequest
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{EisAuthorisationResponseError, EisAuthorisationsResponse, ErrorDetails}

class EisStubControllerSpec extends BaseSpec {

  private val zonedNow:   ZonedDateTime     = ZonedDateTime.of(LocalDate.now.atTime(LocalTime.MIDNIGHT), ZoneId.of("UTC"))
  private val localNow:   LocalDate         = LocalDate.now()
  private val controller: EisStubController = injected[EisStubController]

  "POST /cau/validatecustomsauth/v1" should {
    "return 200 on a single Eori" in {
      val request  = createRequest(body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoriRq_1_valid)))
      val result   = controller.authorisations()(request)
      val expected = EisAuthorisationsResponse(zonedNow, results = eoriResult_1_valid)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe Json.toJson(expected)
    }

    "return 200 on a multiple Eoris" in {
      val request  = createRequest(body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoriRq_2_valid)))
      val result   = controller.authorisations()(request)
      val expected = EisAuthorisationsResponse(zonedNow, results = eoriResult_2_valid)
      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe Json.toJson(expected)
    }

    "return 403 on a missing authorization Header" in { // invalidHeaders1
      val request = createRequest(headers = invalidHeaders1, body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoriRq_2_valid)))
      val result  = controller.authorisations()(request)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 403 on a wrong Header" in {
      val request = createRequest(headers = invalidHeaders2, body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoriRq_2_valid)))
      val result  = controller.authorisations()(request)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 403 on a missing Header" in {
      val request = createRequest(headers = Nil, body = Json.toJson(EisAuthorisationRequest(localNow.toString, eoris = eoriRq_2_valid)))
      val result  = controller.authorisations()(request)
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 500 on a body-less POST Request" in {
      val request  = createRequest(body = Json.toJson("{}"))
      val result   = controller.authorisations()(request)
      val expected = EisAuthorisationResponseError(ErrorDetails(zonedNow.toString, 500, "An internal error has occurred"))
      status(result)        shouldBe Status.INTERNAL_SERVER_ERROR
      contentAsJson(result) shouldBe Json.toJson(expected)
    }

  }

}
