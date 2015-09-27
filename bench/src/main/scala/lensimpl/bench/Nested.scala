package lensimpl.bench

case class Nested0(s: String, i: Int, n: Nested1, l: Long)
case class Nested1(s: String, i: Int, n: Nested2, l: Long)
case class Nested2(s: String, i: Int, n: Nested3, l: Long)
case class Nested3(s: String, i: Int, n: Nested4, l: Long)
case class Nested4(s: String, i: Int, n: Nested5, l: Long)
case class Nested5(s: String, i: Int, n: Nested6, l: Long)
case class Nested6(s: String, i: Int)
