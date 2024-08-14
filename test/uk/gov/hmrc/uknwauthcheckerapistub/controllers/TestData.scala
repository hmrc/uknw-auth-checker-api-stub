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

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.EoriResults

trait TestData {

  val validHeaders:    Seq[(String, String)] = Seq("authorization" -> "Bearer PFZBTElEX1RPS0VOPg==", "Content-Type" -> "application/json")
  val invalidHeaders1: Seq[(String, String)] = Seq("Content-Type" -> "application/json")
  val invalidHeaders2: Seq[(String, String)] = Seq("authorization" -> "Bearer <FORBIDDEN>", "Content-Type" -> "application/json")
  val invalidBody:     JsValue               = Json.parse("""{"invalid": "invalid"}""".mkString)

  // Request Data
  val eoriRq_1_valid: Seq[String] = Seq("GB837826880909874")
  val eoriRq_2_valid: Seq[String] = Seq("GB837826880909874", "GB000000000200")
  // Expected Data
  val eoriResult_1_valid: Seq[EoriResults] = Seq(EoriResults("GB837826880909874", true, 1))
  val eoriResult_2_valid: Seq[EoriResults] = Seq(EoriResults("GB837826880909874", true, 1), EoriResults("GB000000000200", false, 1))
}
