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

package uk.gov.hmrc.uknwauthcheckerapistub.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, POST}

class BaseSpec extends AnyWordSpec with Matchers with TestDataUtils {

  val endPointUrl = "/cau/validatecustomsauth/v1"

  val fakePostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(validHeaders: _*)

  val fakePostErrorHandling: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "")

  val fake404PostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "/xyz").withHeaders(validHeaders: _*)

  val fakePostReqForbiddenHeader1: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(invalidHeaders1: _*)

  val fakePostReqForbiddenHeader2: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(invalidHeaders2: _*)

  val fakeHeadlessPostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl)

  val fakeNoBodyPostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(validHeaders: _*)

  val fakeGetRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, endPointUrl).withHeaders(validHeaders: _*)

}
