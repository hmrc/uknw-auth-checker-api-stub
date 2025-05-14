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

package uk.gov.hmrc.uknwauthcheckerapistub.models

trait ReservedEoris {
  val mock403Eori:                     String = "GB999999999999403"
  val mock500Eori:                     String = "GB999999999999500"
  val mock503Eori:                     String = "GB999999999999503"
  val mockEmptyAuthTypeEori:           String = "GB992154091220"
  val mockEmptyDateAndAuthTypeEori:    String = "GB992692280526"
  val mockEmptyDateEori:               String = "GB992697819786102"
  val mockEmptyResponseEori:           String = "GB999999999999601"
  val mockEmptyResultsAndAuthTypeEori: String = "GB999999999999604"
  val mockEmptyResultsAndDateEori:     String = "GB999999999999603"
  val mockEmptyResultsEori:            String = "GB999999999999602"

  val mockedEoris: Seq[String] = Seq(
    mock403Eori,
    mock500Eori,
    mock503Eori,
    mockEmptyAuthTypeEori,
    mockEmptyDateAndAuthTypeEori,
    mockEmptyDateEori,
    mockEmptyResponseEori,
    mockEmptyResultsAndAuthTypeEori,
    mockEmptyResultsAndDateEori,
    mockEmptyResultsEori
  )
}
