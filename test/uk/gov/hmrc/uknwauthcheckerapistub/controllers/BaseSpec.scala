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

import scala.reflect.ClassTag
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.HeaderNames
import play.api.libs.json.JsValue
import play.api.mvc.AnyContentAsEmpty
import play.api.test.Helpers.POST
import play.api.test.{FakeHeaders, FakeRequest}

class BaseSpec extends AnyWordSpec with Matchers with TestData with GuiceOneAppPerSuite with HeaderNames {

  protected def injected[T](implicit evidence: ClassTag[T]): T = app.injector.instanceOf[T]

  val endPointUrl = "/cau/validatecustomsauth/v1"

  def createRequest(method: String = "POST", headers: Seq[(String, String)] = validHeaders, body: JsValue): FakeRequest[JsValue] =
    FakeRequest(method, endPointUrl, FakeHeaders(headers), body)

  val fakePostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(validHeaders*)

  val fakePostErrorHandling: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "")

  val fake404PostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "/xyz").withHeaders(validHeaders*)

}
