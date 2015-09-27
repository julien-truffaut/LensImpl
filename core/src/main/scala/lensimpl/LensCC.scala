package lensimpl

import lensimpl.typeclass.Functor

// Case Class Lens
case class LensCC[S, A](get: S => A, set: (A, S) => S){

  def modify(f: A => A)(s: S): S =
    set(f(get(s)), s)

  def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]): F[S] =
    F.map(f(get(s)))(set(_, s))

  def compose[B](other: LensCC[A, B]): LensCC[S, B] =
    LensCC(s => other.get(get(s)), (b, s) => modify(other.set(b, _))(s))

}