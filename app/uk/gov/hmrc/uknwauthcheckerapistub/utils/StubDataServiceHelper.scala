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

package uk.gov.hmrc.uknwauthcheckerapistub.utils

import play.api.libs.json.{JsSuccess, JsValue, Json}
import play.api.mvc.Results.*
import play.api.mvc.{Request, Result}
import uk.gov.hmrc.uknwauthcheckerapistub.models.ReservedEoris
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.EisAuthorisationRequest
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{EisAuthorisationResponseError, EisAuthorisationsResponse, ErrorDetails}
import uk.gov.hmrc.uknwauthcheckerapistub.services.ZonedDateTimeService
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants.{body503, mock403Eori, mock500Eori, mock503Eori}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.EoriResultBuilder

trait StubDataServiceHelper(zonedDateService: ZonedDateTimeService) extends ReservedEoris {

  protected lazy val res500: EisAuthorisationResponseError = EisAuthorisationResponseError(
    ErrorDetails(zonedDateService.now().toString, 500, "An internal error has occurred")
  )

  protected val mockedEoriResponses: Map[String, Result] = Map(
    mock403Eori -> Forbidden,
    mock500Eori -> InternalServerError(Json.toJson(res500)),
    mock503Eori -> ServiceUnavailable(body503)
  )

}
