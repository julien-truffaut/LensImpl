package lensimpl

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

class LensSpec extends Properties("LensCC") {

  import Person._

  property("set - get (LensCC)") = forAll { (a: Int, p: Person) =>
    ageCC.get(ageCC.set(a, p)) == a
  }

  property("get - set (LensCC)") = forAll { (p: Person) =>
    ageCC.set(ageCC.get(p), p) == p
  }

  property("set - get (LensVL)") = forAll { (a: Int, p: Person) =>
    ageVL.get(ageVL.set(a, p)) == a
  }

  property("get - set (LensVL)") = forAll { (p: Person) =>
    ageVL.set(ageVL.get(p), p) == p
  }

  property("set - get (LensPF)") = forAll { (a: Int, p: Person) =>
    agePF.get(agePF.set(a, p)) == a
  }

  property("get - set (LensPF)") = forAll { (p: Person) =>
    agePF.set(agePF.get(p), p) == p
  }

}
