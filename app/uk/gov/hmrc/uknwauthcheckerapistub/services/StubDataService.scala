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

import play.api.libs.json.{JsSuccess, JsValue, Json}
import play.api.mvc.Results._
import play.api.mvc.{Request, Result}
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.EisAuthorisationRequest
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{EisAuthorisationResponseError, EisAuthorisationsResponse}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.{EoriResultBuilder, StubDataServiceHelper}

class StubDataService @Inject() (
  myEoriResultBuilder: EoriResultBuilder,
  zonedDateService:    ZonedDateTimeService
) extends StubDataServiceHelper(zonedDateService) {

  def stubbing(req: Request[JsValue]): Result =
    req.body.validate[EisAuthorisationRequest] match {
      case JsSuccess(checkerReq: EisAuthorisationRequest, _) =>
        checkIfMockData(checkerReq.eoris)
      case _ => InternalServerError(Json.toJson(res500))
    }

  private def checkIfMockData(eoris: Seq[String]): Result =
    mockedEoriResponses
      .collectFirst { case (mockEori, result) if eoris.contains(mockEori) => result }
      .getOrElse {
        val res = EisAuthorisationsResponse(
          zonedDateService.now(),
          results = myEoriResultBuilder.makeResults(eoris)
        )
        Ok(Json.toJson(res))
      }
}
