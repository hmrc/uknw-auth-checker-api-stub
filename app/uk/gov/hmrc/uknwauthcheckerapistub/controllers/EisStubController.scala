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
import uk.gov.hmrc.uknwauthcheckerapistub.tools.StubDataService
import uk.gov.hmrc.uknwauthcheckerapistub.tools.helpers.HeadChecker

import javax.inject.{Inject, Singleton}

@Singleton()
class EisStubController @Inject() (cc: ControllerComponents) extends BackendController(cc) with HeadChecker {

  private val serviceStub = new StubDataService

  def authorisations(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    request match {
      case req if req.method == "POST" && hasValidBearerToken(req) => serviceStub.stubbing(req)
      case req if !hasValidBearerToken(req)                        => Forbidden
      case req if req.method != "POST"                             => MethodNotAllowed
      case _                                                       => InternalServerError
    }

  }

}
