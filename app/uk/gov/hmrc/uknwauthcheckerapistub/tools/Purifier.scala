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

package uk.gov.hmrc.uknwauthcheckerapistub.tools

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.uknwauthcheckerapistub.models.{Eori, ExpectedEoriPayload}

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Purifier {
  val goodEoris: List[String] = List("GB0000000200", "XI0000000200")

  def purify(body: JsValue): Either[JsValue, Eori] = {

    val rawEori  = body.validate[ExpectedEoriPayload].get
    val date     = checkDate(rawEori.date)
    val authType = checkAuthType(rawEori.authType)
    val eoris    = checkEori(rawEori.eoris)

    (date, authType, eoris) match {
      case (Right(x), Right(y), Right(z)) => Right(Eori(x, y, z))
      case _ =>
        val res = Json.parse(composeError(date, authType, eoris))
        Left(res)
    }

  }

  def composeError(date: Either[String, LocalDate], authType: Either[String, String], eoris: Either[List[String], List[String]]): String = {
    val badEori = eoris match {
      case Left(value) => value.foldRight("Invalid format of EORI(s):")((anEori, acc) => acc ++ anEori ++ ",")
      case _           => ""
    }

    val badDate = date match {
      case Left(value) => s"Invalid supplied date(Date format should be - YYYY-MM-DD) : $value"
      case _           => ""
    }

    val badAuthType = authType match {
      case Left(value) => s"Invalid authorisation type :$value"
      case _           => ""
    }
    s"""{
      |  "errorDetail": {
      |    "timestamp": "2024-01-15T11:48:10.392967Z",
      |    "errorCode": 400,
      |    "errorMessage": "${badEori.dropRight(1)}$badDate,$badAuthType",
      |    "sourcePDSFaultDetails": "/pds/cnit/validatecustomsauth/v1"
      |  }
      |}""".stripMargin
  }

  private def checkDate(date: String): Either[String, LocalDate] =
    try {
      val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
      val parsedDate = LocalDate.parse(date, dtf)
      Right(parsedDate)
    } catch {
      case _: Throwable => Left(date)
    }

  private def checkAuthType(authType: String): Either[String, String] =
    if (authType == "UKNW") Right(authType) else Left(authType)

  private def checkEori(eoris: List[String]): Either[List[String], List[String]] = {
    val badEoris: List[String] = eoris.foldRight(List[String]()) { (anEori, acc) =>
      if (!goodEoris.contains(anEori)) acc :+ anEori else acc
    }
    if (badEoris.nonEmpty) Left(badEoris) else Right(eoris)
  }

}
