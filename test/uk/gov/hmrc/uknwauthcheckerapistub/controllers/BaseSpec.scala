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
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.api.test.Helpers.POST

import scala.io.Source

class BaseSpec extends AnyWordSpec with Matchers {

  private val basePath = "conf/resources/stubJsons/"
  val validHeaders:    Seq[(String, String)] = Seq("authorization" -> "Bearer <VALID_TOKEN>", "Content-Type" -> "application/json")
  val invalidHeaders1: Seq[(String, String)] = Seq("Content-Type" -> "application/json")
  val invalidHeaders2: Seq[(String, String)] = Seq("authorization" -> "Bearer <FORBIDDEN>", "Content-Type" -> "application/json")

  val fakePostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "/authorisations").withHeaders(validHeaders: _*)

  val fakePostReqForbiddenHeader1: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "/authorisations").withHeaders(invalidHeaders1: _*)

  val fakePostReqForbiddenHeader2: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "/authorisations").withHeaders(invalidHeaders2: _*)

  val fakeHeadlessPostReq: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest(POST, "/authorisations")

  def getJsonFile(fileName: String): JsValue = {
    val source = Source.fromFile(basePath ++ fileName)
    val lines =
      try Json.parse(source.mkString)
      finally source.close()
    lines
  }

}
