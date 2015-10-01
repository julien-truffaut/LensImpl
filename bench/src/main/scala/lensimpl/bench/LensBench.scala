package lensimpl.bench

import lensimpl.{LensCC, LensMO, LensPF, LensVL}
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class LensBench {
  import LensBench._
  
  def safeDivide(n: Int): Option[Int] = 
    if(n % 2 == 0) Some(n / 2)
    else None
  
  // CC
  @Benchmark def lensCCGet0(in: Nested0Input) = CC._n0_i.get(in.n0)
  @Benchmark def lensCCGet3(in: Nested0Input) = CC._n0Ton3I.get(in.n0)
  @Benchmark def lensCCGet6(in: Nested0Input) = CC._n0Ton6I.get(in.n0)
  
  @Benchmark def lensCCSet0(in: Nested0Input) = CC._n0_i.set(43, in.n0)
  @Benchmark def lensCCSet3(in: Nested0Input) = CC._n0Ton3I.set(43, in.n0)
  @Benchmark def lensCCSet6(in: Nested0Input) = CC._n0Ton6I.set(43, in.n0)
  
  @Benchmark def lensCCModify0(in: Nested0Input) = CC._n0_i.modify(_ + 1)(in.n0)
  @Benchmark def lensCCModify3(in: Nested0Input) = CC._n0Ton3I.modify(_ + 1)(in.n0)
  @Benchmark def lensCCModify6(in: Nested0Input) = CC._n0Ton6I.modify(_ + 1)(in.n0)
  
  @Benchmark def lensCCModifyF0(in: Nested0Input) = CC._n0_i.modifyF(safeDivide)(in.n0)
  @Benchmark def lensCCModifyF3(in: Nested0Input) = CC._n0Ton3I.modifyF(safeDivide)(in.n0)
  @Benchmark def lensCCModifyF6(in: Nested0Input) = CC._n0Ton6I.modifyF(safeDivide)(in.n0)

  // VL
  @Benchmark def lensVLGet0(in: Nested0Input) = VL._n0_i.get(in.n0)
  @Benchmark def lensVLGet3(in: Nested0Input) = VL._n0Ton3I.get(in.n0)
  @Benchmark def lensVLGet6(in: Nested0Input) = VL._n0Ton6I.get(in.n0)

  @Benchmark def lensVLSet0(in: Nested0Input) = VL._n0_i.set(43, in.n0)
  @Benchmark def lensVLSet3(in: Nested0Input) = VL._n0Ton3I.set(43, in.n0)
  @Benchmark def lensVLSet6(in: Nested0Input) = VL._n0Ton6I.set(43, in.n0)

  @Benchmark def lensVLModify0(in: Nested0Input) = VL._n0_i.modify(_ + 1)(in.n0)
  @Benchmark def lensVLModify3(in: Nested0Input) = VL._n0Ton3I.modify(_ + 1)(in.n0)
  @Benchmark def lensVLModify6(in: Nested0Input) = VL._n0Ton6I.modify(_ + 1)(in.n0)

  @Benchmark def lensVLModifyF0(in: Nested0Input) = VL._n0_i.modifyF(safeDivide)(in.n0)
  @Benchmark def lensVLModifyF3(in: Nested0Input) = VL._n0Ton3I.modifyF(safeDivide)(in.n0)
  @Benchmark def lensVLModifyF6(in: Nested0Input) = VL._n0Ton6I.modifyF(safeDivide)(in.n0)

  // PF
  @Benchmark def lensPFGet0(in: Nested0Input) = PF._n0_i.get(in.n0)
  @Benchmark def lensPFGet3(in: Nested0Input) = PF._n0Ton3I.get(in.n0)
  @Benchmark def lensPFGet6(in: Nested0Input) = PF._n0Ton6I.get(in.n0)

  @Benchmark def lensPFSet0(in: Nested0Input) = PF._n0_i.set(43, in.n0)
  @Benchmark def lensPFSet3(in: Nested0Input) = PF._n0Ton3I.set(43, in.n0)
  @Benchmark def lensPFSet6(in: Nested0Input) = PF._n0Ton6I.set(43, in.n0)

  @Benchmark def lensPFModify0(in: Nested0Input) = PF._n0_i.modify(_ + 1)(in.n0)
  @Benchmark def lensPFModify3(in: Nested0Input) = PF._n0Ton3I.modify(_ + 1)(in.n0)
  @Benchmark def lensPFModify6(in: Nested0Input) = PF._n0Ton6I.modify(_ + 1)(in.n0)

  @Benchmark def lensPFModifyF0(in: Nested0Input) = PF._n0_i.modifyF(safeDivide)(in.n0)
  @Benchmark def lensPFModifyF3(in: Nested0Input) = PF._n0Ton3I.modifyF(safeDivide)(in.n0)
  @Benchmark def lensPFModifyF6(in: Nested0Input) = PF._n0Ton6I.modifyF(safeDivide)(in.n0)

  // MO
  @Benchmark def lensMOGet0(in: Nested0Input) = MO._n0_i.get(in.n0)
  @Benchmark def lensMOGet3(in: Nested0Input) = MO._n0Ton3I.get(in.n0)
  @Benchmark def lensMOGet6(in: Nested0Input) = MO._n0Ton6I.get(in.n0)

  @Benchmark def lensMOSet0(in: Nested0Input) = MO._n0_i.set(43, in.n0)
  @Benchmark def lensMOSet3(in: Nested0Input) = MO._n0Ton3I.set(43, in.n0)
  @Benchmark def lensMOSet6(in: Nested0Input) = MO._n0Ton6I.set(43, in.n0)

  @Benchmark def lensMOModify0(in: Nested0Input) = MO._n0_i.modify(_ + 1)(in.n0)
  @Benchmark def lensMOModify3(in: Nested0Input) = MO._n0Ton3I.modify(_ + 1)(in.n0)
  @Benchmark def lensMOModify6(in: Nested0Input) = MO._n0Ton6I.modify(_ + 1)(in.n0)

  @Benchmark def lensMOModifyF0(in: Nested0Input) = MO._n0_i.modifyF(safeDivide)(in.n0)
  @Benchmark def lensMOModifyF3(in: Nested0Input) = MO._n0Ton3I.modifyF(safeDivide)(in.n0)
  @Benchmark def lensMOModifyF6(in: Nested0Input) = MO._n0Ton6I.modifyF(safeDivide)(in.n0)

  // Vanilla scala (std)
  @Benchmark def lensSTDGet0(in: Nested0Input) = in.n0.i
  @Benchmark def lensSTDGet3(in: Nested0Input) = in.n0.n.n.n.i
  @Benchmark def lensSTDGet6(in: Nested0Input) = in.n0.n.n.n.n.n.n.i

  @Benchmark def lensSTDSet0(in: Nested0Input) = in.n0.copy(i = 43)
  @Benchmark def lensSTDSet3(in: Nested0Input) = in.n0.copy(n = in.n0.n.copy(n = in.n0.n.n.copy(n = in.n0.n.n.n.copy(i = 43))))
  @Benchmark def lensSTDSet6(in: Nested0Input) = in.n0.copy(
    n = in.n0.n.copy(
      n = in.n0.n.n.copy(
        n = in.n0.n.n.n.copy(
          n = in.n0.n.n.n.n.copy(
            n = in.n0.n.n.n.n.n.copy(
              n = in.n0.n.n.n.n.n.n.copy(
                i = 43
              )))))))


  @Benchmark def lensSTDModify0(in: Nested0Input) = in.n0.copy(i = in.n0.i + 1)
  @Benchmark def lensSTDModify3(in: Nested0Input) = in.n0.copy(n = in.n0.n.copy(n = in.n0.n.n.copy(n = in.n0.n.n.n.copy(i = in.n0.n.n.n.i + 1))))
  @Benchmark def lensSTDModify6(in: Nested0Input) = in.n0.copy(
    n = in.n0.n.copy(
      n = in.n0.n.n.copy(
        n = in.n0.n.n.n.copy(
          n = in.n0.n.n.n.n.copy(
            n = in.n0.n.n.n.n.n.copy(
              n = in.n0.n.n.n.n.n.n.copy(
                i = in.n0.n.n.n.n.n.n.i + 1
              )))))))


  @Benchmark def lensSTDModifyF0(in: Nested0Input) = safeDivide(in.n0.i).map(_i => in.n0.copy(i = _i))
  @Benchmark def lensSTDModifyF3(in: Nested0Input) = safeDivide(in.n0.n.n.n.i).map(_i =>
    in.n0.copy(n = in.n0.n.copy(n = in.n0.n.n.copy(n = in.n0.n.n.n.copy(i = _i))))
  )
  @Benchmark def lensSTDModifyF6(in: Nested0Input) = safeDivide(in.n0.n.n.n.n.n.n.i).map(_i => in.n0.copy(
    n = in.n0.n.copy(
      n = in.n0.n.n.copy(
        n = in.n0.n.n.n.copy(
          n = in.n0.n.n.n.n.copy(
            n = in.n0.n.n.n.n.n.copy(
              n = in.n0.n.n.n.n.n.n.copy(
                i = _i
              ))))))))
}

