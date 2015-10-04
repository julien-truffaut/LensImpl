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
  .aggregate(core, macros, bench)
  .dependsOn(core, macros, bench % "compile-internal;test-internal -> test")

lazy val core = project
  .settings(moduleName := "lens-impl-core")
  .settings(lensSettings)
  .settings(libraryDependencies ++= Seq(scalacheck))

lazy val macros = project.dependsOn(core)
  .in(file("macro"))
  .settings(moduleName := "lens-impl-macro")
  .settings(lensSettings)
  .settings(Seq(
    scalacOptions  += "-language:experimental.macros",
    libraryDependencies ++= Seq(
      "org.scala-lang"  %  "scala-reflect"  % scalaVersion.value,
      "org.scala-lang"  %  "scala-compiler" % scalaVersion.value % "provided"
    ),
    addCompilerPlugin(paradisePlugin)
  ))

lazy val bench = project.dependsOn(core, macros)
  .settings(moduleName := "lens-impl-bench")
  .settings(lensSettings)
  .settings(
    mainClass in (Jmh, run) := Some("lensimpl.bench.Main")
  ) .enablePlugins(JmhPlugin)

lazy val scalacheck    = "org.scalacheck"   %% "scalacheck"                % "1.12.5" % "test"
lazy val jmhAnnProcess = "org.openjdk.jmh"   %  "jmh-generator-annprocess" % "1.10.3"

lazy val paradisePlugin = compilerPlugin("org.scalamacros" %  "paradise" % "2.0.1" cross CrossVersion.full)