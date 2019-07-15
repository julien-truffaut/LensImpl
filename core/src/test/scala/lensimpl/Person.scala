package lensimpl

import org.scalacheck.Arbitrary

case class Person(name: String, age: Int)

object Person {
  val ageCC: adt.Lens[Person, Int] = adt.Lens[Person, Int](_.age, (a, p) => p.copy(age = a))
  val ageVL: vanlaarhoven.Lens[Person, Int] = vanlaarhoven.Lens[Person, Int](_.age, (a, p) => p.copy(age = a))
  val agePF: profunctor.Lens[Person, Int] = profunctor.Lens[Person, Int](_.age, (a, p) => p.copy(age = a))
  val ageMO1: monocle1.Lens[Person, Int] = monocle1.Lens[Person, Int](_.age, (a, p) => p.copy(age = a))
  val ageSubtyping: subtyping.Lens[Person, Int] = subtyping.Lens[Person, Int](_.age)((p, a) => p.copy(age = a))

  implicit val arb: Arbitrary[Person] = Arbitrary(for {
    name <- Arbitrary.arbitrary[String]
    age  <- Arbitrary.arbitrary[Int]
  } yield Person(name, age))
}