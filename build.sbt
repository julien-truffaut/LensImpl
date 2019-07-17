import Dependencies._

val lensSettings: Seq[Setting[_]] = Seq(
  organization       := "com.github.julien-truffaut",
  scalaVersion       := "2.13.0",
  crossScalaVersions := Seq("2.13.0"),
  scalacOptions     ++= Seq(
    "-encoding", "UTF-8",
    "-feature",
    "-language:implicitConversions", "-language:higherKinds", "-language:postfixOps",
    "-unchecked",
    "-Xfatal-warnings",
    "-deprecation",
    "-Ywarn-dead-code",
    "-Ywarn-value-discard",
    "-Ywarn-unused:imports",
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
  .settings(libraryDependencies ++= Seq(scalatest, scalacheck))

lazy val macros = project.dependsOn(core)
  .in(file("macro"))
  .settings(moduleName := "lens-impl-macro")
  .settings(lensSettings)
  .settings(Seq(
    scalacOptions  += "-language:experimental.macros",
    libraryDependencies ++= Seq(
      scalaOrganization.value % "scala-reflect"  % scalaVersion.value,
      scalaOrganization.value % "scala-compiler" % scalaVersion.value % "provided"
    )
  ))

lazy val bench = project.dependsOn(core, macros)
  .settings(moduleName := "lens-impl-bench")
  .settings(lensSettings)
  .settings(mainClass in (Jmh, run) := Some("lensimpl.bench.Main"))
  .settings(
    javaOptions in Jmh         ++= Seq("-server", "-Xms2G", "-Xmx2G", "-XX:+UseG1GC"),
    javaOptions in (Test, run) ++= Seq("-server", "-Xms2G", "-Xmx2G", "-XX:+UseG1GC")
  )
  .settings(libraryDependencies ++= Seq(
    "org.openjdk.jmh" % "jmh-generator-annprocess" % "1.13",
    "org.jfree"       % "jfreechart"               % "1.0.19"
  ))
  .enablePlugins(JmhPlugin)

