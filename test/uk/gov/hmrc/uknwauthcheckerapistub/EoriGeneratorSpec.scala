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

package uk.gov.hmrc.uknwauthcheckerapistub

import uk.gov.hmrc.uknwauthcheckerapistub.controllers.BaseSpec
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants.{authorisedEoris, eoriPattern}

class EoriGeneratorSpec extends BaseSpec with EoriGenerator {
  "eoriGenerator" should {

    "generate a chosen sized list of authorised eoris" in {
      val numberOfEoris: Int = fetchRandomNumber(1, authorisedEoris.size)
      val eoris = useEoriGenerator(numberOfEoris)

      eoris.size shouldBe numberOfEoris

      eoris.forall(eori => authorisedEoris.contains(eori)) shouldBe true

      eoris.distinct.size shouldBe numberOfEoris
    }

    "generate all unique authorised eoris" in {

      val eoris = useEoriGenerator(authorisedEoris.size)

      eoris.size shouldBe authorisedEoris.size

      eoris.forall(eori => authorisedEoris.contains(eori)) shouldBe true

      eoris.distinct.size shouldBe authorisedEoris.size
    }

    "generate invalid eoris" in {
      val numberOfEoris: Int = fetchRandomNumber(1, authorisedEoris.size)
      val eoris = useEoriGenerator(numberOfEoris, Some(0))

      eoris.size shouldBe numberOfEoris

      eoris.forall(eori => authorisedEoris.contains(eori)) shouldBe false

      eoris.distinct.size shouldBe numberOfEoris
    }

    "generate a chosen sized list of invalid eoris" in {

      val numberOfEoris: Int = fetchRandomNumber(1, authorisedEoris.size)
      val eoris = useEoriGenerator(numberOfEoris, Some(0))

      eoris.size shouldBe numberOfEoris

      eoris.forall(eori => authorisedEoris.contains(eori)) shouldBe false

      eoris.distinct.size shouldBe numberOfEoris
    }

    "generate a chosen sized list of authorised eoris and invalid eoris" in {
      val numberOfEoris: Int = fetchRandomNumber(1, authorisedEoris.size)
      val validEoris:    Int = fetchRandomNumber(1, numberOfEoris - 1)

      val eoris: Seq[String] = useEoriGenerator(numberOfEoris, Some(validEoris))

      val extractedAuthorisedEoris: Seq[String] = eoris.filter(authorisedEoris.contains)
      val extractedInvalidEoris:    Seq[String] = eoris.filter(!authorisedEoris.contains(_))

      extractedAuthorisedEoris.size shouldBe validEoris

      extractedInvalidEoris.size shouldBe numberOfEoris - validEoris

      extractedInvalidEoris.forall(eori => eori.matches(eoriPattern)) shouldBe true

      extractedAuthorisedEoris.forall(eori => authorisedEoris.contains(eori)) shouldBe true

      eoris.distinct.size shouldBe numberOfEoris
    }

    "throw an exception with message Number of authorised EORIs cannot be greater than the total number of EORIs" in {
      val exception = intercept[IllegalArgumentException] {
        useEoriGenerator(1, Some(100))
      }

      exception.getMessage shouldBe "Number of authorised EORIs cannot be greater than the total number of EORIs"
    }

    "throw an exception when authorised eoris exceed the authorised EORI test set size" in {
      val exception = intercept[IllegalArgumentException] {
        useEoriGenerator(authorisedEoris.size + 2, Some(authorisedEoris.size + 1))
      }

      exception.getMessage shouldBe s"Number of authorised EORIs cannot be greater than the total number of authorised EORIs within the authorised EORI test set (${authorisedEoris.size})"
    }

    "throw an exception when numberOfAuthorisedEoris exceeds the authorised EORI test set size using single argument" in {
      val exception = intercept[IllegalArgumentException] {
        useEoriGenerator(authorisedEoris.size + 1)
      }

      exception.getMessage shouldBe s"Number of authorised EORIs cannot be greater than the total number of authorised EORIs within the authorised EORI test set (${authorisedEoris.size})"
    }
  }
}
