package lensimpl.bench.monocle1

import lensimpl.bench._
import lensimpl.macros.GenLens
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class LensMacroBench {

  val n1 = GenLens[Nested0, Nested1]("n")
  val n2 = GenLens[Nested1, Nested2]("n")
  val n3 = GenLens[Nested2, Nested3]("n")
  val n4 = GenLens[Nested3, Nested4]("n")
  val n5 = GenLens[Nested4, Nested5]("n")
  val n6 = GenLens[Nested5, Nested6]("n")

  val n0_i = GenLens[Nested0, Int]("i")
  val n3_i = GenLens[Nested3, Int]("i")
  val n6_i = GenLens[Nested6, Int]("i")

  val n0Ton3I = n1 compose n2 compose n3 compose n3_i
  val n0Ton6I = n1 compose n2 compose n3 compose n4 compose n5 compose n6 compose n6_i

  @Benchmark def lensMACROGet0(in: Nested0Input) = n0_i.get(in.n0)
  @Benchmark def lensMACROGet3(in: Nested0Input) = n0Ton3I.get(in.n0)
  @Benchmark def lensMACROGet6(in: Nested0Input) = n0Ton6I.get(in.n0)

  @Benchmark def lensMACROSet0(in: Nested0Input) = n0_i.set(43, in.n0)
  @Benchmark def lensMACROSet3(in: Nested0Input) = n0Ton3I.set(43, in.n0)
  @Benchmark def lensMACROSet6(in: Nested0Input) = n0Ton6I.set(43, in.n0)

  @Benchmark def lensMACROModify0(in: Nested0Input) = n0_i.modify(_ + 1)(in.n0)
  @Benchmark def lensMACROModify3(in: Nested0Input) = n0Ton3I.modify(_ + 1)(in.n0)
  @Benchmark def lensMACROModify6(in: Nested0Input) = n0Ton6I.modify(_ + 1)(in.n0)

  @Benchmark def lensMACROModifyF0(in: Nested0Input) = n0_i.modifyF(Util.safeDivide)(in.n0)
  @Benchmark def lensMACROModifyF3(in: Nested0Input) = n0Ton3I.modifyF(Util.safeDivide)(in.n0)
  @Benchmark def lensMACROModifyF6(in: Nested0Input) = n0Ton6I.modifyF(Util.safeDivide)(in.n0)

}