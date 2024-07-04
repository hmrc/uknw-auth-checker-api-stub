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
import uk.gov.hmrc.uknwauthcheckerapistub.tools.helpers.BodyCheker

class Sanitiser extends BodyCheker {
  private val myErrorComposer = new ErrorMessenger
  private val myOkComposer    = new OkMessenger

  def sanitise(body: JsValue): Either[JsValue, JsValue] = {

    val rawEori  = body.validate[ExpectedEoriPayload].get
    val date     = checkDate(rawEori.date)
    val authType = checkAuthType(rawEori.authType)
    val eoris    = checkEori(rawEori.eoris)

    (date, authType, eoris) match {
      case (Right(x), Right(y), Right(z)) => Right(myOkComposer.makeMessage(Eori(x, y, z)))
      case _ =>
        val res = Json.parse(myErrorComposer.makeMessage(date, authType, eoris))
        Left(res)
    }

  }

}
