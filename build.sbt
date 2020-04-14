name := """play-by-manual"""
organization := "org.alexr"

version := s"0.${sys.env.getOrElse("BUILD_NUMBER", "0")}.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, AshScriptPlugin)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  guice,
  "com.github.pureconfig" %% "pureconfig" % "0.12.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
)

//updateConfiguration := updateConfiguration.value.withMissingOk(true)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "org.alexr.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "org.alexr.binders._"
