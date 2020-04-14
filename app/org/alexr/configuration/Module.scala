package org.alexr.configuration

import com.google.inject.{AbstractModule, Provides, Singleton}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

class Module extends AbstractModule {
  val config: AppConfig = ConfigSource.default.loadOrThrow[AppConfig]

  @Provides()
  @Singleton()
  def configProvider: AppConfig = config
}
