package uk.gov.hmrc.uknwauthcheckerapistub.utils

object Constants {
  val path        = "/cau/validatecustomsauth/v1"
  val bearerToken = "Bearer PFZBTElEX1RPS0VOPg=="
  val eisRequest: String =
    """
      |{
      |  "validityDate": "{{date}}",
      |  "authType": "UKNW",
      |  "eoris" : {{eoris}}
      |}
      |""".stripMargin
  val eisResponse: String =
    """
      |{
      |"processingDate": "{{dateTime}}",
      |"authType": "{{authType}}",
      |"results": {{results}}
      |}
      |""".stripMargin
  val eoriStatus: String =
    """
      |{
      |"eori": "{{eori}}",
      |"valid": true,
      |"code": 0
      |}
      |""".stripMargin
}
