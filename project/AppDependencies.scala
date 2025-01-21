import sbt.*

object AppDependencies {

  private val bootstrapVersion = "9.7.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-30" % bootstrapVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"          %% "bootstrap-test-play-30" % bootstrapVersion % Test,
    "io.github.wolfendale" %% "scalacheck-gen-regexp"  % "1.1.0"          % Test,
    "org.scalacheck"       %% "scalacheck"             % "1.18.1"         % Test
  )

  val it: Seq[Nothing] = Seq.empty
}
