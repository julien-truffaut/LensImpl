package lensimpl

package object data {

  type Id[A] = A
  def Id[A](a: A): Id[A] = a

}
