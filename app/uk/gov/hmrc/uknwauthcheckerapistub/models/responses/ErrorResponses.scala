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

package uk.gov.hmrc.uknwauthcheckerapistub.models.responses

object ErrorResponses {
  val expectedRes400_singleEori: String =
    """{"code":"BAD_REQUEST","message":"Bad request","errors":[{"code":"INVALID_FORMAT","message":"ABCD000000000200 is not a supported EORI number","path":"eoris"}]}"""
  val expectedRes400_multipleEori: String =
    """{"code":"BAD_REQUEST","message":"Bad request","errors":[{"code":"INVALID_FORMAT","message":"ABCD000000000200 is not a supported EORI number","path":"eoris"},{"code":"INVALID_FORMAT","message":"EFGH000000000200 is not a supported EORI number","path":"eoris"}]}"""
  val expectedRes400_missingEori: String =
    """{"code":"BAD_REQUEST","message":"Bad request","errors":[{"code":"INVALID_FORMAT","message":"eoris field missing from JSON","path":"eoris"}]}"""
  val expectedRes400_wrongNumberOfEoris: String =
    """{"code":"BAD_REQUEST","message":"Bad request","errors":[{"code":"INVALID_FORMAT","message":"The request payload must contain between 1 and 3000 EORI entries","path":"eoris"}]}"""
  val expectedRes403_forbidden: String =
    """{"code":"FORBIDDEN","message":"You are not allowed to access this resource"}"""
  val expectedRes500: String = """{"code":"INTERNAL_SERVER_ERROR","message":"Unexpected internal server error"}"""
}
