package lensimpl.data

import lensimpl.typeclass.Functor

case class Const[A, B](getConst: A) {
  def retag[C]: Const[A, C] = this.asInstanceOf[Const[A, C]]
}

object Const {
  implicit def functor[A]: Functor[({type λ[α] = Const[A, α]})#λ] = new Functor[({type λ[α] = Const[A, α]})#λ] {
    override def map[B, C](fa: Const[A, B])(f: B => C): Const[A, C] = fa.retag[C]
  }
}
