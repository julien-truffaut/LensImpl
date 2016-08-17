package lensimpl.bench

import lensimpl.LensPF
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class LensPFBench {

  val n1 = LensPF[Nested0, Nested1](_.n, (n2, n1) => n1.copy(n = n2))
  val n2 = LensPF[Nested1, Nested2](_.n, (n3, n2) => n2.copy(n = n3))
  val n3 = LensPF[Nested2, Nested3](_.n, (n4, n3) => n3.copy(n = n4))
  val n4 = LensPF[Nested3, Nested4](_.n, (n5, n4) => n4.copy(n = n5))
  val n5 = LensPF[Nested4, Nested5](_.n, (n6, n5) => n5.copy(n = n6))
  val n6 = LensPF[Nested5, Nested6](_.n, (n7, n6) => n6.copy(n = n7))

  val n0_i = LensPF[Nested0, Int](_.i, (i, n) => n.copy(i = i))
  val n3_i = LensPF[Nested3, Int](_.i, (i, n) => n.copy(i = i))
  val n6_i = LensPF[Nested6, Int](_.i, (i, n) => n.copy(i = i))

  val n0Ton3I = n1 compose n2 compose n3 compose n3_i
  val n0Ton6I = n1 compose n2 compose n3 compose n4 compose n5 compose n6 compose n6_i
  
  @Benchmark def lensPFGet0(in: Nested0Input) = n0_i.get(in.n0)
  @Benchmark def lensPFGet3(in: Nested0Input) = n0Ton3I.get(in.n0)
  @Benchmark def lensPFGet6(in: Nested0Input) = n0Ton6I.get(in.n0)

  @Benchmark def lensPFSet0(in: Nested0Input) = n0_i.set(43, in.n0)
  @Benchmark def lensPFSet3(in: Nested0Input) = n0Ton3I.set(43, in.n0)
  @Benchmark def lensPFSet6(in: Nested0Input) = n0Ton6I.set(43, in.n0)

  @Benchmark def lensPFModify0(in: Nested0Input) = n0_i.modify(_ + 1)(in.n0)
  @Benchmark def lensPFModify3(in: Nested0Input) = n0Ton3I.modify(_ + 1)(in.n0)
  @Benchmark def lensPFModify6(in: Nested0Input) = n0Ton6I.modify(_ + 1)(in.n0)

  @Benchmark def lensPFModifyF0(in: Nested0Input) = n0_i.modifyF(Util.safeDivide)(in.n0)
  @Benchmark def lensPFModifyF3(in: Nested0Input) = n0Ton3I.modifyF(Util.safeDivide)(in.n0)
  @Benchmark def lensPFModifyF6(in: Nested0Input) = n0Ton6I.modifyF(Util.safeDivide)(in.n0)
  
}