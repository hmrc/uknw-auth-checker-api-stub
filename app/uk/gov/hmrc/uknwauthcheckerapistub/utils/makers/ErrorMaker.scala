package uk.gov.hmrc.uknwauthcheckerapistub.utils.makers

import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.ErrorDetails

import java.time.{LocalDate, ZonedDateTime}

class ErrorMaker {
  def makeMessage(date: Either[String, LocalDate], authType: Either[String, String], eoris: Either[List[String], List[String]]): String = {

    val badEori: String = eoris match {
      case Left(value) => value.foldRight("Invalid format of EORI(s):")((anEori, acc) => acc ++ anEori ++ ",")
      case _           => ""
    }

    val badDate = date match {
      case Left(value) if badEori == "" => s"Invalid supplied date(Date format should be - YYYY-MM-DD) : $value"
      case Left(value) if badEori != "" => s",Invalid supplied date(Date format should be - YYYY-MM-DD) : $value"
      case _                            => ""
    }

    val badAuthType = authType match {
      case Left(value) if badEori == "" || badDate == "" => s"Invalid authorisation type :$value"
      case Left(value) if badEori != "" || badDate != "" => s",Invalid authorisation type :$value"
      case _                                             => ""
    }
    s"""{
       |  "errorDetail": {
       |    "timestamp": "2024-01-15T11:48:10.392967Z",
       |    "errorCode": 400,
       |    "errorMessage": "${badEori.dropRight(1)}$badDate$badAuthType",
       |    "sourcePDSFaultDetails": "/pds/cnit/validatecustomsauth/v1"
       |  }
       |}""".stripMargin
  }
  
  def makeError(date: Either[String,ZonedDateTime], eoris: Either[Seq[String],Seq[String]]): ErrorDetails = ???
}