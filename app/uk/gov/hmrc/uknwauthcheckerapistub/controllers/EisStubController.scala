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

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.uknwauthcheckerapistub.services.StubDataService
import uk.gov.hmrc.uknwauthcheckerapistub.utils.HeadChecker

@Singleton()
class EisStubController @Inject() (stubDataService: StubDataService, cc: ControllerComponents) extends BackendController(cc) with HeadChecker {

  def authorisations(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val isValidToken: Boolean = hasValidBearerToken(request)
    val isPost:       Boolean = request.method == "POST"

    (isValidToken, isPost) match {
      case (true, true) => stubDataService.stubbing(request)
      case (false, _)   => Forbidden
      case (_, false)   => MethodNotAllowed
      case _            => InternalServerError
    }
  }
}
