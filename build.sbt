name := "play-webpack-typescript-react"

lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-target:jvm-1.8",
                        "-encoding",
                        "UTF-8",
                        "-unchecked",
                        "-deprecation",
                        "-Xfatal-warnings",
                        "-Xfuture",
                        "-Xlint",
                        "-Yno-adapted-args",
//      "-Yno-imports", // no automatic import of Predef (removes irritating implicits)
//      "-Yno-predef",  // no automatic imports at all; all symbols must be imported explicitly
                        "-Ywarn-dead-code",
                        "-Ywarn-numeric-widen",
                        "-Ywarn-value-discard",
                        "-Ywarn-unused",
                        "-Ywarn-unused-import"),
  dependencyOverrides ++= Set(
    "org.scala-lang" % "scala-library" % scalaVersion.value,
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  ),
  scapegoatVersion := "1.2.1",
  wartremoverErrors ++= Warts.unsafe,
  crossScalaVersions := Seq("2.11.8", "2.12.0-RC2")
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "play-webpack-typescript-react",
    scalacOptions ~= { (options: Seq[String]) =>
      options filterNot (_ == "-Ywarn-unused-import")
    },
    coverageExcludedPackages := "controllers\\.Reverse.*;controllers\\.javascript\\.Reverse.*;views\\.html\\..*",
    wartremoverExcluded ++= Seq(
      crossTarget.value / "routes" / "main" / "router" / "Routes.scala",
      crossTarget.value / "routes" / "main" / "router" / "RoutesPrefix.scala"
    ),
    scapegoatIgnoredFiles := Seq(
      crossTarget.value + "/routes/main/controllers/ReverseRoutes.scala",
      crossTarget.value + "/routes/main/controllers/javascript/JavaScriptReverseRoutes.scala",
      crossTarget.value + "/routes/main/router/Routes.scala",
      crossTarget.value + "/twirl/.*.template.scala"
    ),
    PlayKeys.playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value
  )
  .settings(
    libraryDependencies ++= Seq(
      ws,
      "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
    ))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)


scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.15")