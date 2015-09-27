import sbt._
import sbt.Keys._

val lensSettings: Seq[Setting[_]] = Seq(
  organization       := "com.github.julien-truffaut",
  scalaVersion       := "2.11.7",
  crossScalaVersions := Seq("2.11.7", "2.12.0-M2"),
  scalacOptions     ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:implicitConversions", "-language:higherKinds", "-language:postfixOps",
    "-unchecked",
    "-Xfatal-warnings",
    "-Yinline-warnings",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  resolvers += Resolver.sonatypeRepo("releases")
)

lazy val lensImpl = project.in(file("."))
  .settings(moduleName := "lens-impl")
  .settings(lensSettings)
  .aggregate(core, bench)
  .dependsOn(core, bench % "compile-internal;test-internal -> test")

lazy val core = project
  .settings(moduleName := "lens-impl-core")
  .settings(lensSettings)
  .settings(libraryDependencies ++= Seq(scalacheck))

lazy val bench = project.dependsOn(core)
  .settings(moduleName := "lens-impl-bench")
  .settings(lensSettings)
  .settings(
    mainClass in (Jmh, run) := Some("lensimpl.bench.Main")
  ) .enablePlugins(JmhPlugin)

lazy val scalacheck    = "org.scalacheck"   %% "scalacheck"                % "1.12.5" % "test"
lazy val jmhAnnProcess = "org.openjdk.jmh"   %  "jmh-generator-annprocess" % "1.10.3"
