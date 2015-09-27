package lensimpl.typeclass


trait Strong[P[_, _]] extends Profunctor[P] {
  def first[A, B, C](pab: P[A, B]): P[(A, C), (B, C)]
  def second[A, B, C](pab: P[A, B]): P[(C, A), (C, B)]
}

object Strong {
  implicit val function1Strong: Strong[Function1] = new Strong[Function1]{
    override def dimap[A, B, C, D](pab: A => B)(f: C => A)(g: B => D): C => D =
      g compose pab compose f

    override def lmap[A, B, C](pab: A => B)(f: C => A): C => B =
      pab compose f

    override def rmap[A, B, C](pab: A => B)(f: B => C): A => C =
      f compose pab

    override def first[A, B, C](f: A => B): ((A, C)) => (B, C) = {
      case (a, c) => (f(a), c)
    }
    override def second[A, B, C](f: A => B): ((C, A)) => (C, B) = {
      case (c, a) => (c, f(a))
    }
  }
}
