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

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.uknwauthcheckerapistub.services.LocalDateService

import java.time.LocalTime
import scala.io.Source

trait JsonGetter extends TokenReplacer {
  def getJsonFile(fileName: String)(implicit localDateService: LocalDateService): JsValue = {
    val source = Source.fromResource(fileName)
    val lines =
      try
        Json.parse(
          source.mkString
            .replaceFormattedDate(localDateService.now())
            .replaceFormattedDateTime(localDateService.now().atTime(LocalTime.MIDNIGHT))
        )
      finally source.close()
    lines
  }
}
