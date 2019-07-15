package lensimpl.vanlaarhoven

import lensimpl.data.{Const, Id}
import lensimpl.typeclass.Functor

// Van Laarhoven Lens
abstract class Lens[S, A] { self =>
  def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]):  F[S]

  final def modify(f: A => A)(s: S): S =
    modifyF[Id](f)(s)

  final def set(a: A, s: S): S =
    modify(_ => a)(s)

  final def get(s: S): A =
    modifyF[({type λ[α] = Const[A, α]})#λ](a => Const(a))(s).getConst

  final def compose[B](other: Lens[A, B]): Lens[S, B] = new Lens[S, B] {
    override def modifyF[F[_]](f: (B) => F[B])(s: S)(implicit F: Functor[F]): F[S] =
      self.modifyF(other.modifyF(f))(s)
  }

}

object Lens {
  def apply[S, A](_get: S => A, _set: (A, S) => S): Lens[S, A] =
    new Lens[S, A] {
      override def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]): F[S] =
        F.map(f(_get(s)))(_set(_, s))
    }
}