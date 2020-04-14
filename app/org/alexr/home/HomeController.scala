package org.alexr.home

import java.net.URL
import java.nio.file.Paths
import java.util.UUID

import javax.inject._
import org.alexr.configuration.{AppConfig, Person}
import play.api._
import play.api.mvc._

import scala.concurrent.duration.FiniteDuration

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
                                val controllerComponents: ControllerComponents,
                                val configFile: Configuration,
                                val config: AppConfig) extends BaseController {

  def index_legacy() = Action { implicit request: Request[AnyContent] =>
    val name = configFile.get[String]("name")
    val host = configFile.get[URL]("host")
    val port = configFile.get[Int]("port")
    val hotels = configFile.underlying.getStringList("hotels")
    val minDuration = configFile.get[FiniteDuration]("min-duration")
    val applicationId = UUID.fromString(configFile.get[String]("application-id"))
    val sshDirectory = Paths.get(configFile.get[String]("ssh-directory"))
    val developer = Person(
      configFile.get[String]("developer.name"),
      configFile.get[Int]("developer.age")
    )
        Ok(views.html.index())
  }

  def index() = Action { implicit request: Request[AnyContent] =>

    val message =
      s"""
         |V:3
         |name: ${config.name}
         |host: ${config.host}
         |port: ${config.port}
         |hotels: ${config.hotels}
         |minDuration: ${config.minDuration}
         |applicationId: ${config.applicationId}
         |sshDirectory: ${config.sshDirectory}
         |developer: ${config.developer}
         |""".stripMargin
    println(message)
    Ok(message)
  }
}
