package org.alexr.configuration

import play.api.{Application, ApplicationLoader}

class AppLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application = {
    val components = new AppComponents(context)

    components.runEvolutions()
    components.application
  }
}
