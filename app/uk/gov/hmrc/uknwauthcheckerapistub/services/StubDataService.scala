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

package uk.gov.hmrc.uknwauthcheckerapistub.services

import javax.inject.Inject

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Results._
import play.api.mvc.{AnyContent, Request, Result}
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.PerformanceRequests._
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.Requests200._
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.Requests400._
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.ErrorResponses._
import uk.gov.hmrc.uknwauthcheckerapistub.utils.JsonGetter

class StubDataService @Inject() (implicit localDateService: LocalDateService) extends JsonGetter {

  def stubbing(req: Request[AnyContent]): Result = {
    val rawEori: Option[JsValue] = req.body.asJson

    val responseMapping: Map[JsValue, Result] = Map(
      getRequestJson(req200_single)       -> Ok(getResponseJson(req200_single)),
      getRequestJson(req200_multiple)     -> Ok(getResponseJson(req200_multiple)),
      getRequestJson(req400_singleEori)   -> BadRequest(Json.parse(expectedRes400_singleEori)),
      getRequestJson(req400_multipleEori) -> BadRequest(Json.parse(expectedRes400_multipleEori)),
      getRequestJson(req400_noEoris)      -> BadRequest(Json.parse(expectedRes400_missingEori)),
      getRequestJson(req400_tooManyEoris) -> BadRequest(Json.parse(expectedRes400_wrongNumberOfEoris)),
      getRequestJson(req403_single)       -> Forbidden(Json.parse(expectedRes403_forbidden)),
      getRequestJson(perfTest_1Eori)      -> Ok(getResponseJson(perfTest_1Eori)),
      getRequestJson(perfTest_100Eori)    -> Ok(getResponseJson(perfTest_100Eori)),
      getRequestJson(perfTest_500Eori)    -> Ok(getResponseJson(perfTest_500Eori)),
      getRequestJson(perfTest_1000Eori)   -> Ok(getResponseJson(perfTest_1000Eori)),
      getRequestJson(perfTest_3000Eori)   -> Ok(getResponseJson(perfTest_3000Eori))
    )

    rawEori.flatMap(responseMapping.get).getOrElse(InternalServerError(Json.parse(expectedRes500)))
  }
}