object LensBench {
  
  object CC {
    val _n1 = LensCC[Nested0, Nested1](_.n, (n2, n1) => n1.copy(n = n2))
    val _n2 = LensCC[Nested1, Nested2](_.n, (n3, n2) => n2.copy(n = n3))
    val _n3 = LensCC[Nested2, Nested3](_.n, (n4, n3) => n3.copy(n = n4))
    val _n4 = LensCC[Nested3, Nested4](_.n, (n5, n4) => n4.copy(n = n5))
    val _n5 = LensCC[Nested4, Nested5](_.n, (n6, n5) => n5.copy(n = n6))
    val _n6 = LensCC[Nested5, Nested6](_.n, (n7, n6) => n6.copy(n = n7))

    val _n0_i = LensCC[Nested0, Int](_.i, (i, n) => n.copy(i = i))
    val _n3_i = LensCC[Nested3, Int](_.i, (i, n) => n.copy(i = i))
    val _n6_i = LensCC[Nested6, Int](_.i, (i, n) => n.copy(i = i))

    val _n0Ton3I = _n1 compose _n2 compose _n3 compose _n3_i
    val _n0Ton6I = _n1 compose _n2 compose _n3 compose _n4 compose _n5 compose _n6 compose _n6_i
  }

  object VL {
    val _n1 = LensVL[Nested0, Nested1](_.n, (n2, n1) => n1.copy(n = n2))
    val _n2 = LensVL[Nested1, Nested2](_.n, (n3, n2) => n2.copy(n = n3))
    val _n3 = LensVL[Nested2, Nested3](_.n, (n4, n3) => n3.copy(n = n4))
    val _n4 = LensVL[Nested3, Nested4](_.n, (n5, n4) => n4.copy(n = n5))
    val _n5 = LensVL[Nested4, Nested5](_.n, (n6, n5) => n5.copy(n = n6))
    val _n6 = LensVL[Nested5, Nested6](_.n, (n7, n6) => n6.copy(n = n7))
    
