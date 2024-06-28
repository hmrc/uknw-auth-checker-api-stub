package uk.gov.hmrc.uknwauthcheckerapistub.tools

import play.api.libs.json.{JsValue, Json}

trait Helper {

  val defaultEORI: JsValue = Json.parse(
    """
      |{
      |  "processingDate": "2024-02-08T14:30:36.307Z",
      |  "authType": "UKIM",
      |  "results": [
      |    {
      |      "eori": "GB0000000001",
      |      "valid": false,
      |      "code": 1
      |    },
      |    {
      |      "eori": "XI9999999999",
      |      "valid": false,
      |      "code": 1
      |    }
      |  ]
      |}
      |""".stripMargin)

  val anotherEORI: JsValue = Json.parse(
    """
      |{
      |  "processingDate": "2024-02-08T14:30:36.307Z",
      |  "authType": "UKIM",
      |  "results": [
      |    {
      |      "eori": "GB0000000001",
      |      "valid": false,
      |      "code": 1
      |    },
      |    {
      |      "eori": "XI9999999999",
      |      "valid": true,
      |      "code": 1
      |    }
      |  ]
      |}
      |""".stripMargin)

}
