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

import java.time.LocalDate
import scala.reflect.ClassTag

import com.google.inject.AbstractModule
import org.mockito.Mockito.when
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSBodyWritables.writeableOf_JsValue
import play.api.libs.ws.{WSClient, WSResponse}
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import uk.gov.hmrc.uknwauthcheckerapistub.controllers.TestData
import uk.gov.hmrc.uknwauthcheckerapistub.services.LocalDateService

class BaseISpec extends PlaySpec, GuiceOneServerPerSuite, BeforeAndAfterAll, TestData {

  lazy val authorisationUrl:                    String           = s"http://localhost:$port/cau/validatecustomsauth/v1"
  protected implicit lazy val localDateService: LocalDateService = injected[LocalDateService]

  override lazy val app: Application = GuiceApplicationBuilder()
    .overrides(moduleOverrides)
    .build()

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    when(injected[LocalDateService].now()).thenReturn(LocalDate.now)
  }

  private lazy val wsClient: WSClient = injected[WSClient]

  def injected[T](c:                 Class[T]):    T = app.injector.instanceOf(c)
  def injected[T](implicit evidence: ClassTag[T]): T = app.injector.instanceOf[T]

  def moduleOverrides: AbstractModule = new AbstractModule {
    override def configure(): Unit =
      bind(classOf[LocalDateService]).toInstance(mock[LocalDateService])
  }

  def postRequestWithHeader(url: String, body: JsValue, headers: Seq[(String, String)]): WSResponse =
    await(
      wsClient
        .url(url)
        .addHttpHeaders(
          headers*
        )
        .post(Json.toJson(body))
    )

  def postRequestWithoutHeader(url: String, body: JsValue): WSResponse =
    await(wsClient.url(url).post(Json.toJson(body)))

  def getRequestWithHeader(url: String, headers: Seq[(String, String)]): WSResponse =
    await(
      wsClient
        .url(url)
        .addHttpHeaders(
          headers*
        )
        .get()
    )
}
