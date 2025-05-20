import uk.gov.hmrc.DefaultBuildSettings

ThisBuild / majorVersion := 0
ThisBuild / scalaVersion := "3.5.1"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val microservice = Project("uknw-auth-checker-api-stub", file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .disablePlugins(JUnitXmlReportPlugin) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    // https://www.scala-lang.org/2021/01/12/configuring-and-suppressing-warnings.html
    // suppress warnings in generated routes files
    scalacOptions += "-Wconf:msg=lint-eta-sam:s,src=routes/.*:s,msg=Flag.*repeatedly:s",
    PlayKeys.devSettings := Seq("play.server.http.port" -> "9071")
  )
  .settings(CodeCoverageSettings.settings*)

Test / javaOptions += "-Dlogger.resource=logback-test.xml"

lazy val it = project
  .enablePlugins(PlayScala)
  .dependsOn(microservice % "test->test")
  .settings(DefaultBuildSettings.itSettings())
  .settings(libraryDependencies ++= AppDependencies.it)
  .settings(
    scalacOptions += "-Wconf:msg=Flag.*repeatedly:s"
  )

addCommandAlias("fmtAll", ";scalafmtAll;it/scalafmtAll")
addCommandAlias("fixAll", ";scalafixAll;it/scalafixAll")
addCommandAlias("preCommit", ";clean;compile;scalafmtSbt;fmtAll;fixAll;coverage;test;it/test;coverageReport")
addCommandAlias("runAllChecks", ";clean;compile;scalafmtCheckAll;coverage;test;it/test;coverageReport")
