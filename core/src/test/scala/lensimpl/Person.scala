package lensimpl

import org.scalacheck.Arbitrary

case class Person(name: String, age: Int)

object Person {
  val ageCC: LensCC[Person, Int] = LensCC[Person, Int](_.age, (a, p) => p.copy(age = a))
  val ageVL: LensVL[Person, Int] = LensVL[Person, Int](_.age, (a, p) => p.copy(age = a))
  val agePF: LensPF[Person, Int] = LensPF[Person, Int](_.age, (a, p) => p.copy(age = a))

  implicit val arb: Arbitrary[Person] = Arbitrary(for {
    name <- Arbitrary.arbitrary[String]
    age  <- Arbitrary.arbitrary[Int]
  } yield Person(name, age))
}