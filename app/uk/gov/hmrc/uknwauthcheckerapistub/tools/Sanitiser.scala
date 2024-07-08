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
import uk.gov.hmrc.uknwauthcheckerapistub.tools.helpers.JsonGetter

class Sanitiser extends JsonGetter {

  def sanitise(body: JsValue): Either[JsValue, JsValue] = {

    val rawEori = body

    rawEori match {
      case x if x == getJsonFile("requests/authRequest200_multiple.json") => Right(getJsonFile("responses/eisAuthResponse200_valid_multiple.json"))
      case x if x == getJsonFile("requests/authRequest200_single.json")   => Right(getJsonFile("responses/eisAuthResponse200_valid_single.json"))
      case x if x == getJsonFile("requests/authRequest400_multiple.json") => Left(getJsonFile("responses/eisAuthResponse400_multiple.json"))
      case x if x == getJsonFile("requests/authRequest400_single.json")   => Left(getJsonFile("responses/eisAuthResponse400_single.json"))
      case _ =>
        val fourthWall: JsValue =
          Json.parse(s"""{
                        |"StubError": "The request sent doesn't match any of the stubbed cases"
                        |}""".stripMargin)
        Left(fourthWall)

    }

  }

}
