package lensimpl.bench

object Util {

  def safeDivide(n: Int): Option[Int] =
    if(n % 2 == 0) Some(n / 2)
    else None

}
