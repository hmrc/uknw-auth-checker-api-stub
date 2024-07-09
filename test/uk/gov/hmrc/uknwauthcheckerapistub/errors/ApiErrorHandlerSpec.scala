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

package uk.gov.hmrc.uknwauthcheckerapistub.errors

import play.api.http.Status._
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import uk.gov.hmrc.auth.core.AuthorisationException
import uk.gov.hmrc.http.HttpVerbs.POST
import uk.gov.hmrc.http.{JsValidationException, NotFoundException}
import uk.gov.hmrc.uknwauthcheckerapistub.controllers.BaseSpec

class ApiErrorHandlerSpec extends BaseSpec {

  private val apiErrorHandler = new ApiErrorHandler()
  private val errorMessage    = "ErrorMessage"

  "onClientError" should {
    "convert a BAD_REQUEST to Bad Request (400) response" in {
      val result = apiErrorHandler.onClientError(fakePostErrorHandling, BAD_REQUEST, errorMessage)

      status(result) shouldEqual BAD_REQUEST
    }

    "convert a FORBIDDEN to Forbidden (403) response" in {
      val result = apiErrorHandler.onClientError(fakePostReq, FORBIDDEN, errorMessage)

      status(result) shouldEqual FORBIDDEN
    }

    "convert a INTERNAL_SERVER_ERROR to Internal Server Error (500) response" in {
      val result = apiErrorHandler.onClientError(fakePostReq, INTERNAL_SERVER_ERROR, errorMessage)

      status(result) shouldEqual INTERNAL_SERVER_ERROR
    }

    "convert a METHOD_NOT_ALLOWED to Method Not Allowed (405) response" in {
      val result = apiErrorHandler.onClientError(fakePostReq, METHOD_NOT_ALLOWED, errorMessage)

      status(result) shouldEqual METHOD_NOT_ALLOWED
    }

    "convert a NOT_FOUND to Not Found (404) response" in {
      val result = apiErrorHandler.onClientError(fake404PostReq, NOT_FOUND, errorMessage)

      status(result) shouldEqual NOT_FOUND
    }

    "convert a NOT_FOUND with /cau/validatecustomsauth/v1 url to to Method Not Allowed (405) response" in {
      val result = apiErrorHandler.onClientError(FakeRequest(POST, endPointUrl), NOT_FOUND, errorMessage)

      status(result) shouldEqual METHOD_NOT_ALLOWED
    }
  }

  "onServerError" should {
    case class TestAuthorisationException(msg: String = errorMessage) extends AuthorisationException(msg)

    "convert a AuthorisationException to Unauthorized response" in {
      val authorisationException = new TestAuthorisationException

      val result = apiErrorHandler.onServerError(fakePostReq, authorisationException)

      status(result) shouldEqual UNAUTHORIZED
    }

    "convert a JsonValidationException to a Bad Request" in {
      val jsValidationException = new JsValidationException("method", "url", classOf[String], "errors")

      val result = apiErrorHandler.onServerError(fakePostReq, jsValidationException)

      status(result) shouldBe BAD_REQUEST
    }

    "convert a NotFoundException to Not Found response" in {
      val notfoundException = new NotFoundException(errorMessage)

      val result = apiErrorHandler.onServerError(fakePostReq, notfoundException)

      status(result) shouldEqual NOT_FOUND
    }

    "convert a RuntimeException to Internal Server Error response" in {
      val runtimeException = new RuntimeException(errorMessage)

      val result = apiErrorHandler.onServerError(fakePostReq, runtimeException)

      status(result) shouldEqual INTERNAL_SERVER_ERROR
    }
  }
}
