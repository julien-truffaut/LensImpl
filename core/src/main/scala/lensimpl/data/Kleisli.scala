package lensimpl.data

import lensimpl.typeclass.{Strong, Functor}

case class Kleisli[F[_], A, B](run: A => F[B]){
  def map[C](f: B => C)(implicit F: Functor[F]): Kleisli[F, A, C] =
    Kleisli(a => F.map(run(a))(f))
  def contramap[C](f: C => A): Kleisli[F, C, B] =
    Kleisli(run compose f)
}

object Kleisli {
  implicit def strong[F[_]](implicit F: Functor[F]): Strong[({type λ[a, b] = Kleisli[F, a, b]})#λ] = new Strong[({type λ[a, b] = Kleisli[F, a, b]})#λ] {
    override def dimap[A, B, C, D](pab: Kleisli[F, A, B])(f: C => A)(g: (B) => D): Kleisli[F, C, D] =
      pab.map(g).contramap(f)

    override def lmap[A, B, C](pab: Kleisli[F, A, B])(f: C => A): Kleisli[F, C, B] =
      pab.contramap(f)

    override def rmap[A, B, C](pab: Kleisli[F, A, B])(f: (B) => C): Kleisli[F, A, C] =
      pab.map(f)

    override def second[A, B, C](pab: Kleisli[F, A, B]): Kleisli[F, (C, A), (C, B)] =
      Kleisli{ case (c, a) =>
          F.map(pab.run(a))((c, _))
      }
    override def first[A, B, C](pab: Kleisli[F, A, B]): Kleisli[F, (A, C), (B, C)] =
      Kleisli{ case (a, c) =>
        F.map(pab.run(a))((_, c))
      }
  }
}
