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

import play.api.libs.json.JsValue
import play.api.mvc.Request
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants

trait HeaderValidator {

  private def validateBearerToken(token: Seq[String]): Boolean =
    token.contains(Constants.bearerToken)

  def hasValidBearerToken(request: Request[JsValue]): Boolean = {
    val valid = request.headers.headers.filter(_._1.toLowerCase.contains("authorization"))

    if (valid.nonEmpty && validateBearerToken(valid.map(_._2))) { true }
    else { false }
  }

}
