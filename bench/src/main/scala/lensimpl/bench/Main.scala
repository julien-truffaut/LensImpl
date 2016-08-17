package lensimpl.bench

import java.io.FileWriter
import java.time.LocalDateTime

import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.{ChainedOptionsBuilder, Options, OptionsBuilder}

import scala.util.Properties.versionString

object Main {

  def main(args: Array[String]): Unit = {
    val config = args.headOption.map{
      case "s" => Config.short
      case "m" => Config.medium
      case "l" => Config.long
    }.getOrElse(sys.error("requires config option s, m or l for small, medium or long"))

    val runner = new Runner(config.jmhOptions)
    val matrix = MatrixFormatter.parse(runner.run())

    val f = new FileWriter("lens.csv", true)

    (List(s"date,${LocalDateTime.now()}", s"config,${config.name}", s"scala,$versionString") ++
      MatrixFormatter.toCSVRaw(matrix) ++
      MatrixFormatter.toCSVRelative(matrix) ++
      List(""))
      .foreach(l => f.write(l + "\n"))

    f.close()
  }

}

case class Config(name: String, builder: ChainedOptionsBuilder) {
  def jmhOptions: Options =
    builder
      .exclude(".*LensVLBench.*")
      .exclude(".*LensPFBench.*")
      .build
}

object Config {
  val short = Config("short", new OptionsBuilder()
    .warmupIterations(1)
    .measurementIterations(1)
    .forks(1))

  val medium = Config("medium", new OptionsBuilder()
    .warmupIterations(10)
    .measurementIterations(10)
    .forks(5)
    .threads(Runtime.getRuntime.availableProcessors))

  val long = Config("long", new OptionsBuilder()
    .warmupIterations(20)
    .measurementIterations(20)
    .forks(10)
    .threads(Runtime.getRuntime.availableProcessors))
}
