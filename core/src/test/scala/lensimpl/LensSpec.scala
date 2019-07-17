package lensimpl

import org.scalatest.{FunSuite, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class LensSpec extends FunSuite with Matchers with ScalaCheckDrivenPropertyChecks {

  import Person._

  test("set - get (ADT)") {
    forAll { (a: Int, p: Person) => ageCC.get(ageCC.set(a, p)) == a }
  }

  test("get - set (ADT)") {
    forAll { (p: Person) => ageCC.set(ageCC.get(p), p) == p }
  }

  test("set - get (Van Laarhoven)") {
    forAll { (a: Int, p: Person) => ageVL.get(ageVL.set(a, p)) == a }
  }

  test("get - set (Van Laarhoven)") {
    forAll { (p: Person) => ageVL.set(ageVL.get(p), p) == p
    }
  }

  test("set - get (Profunctor)") {
    forAll { (a: Int, p: Person) => agePF.get(agePF.set(a, p)) == a }
  }

  test("get - set (Profunctor)") {
    forAll { (p: Person) => agePF.set(agePF.get(p), p) == p }
  }

  test("set - get (Monocle1)") {
   forAll { (a: Int, p: Person) => ageMO1.get(ageMO1.set(a, p)) == a }
  }

  test("get - set (Monocle1)") {
    forAll { (p: Person) => ageMO1.set(ageMO1.get(p), p) == p }
  }

  test("set - get (Subtyping)") {
    forAll { (a: Int, p: Person) => ageSubtyping.get(ageSubtyping.set(a)(p)) == a }
  }

  test("get - set (Subtyping)") {
    forAll { (p: Person) => ageSubtyping.set(ageSubtyping.get(p))(p) == p }
  }

}
