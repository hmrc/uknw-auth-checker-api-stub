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

package uk.gov.hmrc.uknwauthcheckerapistub.utils.makers

import java.time.ZonedDateTime

import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.ErrorDetails
import uk.gov.hmrc.uknwauthcheckerapistub.services.LocalDateService

class ErrorMaker {
  private val localDateService: LocalDateService = new LocalDateService
  
  private def makeMessage(date: Either[String, ZonedDateTime], eoris: Either[Seq[String], Seq[String]]): String = {

    val badEori: String = eoris match {
      case Left(value) => value.foldRight("Invalid format of EORI(s):")((anEori, acc) => acc ++ anEori ++ ",")
      case _           => ""
    }

    val badDate = date match {
      case Left(value) if badEori == "" => s"Invalid supplied date(Date format should be - YYYY-MM-DD) : $value"
      case Left(value) if badEori != "" => s",Invalid supplied date(Date format should be - YYYY-MM-DD) : $value"
      case _                            => ""
    }

    badEori.dropRight(1) + badDate
  }

  def makeError(date: Either[String, ZonedDateTime], eoris: Either[Seq[String], Seq[String]]): ErrorDetails = {
    ErrorDetails(localDateService.now().toString, 400, makeMessage(date, eoris))
  }
}
