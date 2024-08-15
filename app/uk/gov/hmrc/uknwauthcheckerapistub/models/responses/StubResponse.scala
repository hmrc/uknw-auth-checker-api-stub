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

package uk.gov.hmrc.uknwauthcheckerapistub.models.responses

import java.time.ZonedDateTime
import play.api.libs.json.{Json, OFormat, Writes}
import uk.gov.hmrc.uknwauthcheckerapistub.models.Iso8601DateTimeWrites

case class EoriResults(eori: String, valid: Boolean, code: Int)
object EoriResults {
  implicit val format: OFormat[EoriResults] = Json.format[EoriResults]
}

case class StubResponse(processingDate: ZonedDateTime, authType: String = "UKNW", results: Seq[EoriResults])
object StubResponse {
  implicit val zonedDateTimeWrites: Writes[ZonedDateTime] = Iso8601DateTimeWrites.iso8601DateTimeWrites

  implicit val format: OFormat[StubResponse] = Json.format[StubResponse]
}
