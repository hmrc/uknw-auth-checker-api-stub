package uk.gov.hmrc.uknwauthcheckerapistub.models.responses

import play.api.libs.json.{Json, OFormat}

case class ErrorDetails(timestamp: String, errorCode: Int, errorMessage: String, sourcePDSFaultDetails : String)
object ErrorDetails {
  implicit val format: OFormat[ErrorDetails] = Json.format[ErrorDetails]
}

case class ErrorResponse(errorDetails: ErrorDetails)
object ErrorResponse {
  implicit val format: OFormat[ErrorResponse] = Json.format[ErrorResponse]
}
