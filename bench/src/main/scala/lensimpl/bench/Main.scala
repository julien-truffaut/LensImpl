package lensimpl.bench

import lensimpl.bench.output.MatrixResultFormat
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder

object Main {

  def main(args: Array[String]): Unit = {
    val options = new OptionsBuilder()
      .warmupIterations(3)
      .measurementIterations(3)
      .forks(1)
      .build()

    val runner = new Runner(options)

    val matrixFormat = new MatrixResultFormat(System.out)

    matrixFormat.writeOut(runner.run())
  }

}
