package uk.gov.hmrc.uknwauthcheckerapistub.models.requests

import play.api.libs.json.{Json, OFormat}

case class ApiCheckerRequest(date: String, auth: String, eoris: Seq[String])

object ApiCheckerRequest {
  implicit val format: OFormat[ApiCheckerRequest] = Json.format[ApiCheckerRequest]
}