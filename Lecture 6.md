(ctd) 

## Expression 
e.g. 

`a * b + (a * d)`

we represent the expression as a tree
```
           +
          / \
         *   *
        / \ / \
       a  b c  d
```

Techniques :

1. use a stack (used by CS241 regular sections)
``` Scala
// generate code to evaluate `e1 op e2` 
// the convention we use is to put the result of evaluation result 
// in $3
def evalute(e1 : Expression, op : Operator, e2 : Expression): Code = 
    block(
        evaluate(e1),
        "push $3", // replace quoted string with mips code 
        evaluate(e2), 
        "pop $4", 
        "$3 = $4 op $3" 
    )
```


Recursive descend is general :) 
but inefficient generated code :(
and very difficult to optimize further :(

2. We can use __Variables__ / __Virtual Registers__

`t1 = a * b`

`t2 = c * d`

`t3 = t1 + t2`

``` Scala
// generate code to evaluate e1 op e2 and put result in a varialbe 
def evaluates(e1 : Expression, op : Operator, e2 : Expression) : (Code, Variable) = {
    val (c1, v1) = evaluate(e1)
    val (c2, c2) = evaluate(e2)

    val v3 = new Variable(#uuid#)

    val code = block(
        c1,
        c2,
        "v3 = v1 op v2"
    )

    (code, v3) 
```
}

:( it looks like we ended up using a lot of variables,
is it bad? 
not really if we can efficiently map __many__ varaibles to __few__ registers/memory locations

:) flexiable but general

:) gerated code easy to optimize further 

:( machine language instructions used __registers__ but not __variables__

3. Compromise: use variables, operate on registers (Assignment 4)

``` Scala 
// save result in $3
def evaluate(e1: Expression, op: Operator, e2; Expression): Code = {
    withTempVar{t1 => 
        block(
            evaluate(e1),
            "write t1 <- $3",
            evaluate(e2),
            "read $4 <- t1",
            "$3 = $3 + $4"
        )
    }
}
```

compared to technique #2, all the operations are done on the registers rather than on variables 



