package lensimpl

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

class LensSpec extends Properties("Lens") {

  import Person._

  property("set - get (ADT)") = forAll { (a: Int, p: Person) =>
    ageCC.get(ageCC.set(a, p)) == a
  }

  property("get - set (ADT)") = forAll { (p: Person) =>
    ageCC.set(ageCC.get(p), p) == p
  }

  property("set - get (Van Laarhoven)") = forAll { (a: Int, p: Person) =>
    ageVL.get(ageVL.set(a, p)) == a
  }

  property("get - set (Van Laarhoven)") = forAll { (p: Person) =>
    ageVL.set(ageVL.get(p), p) == p
  }

  property("set - get (Profunctor)") = forAll { (a: Int, p: Person) =>
    agePF.get(agePF.set(a, p)) == a
  }

  property("get - set (Profunctor)") = forAll { (p: Person) =>
    agePF.set(agePF.get(p), p) == p
  }

  property("set - get (Monocle1)") = forAll { (a: Int, p: Person) =>
    ageMO1.get(ageMO1.set(a, p)) == a
  }

  property("get - set (Monocle1)") = forAll { (p: Person) =>
    ageMO1.set(ageMO1.get(p), p) == p
  }

  property("set - get (Subtyping)") = forAll { (a: Int, p: Person) =>
    ageSubtyping.get(ageSubtyping.set(a)(p)) == a
  }

  property("get - set (Subtyping)") = forAll { (p: Person) =>
    ageSubtyping.set(ageSubtyping.get(p))(p) == p
  }

}
