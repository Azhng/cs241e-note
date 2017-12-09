# Lambda Calculus

```
e -> v | e e | λv.e
     ^   ^     ^
     |   |     function creation, `v` is the parameter, `e` is the body 
     |   expr followed by expr, means function call 
     |   first `e` is the funciton / closure, second `e` is the argument 
     expr evaluate to values 

(λv.e1) e2 -> e1 [e2 / v]  β-reduction (substitution)

(λv.v v) (a b c) -> (a b c) (a b c)
(λv.v v) (λx. x x) -> (λx. x x) (λx. x x)
λy.(λx. λy. x y) y) -> λy. λy.y y
λy. (λx. λy'. x y') y -> λy. λy'. y y'
```

Abastraction :

``` Scala
object Lambda {
    abstract class Expr
    case class Var(name: Symbol) extends Expr
    case class Apply(fun: Expr, args: Expr) extends Expr
    case class λ(name: Symbol, body: Expr) extends Expr 
    implicit def toVar(name: Var): Expr = Var(name)
}



One = λ(f, λ(x, f @@ x))
Two = λ(f, λ(x, f @@ (f @@ x)))
Zero = λ(f, λ(x, x))
Succ = λ(n, λ(f, λ(x, n @@ f @@ (f @@ x))))


```

