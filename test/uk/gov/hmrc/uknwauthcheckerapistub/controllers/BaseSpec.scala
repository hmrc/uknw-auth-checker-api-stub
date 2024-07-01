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
import play.api.test.FakeRequest
import play.api.test.Helpers.POST

import scala.io.Source

class BaseSpec extends  AnyWordSpec with Matchers {

  private val basePath = "conf/resources/stubJsons/"
  val fakePostReq = FakeRequest(POST, "/authorisations").withHeaders("Content-Type" -> "application/json")

  def getJsonFile(fileName: String): JsValue = {
    val source = Source.fromFile(basePath ++ fileName)
    val lines = try Json.parse(source.mkString) finally source.close()
    lines
  }





}
