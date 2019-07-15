package lensimpl.subtyping

trait Optional[A, B] { self =>
  def getOption(from: A): Option[B]
  def set(to: B): A => A

  def modify(f: B => B): A => A = a => getOption(a).fold(a)(set(_)(a))

  def compose[C](other: Optional[B, C]): Optional[A, C] = new Optional[A, C] {
    def getOption(from: A): Option[C] = self.getOption(from).flatMap(other.getOption)
    def set(to: C): A => A = self.modify(other.set(to))
    override def modify(f: C => C): A => A = self.modify(other.modify(f))
  }
}

object Optional {
  def apply[A, B](_getOption: A => Option[B])(_set: (A, B) => A): Optional[A, B] = new Optional[A, B] {
    def getOption(from: A): Option[B] = _getOption(from)
    def set(to: B): A => A = _set(_, to)
  }
}
