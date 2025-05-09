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

import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results.*
import uk.gov.hmrc.uknwauthcheckerapistub.models.ReservedEoris
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{EisAuthorisationResponseError, EisAuthorisationsResponse, EoriResults, ErrorDetails}
import uk.gov.hmrc.uknwauthcheckerapistub.services.ZonedDateTimeService
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants.body503

import java.time.ZonedDateTime

trait StubDataServiceHelper(zonedDateService: ZonedDateTimeService, myEoriResultBuilder: EoriResultBuilder) extends ReservedEoris {

  protected lazy val res500: EisAuthorisationResponseError = EisAuthorisationResponseError(
    ErrorDetails(zonedDateService.now().toString, 500, "An internal error has occurred")
  )
  val authType       = Some("UKNW")
  val processingDate = Some(zonedDateService.now())

  def checkForMockedEoriResponses(eoris: Seq[String]): Map[String, Result] = {
    val results = Some(myEoriResultBuilder.makeResults(eoris))
    Map(
      mock403Eori                     -> Forbidden,
      mock500Eori                     -> InternalServerError(Json.toJson(res500)),
      mock503Eori                     -> ServiceUnavailable(body503),
      mockEmptyResponseEori           -> Ok(generateEisResponse(processingDate = None, authType = None, results = None)),
      mockEmptyDateEori               -> Ok(generateEisResponse(processingDate = None, authType = authType, results = results)),
      mockEmptyAuthTypeEori           -> Ok(generateEisResponse(processingDate = processingDate, authType = None, results = results)),
      mockEmptyResultsEori            -> Ok(generateEisResponse(processingDate = processingDate, authType = authType, results = None)),
      mockEmptyResultsAndDateEori     -> Ok(generateEisResponse(processingDate = None, authType = authType, results = None)),
      mockEmptyResultsAndAuthTypeEori -> Ok(generateEisResponse(processingDate = processingDate, authType = None, results = None)),
      mockEmptyDateAndAuthTypeEori    -> Ok(generateEisResponse(processingDate = None, authType = None, results = results))
    )
  }

  private def generateEisResponse(processingDate: Option[ZonedDateTime], authType: Option[String], results: Option[Seq[EoriResults]]) =
    Json.toJson(
      EisAuthorisationsResponse(processingDate = processingDate, authType = authType, results = results)
    )
}
