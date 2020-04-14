package org.alexr.configuration

//import play.api._
import play.api.{Application, ApplicationLoader}

class AppLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application = {
    new AppComponents(context).application
  }
}
