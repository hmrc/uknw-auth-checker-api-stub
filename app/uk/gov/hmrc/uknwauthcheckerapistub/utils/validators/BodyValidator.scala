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

package uk.gov.hmrc.uknwauthcheckerapistub.utils.validators

import java.time.{LocalDate, ZonedDateTime}
import scala.util.matching.Regex

import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.ApiCheckerRequest
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{ErrorResponse, StubResponse}
import uk.gov.hmrc.uknwauthcheckerapistub.services.ZonedDateTimeService
import uk.gov.hmrc.uknwauthcheckerapistub.utils.makers.{ErrorMaker, OkMaker}

class BodyValidator {
  private val myErrorMaker:     ErrorMaker           = new ErrorMaker
  private val myOkMaker:        OkMaker              = new OkMaker
  private val zonedDateService: ZonedDateTimeService = new ZonedDateTimeService

  def checkRequest(body: ApiCheckerRequest): Either[ErrorResponse, StubResponse] = {
    val isDateValid   = checkDate(body.date)
    val areEorisRight = checkEori(body.eoris)

    (isDateValid, areEorisRight) match
      case (Right(date), Right(eoris)) => Right(StubResponse(zonedDateService.now(), results = myOkMaker.makeResults(eoris)))
      case _                           => Left(ErrorResponse(myErrorMaker.makeError(isDateValid, areEorisRight)))

  }

  def checkDate(stringDate: String): Either[String, LocalDate] =
    try
      Right(LocalDate.parse(stringDate))
    catch {
      case _: Throwable => Left(s"Invalid supplied date(Date format should be - YYYY-MM-DD) : $stringDate")
    }

  def checkEori(eorisSeq: Seq[String]): Either[Seq[String], Seq[String]] = {

    val eoriPattern: Regex       = "^(GB|XI)[0-9]{12}|(GB|XI)[0-9]{15}$".r
    val badEoris:    Seq[String] = eorisSeq.distinct.filterNot(anEori => eoriPattern.findFirstIn(anEori).isDefined)

    badEoris.isEmpty match
      case true => Right(eorisSeq.distinct)
      case _    => Left(badEoris)

  }

}
