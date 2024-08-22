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

import uk.gov.hmrc.uknwauthcheckerapistub.EoriGenerator
import uk.gov.hmrc.uknwauthcheckerapistub.controllers.BaseSpec
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants.authorisedEoris

class EoriResultBuilderSpec extends BaseSpec with EoriGenerator {

  private val eoriResultBuilder: EoriResultBuilder = new EoriResultBuilder()

  "eoriResultBuilder" should {
    "produce a list of EoriResults" in {
      val randomNumber = fetchRandomNumber(1, authorisedEoris.size)
      val eoris        = useEoriGenerator(randomNumber)

      val results = eoriResultBuilder.makeResults(eoris)

      results.size shouldBe randomNumber
    }

    "produce a distinct list of EoriResults" in {
      val eori  = useEoriGenerator(1).head
      val eoris = Seq(eori, eori)

      val results = eoriResultBuilder.makeResults(eoris)

      results.size shouldBe 1
    }

    "produce a list of EoriResults with valid set to true if the eori is in the authorisedEoris list" in {
      val eori = authorisedEoris.head

      val results = eoriResultBuilder.makeResults(Seq(eori))

      results.head.valid shouldBe true
    }

    "produce a list of EoriResults with valid set to false if the eori is not in the authorisedEoris list" in {
      val eori = useGarbageGenerator(1).head

      val results = eoriResultBuilder.makeResults(Seq(eori))

      results.head.valid shouldBe false
    }

  }
}
