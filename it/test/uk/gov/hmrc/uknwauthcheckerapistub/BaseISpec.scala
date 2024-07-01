package uk.gov.hmrc.uknwauthcheckerapistub

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient
import play.api.test.Helpers.{await, defaultAwaitTimeout}

import scala.io.Source
import scala.reflect.ClassTag

class BaseISpec extends PlaySpec with GuiceOneServerPerSuite {
  private val basePath = "conf/resources/stubJsons/"

  override lazy val app: Application = GuiceApplicationBuilder().build()
  private lazy val wsClient: WSClient = injected[WSClient]
  def injected[T](c: Class[T]): T                    = app.injector.instanceOf(c)
  def injected[T](implicit evidence: ClassTag[T]): T = app.injector.instanceOf[T]

  def postRequest(url: String, body: JsValue, headers: Seq[(String, String)]) = {
    await(wsClient.url(url)
      .addHttpHeaders(
        headers: _*
      ).post(Json.toJson(body))
    )
  }



  def getJsonFile(fileName: String): JsValue = {
    val source = Source.fromFile(basePath ++ fileName)
    val lines = try Json.parse(source.mkString) finally source.close()
    lines
  }
}
