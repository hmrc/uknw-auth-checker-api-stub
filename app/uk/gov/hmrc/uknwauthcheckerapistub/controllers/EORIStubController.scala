package uk.gov.hmrc.uknwauthcheckerapistub.controllers

import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import uk.gov.hmrc.uknwauthcheckerapistub.tools.Helper

import javax.inject.{Inject, Singleton}

@Singleton()
class EORIStubController @Inject()(cc: ControllerComponents)
  extends BackendController(cc) with Helper {

  def checkEORI(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>

    Ok(defaultEORI)
  }
}
