package lensimpl.data

import lensimpl.typeclass.Strong

case class Forget[R, A, B](run: A => R){
  def retag[C]: Forget[R, A, C] = asInstanceOf[Forget[R, A, C]]
}

object Forget {
  implicit def strong[R]: Strong[({type λ[a, b] = Forget[R, a, b]})#λ] = new Strong[({type λ[a, b] = Forget[R, a, b]})#λ] {
    override def dimap[A, B, C, D](pab: Forget[R, A, B])(f: C => A)(g: B => D): Forget[R, C, D] =
      Forget[R, C, D](c => pab.run(f(c)))

    override def lmap[A, B, C](pab: Forget[R, A, B])(f: C => A): Forget[R, C, B] =
      Forget[R, C, B](c => pab.run(f(c)))

    override def rmap[A, B, C](pab: Forget[R, A, B])(f: B => C): Forget[R, A, C] =
      pab.retag[C]

    override def second[A, B, C](pab: Forget[R, A, B]): Forget[R, (C, A), (C, B)] =
      Forget[R, (C, A), (C, B)]{
        case (c, a) => pab.run(a)
      }
    override def first[A, B, C](pab: Forget[R, A, B]): Forget[R, (A, C), (B, C)] =
      Forget[R, (A, C), (B, C)]{
        case (a, c) => pab.run(a)
      }
  }
}
