package lensimpl.macros

import lensimpl.monocle1.Lens

import scala.reflect.macros.blackbox.Context

/** Generate efficient Lens */
object GenLens {
  def apply[S, A](fieldName: String): Lens[S, A] = macro GenLensImpl.mkLens_impl[S, A]
}

object GenLensImpl {

  def mkLens_impl[S: c.WeakTypeTag, A: c.WeakTypeTag](c: Context)(fieldName: c.Expr[String]): c.Expr[Lens[S, A]] = {
    import c.universe._

    val (sTpe, aTpe) = (weakTypeOf[S], weakTypeOf[A])

    val strFieldName = c.eval(c.Expr[String](c.untypecheck(fieldName.tree.duplicate)))

    val fieldMethod = sTpe.decls.collectFirst {
      case m: MethodSymbol if m.isCaseAccessor && m.name.decodedName.toString == strFieldName => m
    }.getOrElse(c.abort(c.enclosingPosition, s"Cannot find method $strFieldName in $sTpe"))

    val constructor = sTpe.decls.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor => m
    }.getOrElse(c.abort(c.enclosingPosition, s"Cannot find constructor in $sTpe"))

    val field = constructor.paramLists.head
      .find(_.name.decodedName.toString == strFieldName)
      .getOrElse(c.abort(c.enclosingPosition, s"Cannot find constructor field named $fieldName in $sTpe"))

    c.Expr[Lens[S, A]](q"""
      import lensimpl.monocle1.Lens
      import lensimpl.typeclass.Functor

      new Lens[$sTpe, $aTpe]{
        def get(s: $sTpe): $aTpe =
          s.$fieldMethod

        def set(a: $aTpe, s: $sTpe): $sTpe =
          s.copy($field = a)

        def modify(f: $aTpe => $aTpe)(s: $sTpe): $sTpe =
          s.copy($field = f(s.$fieldMethod))

        def modifyF[F[_]](f: $aTpe => F[$aTpe])(s: $sTpe)(implicit F: Functor[F]): F[$sTpe] =
          F.map(f(s.$fieldMethod))(a => s.copy($field = a))
      }
    """)
  }

}
