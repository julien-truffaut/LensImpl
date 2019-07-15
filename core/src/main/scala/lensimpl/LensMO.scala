package lensimpl

import lensimpl.typeclass.Functor

// Monocle 1.1 Lens
abstract class LensMO[S, A] { self =>
  def get(s: S): A

  def set(a: A, s: S): S

  def modify(f: A => A)(s: S): S

  def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]): F[S]

  final def compose[B](other: LensMO[A, B]): LensMO[S, B] = new LensMO[S, B] {
    override def get(s: S): B =
      other.get(self.get(s))

    override def set(a: B, s: S): S =
      self.modify(other.set(a, _))(s)

    override def modify(f: B => B)(s: S): S =
      self.modify(other.modify(f)(_))(s)

    override def modifyF[F[_]](f: B => F[B])(s: S)(implicit F: Functor[F]): F[S] =
      self.modifyF(other.modifyF(f)(_))(s)
  }

}

object LensMO {
  def apply[S, A](_get: S => A, _set: (A, S) => S): LensMO[S, A] =
    new LensMO[S, A] {
      override def get(s: S): A = _get(s)
      override def set(a: A, s: S): S = _set(a, s)
      override def modify(f: A => A)(s: S): S = _set(f(get(s)), s)
      override def modifyF[F[_]](f: (A) => F[A])(s: S)(implicit F: Functor[F]): F[S] =
        F.map(f(_get(s)))(_set(_, s))
    }
}