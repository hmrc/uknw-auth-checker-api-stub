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

package uk.gov.hmrc.uknwauthcheckerapistub.tools.helpers

import java.time.LocalDate
import java.time.format.DateTimeFormatter

trait BodyCheker {
  val goodEoris: List[String] = List("GB0000000200", "XI0000000200")

  def checkDate(date: String): Either[String, LocalDate] =
    try {
      val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
      val parsedDate = LocalDate.parse(date, dtf)
      Right(parsedDate)
    } catch {
      case _: Throwable => Left(date)
    }

  def checkAuthType(authType: String): Either[String, String] =
    if (authType == "UKNW") Right(authType) else Left(authType)

  def checkEori(eoris: List[String]): Either[List[String], List[String]] = {
    val badEoris: List[String] = eoris.foldRight(List[String]()) { (anEori, acc) =>
      if (!goodEoris.contains(anEori)) acc :+ anEori else acc
    }
    if (badEoris.nonEmpty) Left(badEoris) else Right(eoris)
  }

}
