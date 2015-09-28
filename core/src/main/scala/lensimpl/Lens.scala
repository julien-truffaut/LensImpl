package lensimpl

import lensimpl.typeclass.Functor

/**
 * Lens API
 * for the moment, Lens implementation do not inherit them as I am not sure if it could affect benchmarks
 */
trait Lens[S, A] {

  def get(s: S): A

  def set(a: A, s: S): S

  def modify(f: A => A)(s: S): S

  def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]): F[S]

  def compose[B](other: LensMO[A, B]): Lens[S, B]

}
