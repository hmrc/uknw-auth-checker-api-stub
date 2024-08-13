package uk.gov.hmrc.uknwauthcheckerapistub.models.responses

import play.api.libs.json.{Json, OFormat}

import java.time.ZonedDateTime

case class EoriResults(eori: String, valid: Boolean, code: Int)
object EoriResults {
  implicit val format: OFormat[EoriResults] = Json.format[EoriResults]
}

case class StubResponse (date: ZonedDateTime , auth: String = "UKNW", results: Seq[EoriResults])
object StubResponse {
  implicit val format: OFormat[StubResponse] = Json.format[StubResponse]
}
