package lensimpl.subtyping

import lensimpl.subtyping.Foo.FooI
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.{FunSuite, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks


class ComposeSpec extends FunSuite with Matchers with ScalaCheckDrivenPropertyChecks {

  test("Lens -> Iso") {
    val composed = Bar.id compose Id.x
    val typeTest: Lens[Bar, String] = composed

    forAll((bar: Bar) => composed.get(bar) shouldEqual bar.id.x)
  }

  test("Lens -> Lens") {
    val idLens: Lens[Id, String] = Id.x
    val composed = Bar.id compose idLens
    val typeTest: Lens[Bar, String] = composed

    forAll((bar: Bar) => composed.get(bar) shouldEqual bar.id.x)
  }

  test("Lens -> Prism") {
    val composed = Bar.foo compose Foo.fooI
    val typeTest: Optional[Bar, FooI] = composed

    forAll( (bar: Bar) =>
      composed.getOption(bar) shouldEqual bar.foo.asFooI
    )
  }

  test("Lens -> Optional") {
    val fooIOptional: Optional[Foo, Int] = Foo.fooIS compose Foo.FooIS.i
    val composed = Bar.foo compose fooIOptional
    val typeTest: Optional[Bar, Int] = composed

    forAll((bar: Bar) => composed.getOption(bar) shouldEqual bar.foo.asFooIS.map(_.i))
  }
  
}

case class Bar(i: Int, id: Id, foo: Foo)

object Bar {
  val i: Lens[Bar, Int] = Lens[Bar, Int](_.i)((bar, newI) => bar.copy(i = newI))
  val id: Lens[Bar, Id] = Lens[Bar, Id](_.id)((bar, newId) => bar.copy(id = newId))
  val foo: Lens[Bar, Foo] = Lens[Bar, Foo](_.foo)((bar, newFoo) => bar.copy(foo = newFoo))

  implicit val arb: Arbitrary[Bar] =
    Arbitrary(
      for {
        i <- arbitrary[Int]
        id <- arbitrary[Id]
        foo <- arbitrary[Foo]
      } yield Bar(i, id, foo)
    )
}

case class Id(x: String)

object Id {
  val x: Iso[Id, String] = Iso[Id, String](_.x)(Id(_))

  implicit val arb: Arbitrary[Id] =
    Arbitrary(Arbitrary.arbitrary[String].map(Id(_)))
}

sealed trait Foo {
  def asFooI: Option[Foo.FooI] = this match {
    case x: Foo.FooI => Some(x)
    case _ => None
  }

  def asFooIS: Option[Foo.FooIS] = this match {
    case x: Foo.FooIS => Some(x)
    case _ => None
  }
}

object Foo {
  case class FooI(i: Int) extends Foo
  case class FooS(s: String) extends Foo
  case class FooIS(i: Int, s: String) extends Foo
  case object FooU extends Foo

  val fooI: Prism[Foo, FooI] = Prism.partial[Foo, FooI]{ case x: FooI => x }(identity)
  val fooS: Prism[Foo, FooS] = Prism.partial[Foo, FooS]{ case x: FooS => x }(identity)
  val fooIS: Prism[Foo, FooIS] = Prism.partial[Foo, FooIS]{ case x: FooIS => x }(identity)
  val fooU: Prism[Foo, FooU.type] = Prism.partial[Foo, FooU.type]{ case FooU => FooU }(identity)

  object FooIS {
    val i: Lens[FooIS, Int] = Lens[FooIS, Int](_.i)((foo, newI) => foo.copy(i = newI))
  }

  implicit val arb: Arbitrary[Foo] =
    Arbitrary(
      Gen.oneOf[Foo](
        arbitrary[Int].map(FooI(_)),
        arbitrary[String].map(FooS(_)),
        for {
          i <- arbitrary[Int]
          s <- arbitrary[String]
        } yield FooIS(i, s),
        Gen.const(FooU)
      )
    )
}

