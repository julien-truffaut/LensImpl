package lensimpl.typeclass


trait Profunctor[P[_, _]] {
  def dimap[A, B, C, D](pab: P[A, B])(f: C => A)(g: B => D): P[C, D]
  def lmap[A, B, C](pab: P[A, B])(f: C => A): P[C, B]
  def rmap[A, B, C](pab: P[A, B])(f: B => C): P[A, C]
}
