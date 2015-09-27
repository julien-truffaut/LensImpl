package lensimpl.bench

import org.openjdk.jmh.annotations.{Scope, Setup, State}

import scala.util.Random

@State(Scope.Thread)
class Nested0Input {

  val r = new Random

  def genBool(): Boolean = r.nextBoolean()
  def genInt(): Int = r.nextInt()
  def genLong(): Long = r.nextLong()
  def genStr(): String = r.nextString(r.nextInt(100))

  var n0: Nested0 = _

  @Setup
  def setup(): Unit =
    n0 = Nested0(genStr(), genInt(),
      Nested1(genStr(), genInt(),
        Nested2(genStr(), genInt(),
          Nested3(genStr(), genInt(),
            Nested4(genStr(), genInt(),
              Nested5(genStr(), genInt(),
                Nested6(genStr(), genInt())
                ,genLong()), genLong()), genLong()), genLong()), genLong()), genLong())

}
