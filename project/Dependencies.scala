import sbt._

object Dependencies {
  lazy val scalatest  = "org.scalatest"  %% "scalatest"  % "3.0.8"  % "test"
  lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
}
