package lensimpl.bench.output

import java.io.{PrintStream, PrintWriter, StringWriter}

import org.openjdk.jmh.results.RunResult
import org.openjdk.jmh.results.format.ResultFormat

import scala.collection.JavaConversions._
import scala.language.existentials

class MatrixResultFormat(out: PrintStream) extends ResultFormat {

  type Matrix = Map[(Method, Impl), Result]

  override def writeOut(collection: java.util.Collection[RunResult]): Unit = {
    val results = collection.toList

    val matrix = results.foldLeft(Map.empty[(Method, Impl), Result])( (acc, r) =>
      (for {
        method <- extractMethod(r)
        impl   <- extractImpl(r)
        res     = r.getPrimaryResult
      } yield acc + ((method, impl) -> Result(res.getScore, res.getScoreUnit, res.getScoreError))).getOrElse{
        println(s"Could not extract method and impl from ${r.getParams.getBenchmark}")
        acc
      }
    )

    print(matrix)
  }

  private def print(matrix: Matrix): Unit = {
    val sw = new StringWriter()
    val pw = new PrintWriter(sw)

    toCSV(matrix).foreach(pw.println)
  }

  private def toCSV(matrix: Matrix): List[String] =
    Method.all.map{ method =>
      val line = method.toString :: Impl.all.map(impl => matrix.get((method, impl)).fold("N/A")(_.score.toString))
      line.mkString(",")
    }

  private def extractMethod(r: RunResult): Option[Method] =
    Method.all.find(m =>
      r.getParams.getBenchmark.contains(m.toString)
    )

  private def extractImpl(r: RunResult): Option[Impl] =
    Impl.all.find(m =>
      r.getParams.getBenchmark.contains(m.toString)
    )

}

case class Result(score: Double, unit: String, error: Double)

sealed trait Method extends Product with Serializable
object Method {
  val all = List(Get0, Get3, Get6, Set0, Set3, Set6, Modify0, Modify3, Modify6, ModifyF0, ModifyF3, ModifyF6)
  case object Get0     extends Method
  case object Get3     extends Method
  case object Get6     extends Method
  case object Set0     extends Method
  case object Set3     extends Method
  case object Set6     extends Method
  case object Modify0  extends Method
  case object Modify3  extends Method
  case object Modify6  extends Method
  case object ModifyF0 extends Method
  case object ModifyF3 extends Method
  case object ModifyF6 extends Method
}

sealed trait Impl extends Product with Serializable
object Impl {
  val all = List(STD, MO, CC, VL, PF)
  case object STD extends Impl
  case object MO  extends Impl
  case object CC  extends Impl
  case object VL  extends Impl
  case object PF  extends Impl
}

