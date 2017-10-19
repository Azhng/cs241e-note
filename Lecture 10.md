# Lecture 10: More on nesting procedure 


``` Scala
f() { // nesting depth: 0
    g() {} // nesting depth: 1
    h() {  // nesting depth: 1
        k() {} // nesting depth: 2
    }
}
```

## Define: __static link__ is a pointer to the frame of the statically enclosing the procedure 
## Define: __nesting depth__ of a procedure is the number of procedures that it is nested inside of 


Algorithm
```
depth(outer procedure) = 0
if p nested immediately in p':
    then depth(p) = depth(p') + 1
```


To access a variable(`eliminateVarAccessA6`)
```
1. n = depth(current procedure) - depth(declaring procedure of the variable)
2. follow the static link `n` times, then access variable in that frame 
```

At a call site, compute static link:
```
depth(static link) = depth(callee) - 1
n = depth(current procedure) - depth(static link)
n = depth(current procedure) - depth(callee) + 1
if n == 0: then static link = frame_pointer
else: static_link = follow static link `n` times 
```


## Examples: 
`f` calls `g` :
```
depth(f) = 0
depth(g) = 1
n = 0 - 1 + 1 = 0
static link = frame pointer 
```


`g` calls `h` 
```
depth(g) = 1
depth(h) = 1
n = 1 - 1 + 1 = 1
follow g's static link once, 
static link = g's static link
```



`k` calls `g` 
```
depth(k) = 2
depth(g) = 1
n = 2 - 1 + 1 = 2
follow k's static link once,
follow h's static link once,
static link = h's static link
```


`f` calls `k` 
```
illegal, because `k` is local to `g` 
n = -1 
```




# First-class functions
## A.k.a. __Code is data__ / function values 

in Scala:
``` Scala 
def procedure(x: Int) = x + 1
var increase: (Int)=>(Int) = procedure

increase(5) // 6

increase = { x => x + 2 }

increase(5) // 7

List(5,6,7).map(increase) // 7,8,9

def twice(fun: Int=>Int): Int=>Int = {
    x => (fun(fun(x)))
}

increase = twice(increase)

increase(5) // 9

increase = { x => x + increment } // compile-error 
```

## A `Free Variable` in an expression is a variable that is not defined within the expression 

E.g. 
- `x` is free in `x + increment`
- `x` is not free in `{ x => x + increment }`


## An expression is `closed` if it contains no free variable 

``` Scala
def increaseBy(increment: Int): Int=>Int = { x => x + increment }

increase = increaseBy(3)

increase(5) // 8

List(increaseBy(1), increaseBy(2), increaseBy(3))
    .map(fun => fun(5))
 // List(6, 7, 8)

```

## A `closure` is a pair of 
- the code of a function body 
    - the code label 
- and `environment` for the free variables in the function body 
    - frame at enclosing proceure 
    - which is basically the static link 

``` Scala 
var functionValue: (Int=>Int)
def constuctor(n: Int): (Int=>Int) = {
    var b = a * 2
    def procedure(c: Int): Int = {a + b + c}
    procedure // closure creation 
}

functionValue = constructor(42)
//              ^~~~~~~~~~~~~~
//              Call(...), `constructor` is a Procedure(...)
functionValue(5)
// ^~~~~~~~~~~~~
// CallClosure(...)
// essentially we are calling constructor(42)(5)
// target is Code that eventually evaluates to a Closure 
//           ^~~~
//           code that reads variable functionValue()
```

`functionValue` must store:
- label at procedure code 
- static link 

## At closure creation, compute static link just like in a normal call 
## At closure call, passenvironment from closure as the static link 


## Question: What is the _Extend_ of `a` and `b` ? 
- begins at the beginning of the `constructor`
- we *CANNOT* allocate frame of `constructor` on the stack 
    - because we need to access `constructor`'s local vars after the call returns 
- ends when all copies of the closure values have been lost or overwritten
    - we can just heap in this case 

## A `heap` is a data structure that manages memory, so it can be allocated / freed at any time 
(Assignment 11: implementing a real heap)

(Assignment 6: simplified heap, allocates and never frees )

## Question: Which procedure on heap? 
- a closure can access frames of all procedures in which it is nested 
    - that means these frames must be on heap 
- frame of a procedure that contains p (a procedure) anywhere inside it must be on heap if we ever make closure out of p


// ignore this 
``` Scala
// scala version
def increaseBy2(increment: Int) :(Int)=>Int = {
    def procedure(x: Int) = { x + increment }
    procedure
}

def main(a: Int, b: Int) = (increaseBy2(a))(b)

main(3, 5) // 8

// lacs version 
def v(variable: Variable): Code = read(Reg.result, variable)

val increment = new Variable("inrement")
val increaseBy = new Procedure("increaseBy", Seq(increment), Seq())

val x = new Variable("x")
val procedure = new Procedure("procedure", Seq(x), Seq(), Some(increaseBy))
// ^~~~~~~
// this is nested within the `increaseBy` procedure 

procedure.code  = binOp(v(x), plus, v(increment))
increaseBy.code = Closure(procedure)

val main = new Procedure("main", Seq(a, b), Seq())
val parameter = new Variable("parameter")
main.code = CallClosure(call(increaseBy, v(a)), Seq(v(b),           Seq(parameter))
//                                                  ^~~             ^~~~~~~~~~~~~~
//                                        closure call param      means closure takes one param

val machineCode = compilerA6(Seq(main, increaseBy, procedure))

val endState = A1.loadAndRun(machineCode.words, Word(encodeSigned(3)), Word(encodeSigned(5)))
// Reg(3) should be 8 
```