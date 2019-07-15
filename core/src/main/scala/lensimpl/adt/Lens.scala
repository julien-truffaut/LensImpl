package lensimpl.adt

import lensimpl.typeclass.Functor

// Case Class Lens
case class Lens[S, A](get: S => A, set: (A, S) => S){

  def modify(f: A => A)(s: S): S =
    set(f(get(s)), s)

  def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]): F[S] =
    F.map(f(get(s)))(set(_, s))

  def compose[B](other: Lens[A, B]): Lens[S, B] =
    Lens(s => other.get(get(s)), (b, s) => modify(other.set(b, _))(s))

}
