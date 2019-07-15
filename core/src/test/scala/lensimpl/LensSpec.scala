package lensimpl

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

class LensSpec extends Properties("Lens") {

  import Person._

  property("set - get (Lens)") = forAll { (a: Int, p: Person) =>
    ageCC.get(ageCC.set(a, p)) == a
  }

  property("get - set (Lens)") = forAll { (p: Person) =>
    ageCC.set(ageCC.get(p), p) == p
  }

  property("set - get (Lens)") = forAll { (a: Int, p: Person) =>
    ageVL.get(ageVL.set(a, p)) == a
  }

  property("get - set (Lens)") = forAll { (p: Person) =>
    ageVL.set(ageVL.get(p), p) == p
  }

  property("set - get (Lens)") = forAll { (a: Int, p: Person) =>
    agePF.get(agePF.set(a, p)) == a
  }

  property("get - set (Lens)") = forAll { (p: Person) =>
    agePF.set(agePF.get(p), p) == p
  }

  property("set - get (Lens)") = forAll { (a: Int, p: Person) =>
    ageMO.get(ageMO.set(a, p)) == a
  }

  property("get - set (Lens)") = forAll { (p: Person) =>
    ageMO.set(ageMO.get(p), p) == p
  }

}
