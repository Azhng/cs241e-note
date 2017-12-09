object Lambda {
  abstract class Expr {
      override def toString = prettyPrint(this)
    def @@(arg: Expr): Expr = Apply(this, arg)
  }
  case class Var(name: Symbol) extends Expr
  case class Apply(fun: Expr, arg: Expr) extends Expr
  case class λ(name: Symbol, body: Expr) extends Expr
  implicit def toVar(name: Symbol): Expr = Var(name)

  def prettyPrint(expr: Expr, followedByStuff: Boolean = false): String = expr match {
    case λ(name, e2) if followedByStuff => s"(λ${name.name}.$e2)"
    case λ(name, e2) => s"λ${name.name}.$e2"
    case Apply(e1, e2: Apply) => prettyPrint(e1, true) + s" ($e2)"
    case Apply(e1, e2) => prettyPrint(e1, true) + " " + prettyPrint(e2, followedByStuff)
    case Var(name) => s"${name.name}"
  }

  val step: PartialFunction[Expr, Expr] = {
    case Apply(λ(name, body), arg) => subst(body, name, arg)
    case Apply(fun, arg) if step.isDefinedAt(fun) => Apply(step(fun), arg)
    case Apply(fun, arg) if step.isDefinedAt(arg) => Apply(fun, step(arg))
    case λ(name, body) if step.isDefinedAt(body) => λ(name, step(body))
  }

  def subst(expr: Expr, name: Symbol, replacement: Expr): Expr =
    expr match {
      case Var(name2) => if(name2 == name) replacement else expr
      case Apply(fun, arg) =>
        Apply(subst(fun, name, replacement), subst(arg, name, replacement))
      case λ(name2, body) =>
        val newName = uniqueName(name2, freeVariables(replacement) + name)
        val newBody = subst(body, name2, Var(newName))
        λ(newName, subst(newBody, name, replacement))
    }

  def uniqueName(name: Symbol, badNames: Set[Symbol]): Symbol =
    if(badNames.contains(name)) uniqueName(Symbol(name.name + "'"), badNames)
    else name

  def freeVariables(expr: Expr): Set[Symbol] = expr match {
    case Var(name) => Set(name)
    case Apply(fun, arg) => freeVariables(fun) ++ freeVariables(arg)
    case λ(name, body) => freeVariables(body) - name
  }

  def reduce(e: Expr): Expr =
    if(step.isDefinedAt(e)) reduce(step(e))
    else e

  def main(args: Array[String]): Unit = {
    val ID = λ('x, 'x)
    val FIRST = λ('x, λ('y, 'x))
    step(step(Apply(Apply(FIRST, 'zz), 'zzz)))
    println(FIRST @@ 'zz @@ 'zzz)
    println(reduce(FIRST @@ 'zz @@ 'zzz))

    val TRUE = FIRST
    val IF = λ('c, λ('t, λ('e, 'c @@ 't @@ 'e)))
    val FALSE = λ('x, λ('y, 'y))
    println(reduce(IF @@ TRUE @@ 'then @@ 'else))
    println(reduce(IF @@ FALSE @@ 'then @@ 'else))
    val ONE = λ('f, λ('x, 'f @@ 'x))
    val TWO = λ('f, λ('x, 'f @@ ('f @@ 'x)))
    val ZERO = λ('f, λ('x, 'x))
    val SUCC = λ('n, λ('f, λ('x, 'n @@ 'f @@ ('f @@ 'x))))
    println(ONE)
    println(TWO)
    println(reduce(SUCC @@ TWO))
    def NUM(n: Int): Expr = if(n==0) ZERO else reduce(SUCC @@ NUM(n-1))
    println(NUM(6))
    val PLUS = λ('m, λ('n, λ('f, λ('x, 'm @@ 'f @@ ('n @@ 'f @@ 'x)))))
    println(reduce(PLUS @@ TWO @@ NUM(3)))
    val TIMES = λ('m, λ('n, λ('f, 'n @@ ('m @@ 'f))))
    println(reduce(TIMES @@ TWO @@ NUM(3)))
    val PAIR = λ('car, λ('cdr, λ('select, 'select @@ 'car @@ 'cdr)))
    val CAR = λ('pair, 'pair @@ TRUE)
    val CDR = λ('pair, 'pair @@ FALSE)
    val AB = PAIR @@ 'a @@ 'b
    println(reduce(CAR @@ AB))
    println(reduce(CDR @@ AB))

    // given pair (a,b), return (b, b+1)
    val INCPAIR = λ('pair, PAIR @@ (CDR @@ 'pair) @@ (SUCC @@ (CDR @@ 'pair)))
    val ZERO_ZERO = reduce(PAIR @@ ZERO @@ ZERO)
    val ZERO_ONE = reduce(INCPAIR @@ ZERO_ZERO)
    val ONE_TWO = reduce(INCPAIR @@ ZERO_ONE)
    val PRED = λ('num, CAR @@ ('num @@ INCPAIR @@ ZERO_ZERO))
    println(reduce(PRED @@ NUM(5)))
    val ISZERO = λ('num, 'num @@ λ('x, FALSE) @@ TRUE)

    val MKFACTORIAL = λ('factorial, λ('num,
      IF @@ (ISZERO @@ 'num) @@ ONE @@
        (TIMES @@ 'num @@ ('factorial @@ (PRED @@ 'num)))))

    val F4 = MKFACTORIAL @@ (MKFACTORIAL @@ (MKFACTORIAL @@ (MKFACTORIAL @@ ID)))
    println(reduce(F4 @@ NUM(2)))
    println(reduce(F4 @@ NUM(3)))
    println(reduce(F4 @@ NUM(4)))


    val Y = λ('f, λ('x, 'f @@ ('x @@ 'x)) @@ λ('x, 'f @@ ('x @@ 'x)))
    println(Y)
    val Yg = Y @@ 'g
    println(step(Yg))
    println(step(step(Yg)))
    println(step(step(step(Yg))))
    println(step(step(step(step(Yg)))))

    val FACTORIAL = Y @@ MKFACTORIAL
    println(reduce(FACTORIAL @@ NUM(4)))
    println(reduce(FACTORIAL @@ NUM(5)))
  }
}
