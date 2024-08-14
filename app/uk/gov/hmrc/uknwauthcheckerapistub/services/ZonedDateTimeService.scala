package uk.gov.hmrc.uknwauthcheckerapistub.services

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalTime, ZoneId, ZonedDateTime}

class ZonedDateTimeService {
  def now(): ZonedDateTime = ZonedDateTime.of(LocalDate.now.atTime(LocalTime.MIDNIGHT), ZoneId.of("UTC"))

}
