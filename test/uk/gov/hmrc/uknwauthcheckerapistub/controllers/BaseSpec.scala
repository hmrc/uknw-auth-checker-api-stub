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

import java.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.JsValue
import play.api.mvc.AnyContentAsEmpty
import play.api.test.{DefaultAwaitTimeout, FakeHeaders, FakeRequest}
import play.api.test.Helpers.{GET, POST}
import uk.gov.hmrc.uknwauthcheckerapistub.services.LocalDateService
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.HeaderNames

import scala.reflect.ClassTag

class BaseSpec
    extends AnyWordSpec
    with Matchers
    with TestDataUtils
    with GuiceOneAppPerSuite
    with HeaderNames {

  protected implicit val mockLocalDateService: LocalDateService = mock[LocalDateService]
  protected def injected[T](implicit evidence: ClassTag[T]): T = app.injector.instanceOf[T]

  when(mockLocalDateService.now()).thenReturn(LocalDate.now)

  val endPointUrl = "/cau/validatecustomsauth/v1"

  def createRequest(headers: Seq[(String, String)] = validHeaders, body: JsValue): FakeRequest[JsValue] =
    FakeRequest(POST, endPointUrl, FakeHeaders(headers), body)

  val fakePostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(validHeaders*)

  val fakePostErrorHandling: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "")

  val fake404PostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "/xyz").withHeaders(validHeaders*)

  val fakePostReqForbiddenHeader1: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(invalidHeaders1*)

  val fakePostReqForbiddenHeader2: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(invalidHeaders2*)

  val fakeHeadlessPostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl)

  val fakeNoBodyPostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, endPointUrl).withHeaders(validHeaders*)

  val fakeGetRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, endPointUrl).withHeaders(validHeaders*)

}