    val _n0_i = LensVL[Nested0, Int](_.i, (i, n) => n.copy(i = i))
    val _n3_i = LensVL[Nested3, Int](_.i, (i, n) => n.copy(i = i))
    val _n6_i = LensVL[Nested6, Int](_.i, (i, n) => n.copy(i = i))

    val _n0Ton3I = _n1 compose _n2 compose _n3 compose _n3_i
    val _n0Ton6I = _n1 compose _n2 compose _n3 compose _n4 compose _n5 compose _n6 compose _n6_i
  }

  object PF {
    val _n1 = LensPF[Nested0, Nested1](_.n, (n2, n1) => n1.copy(n = n2))
    val _n2 = LensPF[Nested1, Nested2](_.n, (n3, n2) => n2.copy(n = n3))
    val _n3 = LensPF[Nested2, Nested3](_.n, (n4, n3) => n3.copy(n = n4))
    val _n4 = LensPF[Nested3, Nested4](_.n, (n5, n4) => n4.copy(n = n5))
    val _n5 = LensPF[Nested4, Nested5](_.n, (n6, n5) => n5.copy(n = n6))
    val _n6 = LensPF[Nested5, Nested6](_.n, (n7, n6) => n6.copy(n = n7))

    val _n0_i = LensPF[Nested0, Int](_.i, (i, n) => n.copy(i = i))
    val _n3_i = LensPF[Nested3, Int](_.i, (i, n) => n.copy(i = i))
    val _n6_i = LensPF[Nested6, Int](_.i, (i, n) => n.copy(i = i))

    val _n0Ton3I = _n1 compose _n2 compose _n3 compose _n3_i
    val _n0Ton6I = _n1 compose _n2 compose _n3 compose _n4 compose _n5 compose _n6 compose _n6_i
  }

  object MO {
    val _n1 = LensMO[Nested0, Nested1](_.n, (n2, n1) => n1.copy(n = n2))
    val _n2 = LensMO[Nested1, Nested2](_.n, (n3, n2) => n2.copy(n = n3))
    val _n3 = LensMO[Nested2, Nested3](_.n, (n4, n3) => n3.copy(n = n4))
    val _n4 = LensMO[Nested3, Nested4](_.n, (n5, n4) => n4.copy(n = n5))
    val _n5 = LensMO[Nested4, Nested5](_.n, (n6, n5) => n5.copy(n = n6))
    val _n6 = LensMO[Nested5, Nested6](_.n, (n7, n6) => n6.copy(n = n7))

    val _n0_i = LensMO[Nested0, Int](_.i, (i, n) => n.copy(i = i))
    val _n3_i = LensMO[Nested3, Int](_.i, (i, n) => n.copy(i = i))
    val _n6_i = LensMO[Nested6, Int](_.i, (i, n) => n.copy(i = i))

    val _n0Ton3I = _n1 compose _n2 compose _n3 compose _n3_i
    val _n0Ton6I = _n1 compose _n2 compose _n3 compose _n4 compose _n5 compose _n6 compose _n6_i
  }
  
}