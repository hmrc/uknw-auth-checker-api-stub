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

import play.api.libs.json.JsValue
import play.api.mvc.Results._
import play.api.mvc.{AnyContent, Request, Result}
import uk.gov.hmrc.uknwauthcheckerapistub.tools.helpers.JsonGetter

class StubDataService extends JsonGetter {

  def stubbing(req: Request[AnyContent]): Result = {

    val rawEori: Option[JsValue] = req.body.asJson

    rawEori match {
      case Some(x) if x == getJsonFile("requests/authRequest200_multiple.json") => Ok(getJsonFile("responses/eisAuthResponse200_valid_multiple.json"))
      case Some(x) if x == getJsonFile("requests/authRequest200_single.json")   => Ok(getJsonFile("responses/eisAuthResponse200_valid_single.json"))
      case Some(x) if x == getJsonFile("requests/authRequest400_wrongAll.json") =>
        BadRequest(getJsonFile("responses/eisAuthResponse400_wrongAll.json"))
      case Some(x) if x == getJsonFile("requests/authRequest400_wrongAuth.json") =>
        BadRequest(getJsonFile("responses/eisAuthResponse400_wrongAuth.json"))
      case Some(x) if x == getJsonFile("requests/authRequest400_wrongDate.json") =>
        BadRequest(getJsonFile("responses/eisAuthResponse400_wrongDate.json"))
      case Some(x) if x == getJsonFile("requests/authRequest400_wrongEori.json") =>
        BadRequest(getJsonFile("responses/eisAuthResponse400_wrongEori.json"))
      // This is a dummy test case just to trigger a 403 in test-api, it has nothing to do with the api-stub spec.
      case Some(x) if x == getJsonFile("dummies/authRequest403_api-test-only.json") =>
        Forbidden
      case _ => InternalServerError

    }
  }

}
