package lensimpl.profunctor

import lensimpl.data.{Forget, Kleisli}
import lensimpl.typeclass.{Functor, Strong}

// Profunctor Lens
abstract class Lens[S, A] { self =>
  def _lens[P[_,_]](implicit P: Strong[P]): P[A, A] => P[S, S]

  final def modifyF[F[_]](f: A => F[A])(s: S)(implicit F: Functor[F]): F[S] =
    _lens[({type λ[a, b] = Kleisli[F, a, b]})#λ].apply(Kleisli(f)).run(s)

  final def modify(f: A => A)(s: S): S =
    _lens[Function1].apply(f).apply(s)

  final def set(a: A, s: S): S =
    modify(_ => a)(s)

  final def get(s: S): A =
    _lens[({type λ[a, b] = Forget[A, a, b]})#λ].apply(Forget(identity)).run(s)

  final def compose[B](other: Lens[A, B]): Lens[S, B] = new Lens[S, B] {
    override def _lens[P[_, _]](implicit P: Strong[P]): P[B, B] => P[S, S] =
      self._lens[P] compose other._lens[P]
  }

}

object Lens {
  def apply[S, A](_get: S => A, _set: (A, S) => S): Lens[S, A] =
    new Lens[S, A] {
      override def _lens[P[_, _]](implicit P: Strong[P]): P[A, A] => P[S, S] =
        paa =>
          P.dimap(P.first[A, A, S](paa))((s: S) => (_get(s), s))(_set.tupled)
    }
}