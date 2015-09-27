package lensimpl.bench

import java.io.{File, FileOutputStream}

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

    val matrix = MatrixFormatter.parse(runner.run())

    val f = new FileOutputStream(new File("lens.csv"))

    (MatrixFormatter.toCSVRaw(matrix) ++ MatrixFormatter.toCSVRelative(matrix))
      .foreach(l => f.write((l + "\n").getBytes("UTF-8")))

    f.close()
  }

}
