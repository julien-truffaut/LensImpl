package lensimpl.bench

import java.io.{File, FileOutputStream}
import java.time.LocalDateTime

import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder

object Main {

  val short = new OptionsBuilder()
    .warmupIterations(3)
    .measurementIterations(3)
    .forks(1)
    .build()

  val medium = new OptionsBuilder()
    .warmupIterations(10)
    .measurementIterations(10)
    .forks(5)
    .threads(Runtime.getRuntime.availableProcessors)
    .build()

  val long = new OptionsBuilder()
    .warmupIterations(20)
    .measurementIterations(20)
    .forks(10)
    .threads(Runtime.getRuntime.availableProcessors)
    .build()

  def main(args: Array[String]): Unit = {

    val runner = new Runner(medium)
    val matrix = MatrixFormatter.parse(runner.run())
    val f = new FileOutputStream(new File(s"lens-${LocalDateTime.now()}.csv"))

    (MatrixFormatter.toCSVRaw(matrix) ++ MatrixFormatter.toCSVRelative(matrix))
      .foreach(l => f.write((l + "\n").getBytes("UTF-8")))

    f.close()
  }

}
