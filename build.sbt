val lensSettings: Seq[Setting[_]] = Seq(
  organization       := "com.github.julien-truffaut",
  scalaVersion       := "2.11.8",
  crossScalaVersions := Seq("2.11.8", "2.12.0-M5"),
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
  .settings(mainClass in (Jmh, run) := Some("lensimpl.bench.Main"))
  .settings(
    javaOptions in Jmh         ++= Seq("-server", "-Xms2G", "-Xmx2G", "-XX:+UseG1GC"),
    javaOptions in (Test, run) ++= Seq("-server", "-Xms2G", "-Xmx2G", "-XX:+UseG1GC")
  )
  .settings(libraryDependencies += "org.openjdk.jmh" % "jmh-generator-annprocess" % "1.13")
  .enablePlugins(JmhPlugin)

lazy val scalacheck    = "org.scalacheck"   %% "scalacheck" % "1.13.2" % "test"

lazy val paradisePlugin = compilerPlugin("org.scalamacros" %  "paradise" % "2.1.0" cross CrossVersion.full)
