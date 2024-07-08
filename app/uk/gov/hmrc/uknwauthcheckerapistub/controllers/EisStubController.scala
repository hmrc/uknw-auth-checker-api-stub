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

package uk.gov.hmrc.uknwauthcheckerapistub.controllers

import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import uk.gov.hmrc.uknwauthcheckerapistub.tools.Sanitiser
import uk.gov.hmrc.uknwauthcheckerapistub.tools.helpers.HeadCheker

import javax.inject.{Inject, Singleton}

@Singleton()
class EisStubController @Inject() (cc: ControllerComponents) extends BackendController(cc) with HeadCheker {

  val mySanitiser = new Sanitiser

  def authorisations(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val myBody = request.body.asJson

    try
      if (hasValidBearerToken(request)) {
        val res = myBody match {
          case eoris =>
            mySanitiser.sanitise(eoris.get) match {
              case Right(value) => Ok(value)
              case Left(value)  => BadRequest(value)
            }
          case _ => BadRequest
        }
        res

      } else {
        Forbidden
      }
    catch {
      case _: Throwable => InternalServerError
    }

  }

  def notAllowed(): Action[AnyContent] = Action(MethodNotAllowed)

}
