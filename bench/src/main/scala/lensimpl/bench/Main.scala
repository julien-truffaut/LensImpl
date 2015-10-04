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

  val long = new OptionsBuilder()
    .warmupIterations(10)
    .measurementIterations(20)
    .forks(3)
    .threads(Runtime.getRuntime.availableProcessors)
    .build()

  def main(args: Array[String]): Unit = {

    val runner = new Runner(short)
    val matrix = MatrixFormatter.parse(runner.run())
    val f = new FileOutputStream(new File(s"lens-${LocalDateTime.now()}.csv"))

    (MatrixFormatter.toCSVRaw(matrix) ++ MatrixFormatter.toCSVRelative(matrix))
      .foreach(l => f.write((l + "\n").getBytes("UTF-8")))

    f.close()
  }

}
