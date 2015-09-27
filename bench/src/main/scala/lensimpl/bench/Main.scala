package lensimpl.bench

import lensimpl.bench.output.MatrixResultFormat
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder

object Main extends App {

  val options = new OptionsBuilder()
    .include(".*" + LensBench.getClass.getSimpleName + ".*")
    .warmupIterations(3)
    .measurementIterations(5)
    .build()

  val runner = new Runner(options)

  val matrixFormat = new MatrixResultFormat(System.out)

  matrixFormat.writeOut(runner.run())

}
