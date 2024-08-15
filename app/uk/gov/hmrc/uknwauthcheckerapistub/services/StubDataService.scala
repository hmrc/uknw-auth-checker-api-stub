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

package uk.gov.hmrc.uknwauthcheckerapistub.services

import play.api.libs.json.{JsSuccess, JsValue, Json}
import play.api.mvc.Results.*
import play.api.mvc.{Request, Result}
import uk.gov.hmrc.uknwauthcheckerapistub.models.requests.ApiCheckerRequest
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{ErrorDetails, ErrorResponse, StubResponse}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.makers.OkMaker

class StubDataService {

  private val myOkMaker:        OkMaker              = new OkMaker
  private val zonedDateService: ZonedDateTimeService = new ZonedDateTimeService

  def stubbing(req: Request[JsValue]): Result =
    req.body.validate[ApiCheckerRequest] match {
      case JsSuccess(checkerReq: ApiCheckerRequest, _) =>
        val res = StubResponse(zonedDateService.now(), results = myOkMaker.makeResults(checkerReq.eoris))
        Ok(Json.toJson(res))

      case _ =>
        val res = ErrorResponse(ErrorDetails(zonedDateService.now().toString, 500, "An internal error has occurred"))
        InternalServerError(Json.toJson(res))
    }
}
