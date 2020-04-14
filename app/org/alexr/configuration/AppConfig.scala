package org.alexr.configuration

import java.net.URL
import java.nio.file.Path
import java.util.UUID

import scala.concurrent.duration.FiniteDuration

// camelCaseMember in `application.conf`
case class AppConfig(
                   name: String,
                   host: URL,
                   port: Int,
                   hotels: List[String],
                   minDuration: FiniteDuration,
                   applicationId: UUID,
                   sshDirectory: Path,
                   developer: Person
                 )
