package lensimpl.typeclass

import lensimpl.data.Id

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {

  implicit val idFunctor: Functor[Id] = new Functor[Id] {
    override def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)
  }

  implicit val optionFunctor: Functor[Option] = new Functor[Option]{
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

}
