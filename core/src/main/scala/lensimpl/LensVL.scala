package lensimpl

import typeclass.Functor
import data._

// Van Laarhoven Lens
abstract class LensVL[S, A] { self =>
  def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]):  F[S]

  final def modify(f: A => A)(s: S): S =
    modifyF[Id](f)(s)

  final def set(a: A, s: S): S =
    modify(_ => a)(s)

  final def get(s: S): A =
    modifyF[({type λ[α] = Const[A, α]})#λ](a => Const(a))(s).getConst

  final def compose[B](other: LensVL[A, B]): LensVL[S, B] = new LensVL[S, B] {
    override def modifyF[F[_]](f: (B) => F[B])(s: S)(implicit F: Functor[F]): F[S] =
      self.modifyF(other.modifyF(f))(s)
  }

}

object LensVL {
  def apply[S, A](_get: S => A, _set: (A, S) => S): LensVL[S, A] =
    new LensVL[S, A] {
      override def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]): F[S] =
        F.map(f(_get(s)))(_set(_, s))
    }
}