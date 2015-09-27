package lensimpl.bench

import org.openjdk.jmh.results.RunResult

import scala.collection.JavaConversions._
import scala.language.existentials
import scala.util.Try

object MatrixFormatter {

  type Matrix = Map[(Method, Impl), Result]

  def parse(collection: java.util.Collection[RunResult]): Matrix = {
    val results = collection.toList

    results.foldLeft(Map.empty[(Method, Impl), Result])( (acc, r) =>
      (for {
        method <- extractMethod(r)
        impl   <- extractImpl(r)
        res     = r.getPrimaryResult
      } yield acc + ((method, impl) -> Result(res.getScore, res.getScoreUnit, res.getScoreError))).getOrElse{
        println(s"Could not extract method and impl from ${r.getParams.getBenchmark}")
        acc
      }
    )
  }


  def toCSVRaw(matrix: Matrix): List[String] =
    (("Method" :: Impl.all.map(_.toString)) :: Method.all.map{ method =>
      method.toString :: Impl.all.map(impl => matrix.get((method, impl)).fold("N/A")(r => format(r.score)))
    }).map(_.mkString(","))

  def toCSVRelative(matrix: Matrix): List[String] =
    (("Method" :: Impl.all.map(_.toString)) :: Method.all.map{ method =>
      method.toString :: Impl.all.map(impl =>
        (for {
          implRes <- matrix.get((method, impl))
          stdRes  <- matrix.get((method, Impl.STD))
          ratio   <- Try(implRes.score / stdRes.score).toOption
        } yield format(ratio)).getOrElse("N/A")
      )
    }).map(_.mkString(","))

  private def format(d: Double): String =
    "%1.2f".format(d)

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

