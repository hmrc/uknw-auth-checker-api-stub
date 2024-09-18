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
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{EisAuthorisationResponseError, EisAuthorisationsResponse, ErrorDetails}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants.{body503, mock403Eori, mock500Eori, mock503Eori}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.EoriResultBuilder

class StubDataService @Inject() (myEoriResultBuilder: EoriResultBuilder, zonedDateService: ZonedDateTimeService) {

  private lazy val res500 = EisAuthorisationResponseError(ErrorDetails(zonedDateService.now().toString, 500, "An internal error has occurred"))

  def stubbing(req: Request[JsValue]): Result =
    req.body.validate[EisAuthorisationRequest] match {
      case JsSuccess(checkerReq: EisAuthorisationRequest, _) =>
        checkIfMockData(checkerReq.eoris)

      case _ => InternalServerError(Json.toJson(res500))
    }

  private def checkIfMockData(eoris: Seq[String]): Result =
    eoris match {
      case x if x.contains(mock403Eori) => Forbidden
      case x if x.contains(mock500Eori) => InternalServerError(Json.toJson(res500))
      case x if x.contains(mock503Eori) => ServiceUnavailable(body503)
      case x =>
        val res = EisAuthorisationsResponse(zonedDateService.now(), results = myEoriResultBuilder.makeResults(eoris))
        Ok(Json.toJson(res))

      case null => InternalServerError(Json.toJson(res500))
    }

}
