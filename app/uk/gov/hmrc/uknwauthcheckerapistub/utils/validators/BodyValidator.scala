package uk.gov.hmrc.uknwauthcheckerapistub.utils.validators

import uk.gov.hmrc.uknwauthcheckerapistub.models.responses.{ErrorDetails, ErrorResponse}
import uk.gov.hmrc.uknwauthcheckerapistub.models.{ApiCheckerRequest, StubResponse}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.{ErrorMaker, OkMaker}

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZonedDateTime}
import scala.util.matching.Regex

class BodyValidator {
  val myErrorMaker: ErrorMaker = new ErrorMaker
  val myOkMaker: OkMaker = new OkMaker

  def checkRequest(body: ApiCheckerRequest): Either[ErrorResponse, StubResponse] = {
    val isDateValid = checkDate(body.date)
    val areEorisRight = checkEori(body.eoris)

    (isDateValid, areEorisRight) match
      case (Right(date), Right(eoris)) => Right(StubResponse(date,results = myOkMaker.makeOk(eoris)))
      case _ => Left(ErrorResponse(myErrorMaker.makeError(isDateValid,areEorisRight)))

  }

  def checkDate(stringDate: String): Either[String, ZonedDateTime] = {
    val formatter = DateTimeFormatter.ISO_DATE_TIME

    try {
      Right(ZonedDateTime.parse(stringDate, formatter))
    } catch {
      case _: Throwable => Left(s"Invalid supplied date(Date format should be - YYYY-MM-DD) : $stringDate")
    }
  }

  def checkEori(eorisSeq: Seq[String]): Either[Seq[String], Seq[String]] = {

    val eoriPattern: Regex = "^(GB|XI)[0-9]{12}|(GB|XI)[0-9]{15}$".r
    val badEoris: Option[Seq[String]] = Some(eorisSeq.distinct.filterNot(anEori => eoriPattern.findFirstIn(anEori).isDefined))

    badEoris match
      case Some(x) => Left(x)
      case _ => Right(eorisSeq.distinct)

  }

}
