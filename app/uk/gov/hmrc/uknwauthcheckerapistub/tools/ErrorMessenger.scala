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

import java.time.LocalDate

class ErrorMessenger {

  def makeMessage(date: Either[String, LocalDate], authType: Either[String, String], eoris: Either[List[String], List[String]]): String = {
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
}
