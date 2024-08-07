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

package uk.gov.hmrc.uknwauthcheckerapistub.utils

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime, ZoneId}

trait TokenReplacer {
  implicit class TokenReplacer(value: String) {

    private val dateTimeFormat: String            = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private val formatter:      DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat)
    private val utcZone:        String            = "UTC"
    private val authType:       String            = "UKNW"
    private val utcZoneId = ZoneId.of(utcZone)

    def replaceFormattedDate(date: LocalDate): String =
      value.replace("{{date}}", date.format(DateTimeFormatter.ISO_LOCAL_DATE))

    def replaceFormattedDateTime(dateTime: LocalDateTime): String =
      value.replace("{{dateTime}}", formatter.format(dateTime.atZone(utcZoneId)))

    def replaceAuthType: String = value.replace("{{authType}}", authType)

    def replaceEoris(eoris: String): String =
      value.replace("{{eoris}}", eoris)

    def replaceEori(eori: String): String =
      value.replace("{{eori}}", eori)

    def replaceResults(results: String): String =
      value.replace("{{results}}", results)
  }
}
