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

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

import play.api.Logging
import play.api.http.HttpErrorHandler
import play.api.http.Status._
import play.api.mvc.Results.Status
import play.api.mvc.{RequestHeader, Result}
import uk.gov.hmrc.auth.core.AuthorisationException
import uk.gov.hmrc.http.{JsValidationException, NotFoundException}
import uk.gov.hmrc.uknwauthcheckerapistub.utils.Constants

@Singleton
class ApiErrorHandler @Inject() extends HttpErrorHandler, Logging {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logger.warn(
      s"[ApiErrorHandler][onClientError] error for (${request.method}) [${request.uri}] with status:" +
        s" $statusCode and message: $message"
    )

    Future.successful(
      statusCode match {
        case NOT_FOUND =>
          request.path match {
            case Constants.path => Status(METHOD_NOT_ALLOWED)
            case _              => Status(NOT_FOUND)
          }
        case code => Status(code)
      }
    )
  }

  override def onServerError(request: RequestHeader, ex: Throwable): Future[Result] = {
    logger.warn(
      s"[ApiErrorHandler][onServerError] Internal server error for (${request.method}) [${request.uri}] -> ",
      ex
    )

    Future.successful {
      ex match {
        case _: NotFoundException      => Status(NOT_FOUND)
        case _: AuthorisationException => Status(UNAUTHORIZED)
        case _: JsValidationException  => Status(BAD_REQUEST)
        case ex =>
          logger.error(s"[ApiErrorHandler][onServerError] Server error due to unexpected exception", ex)
          Status(INTERNAL_SERVER_ERROR)
      }
    }

  }
}
