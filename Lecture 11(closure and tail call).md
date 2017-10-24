# Object and State 

Definiiton: An `object` is a structure that contains: 
- data (state) 
- methods/procedures (behaviour)

``` Scala
class Counter {
    var value = 0
    def get() = value
    def incrementBy(amount: Int) = {
        value = value + amount
    }
}

val c = new Counter
c.get() // 0
c.incrementBy(42)
c.incrementBy(-5)
c.get() // 37
```

What if we chagne the `class` to a function ?
``` Scala
def newCounter(): (()=>Int, Int=>Unit) = {
    var value = 0;
    def get() = value
    def incrementBy(amount: Int) = {
        value = value + amount
    }

    return (get, incremntBy)
}

val c2 = newCounter
c2._1() // 0
c2._2(42)
c2._2(-5)
c2._1() // 37 
```

Observe that in this case the class behave bascially same as a closure

Defintion: And `object` is a collection of closures (indexed by names) that close over a common environment






==================================================================================
Recall, we can replace while loop using nested procedure 

``` Scala
def m() = {
    var i=0
    var j=0
    while(i<10){
        i = i + 1
        j = j + i
    }
    i + j
}

def m2() = {
    var i=0
    var j=0
    def loop(): Unit = {
        if(i < 10) {
            i = i + 1
            j = j + i
            loop()
        }
    }
    loop()
    i+j
}
```


if the number is huge, `loop` should run out of memory since it will potentially keep calling itself and eventually eats up all the stack space 

Potentially we could free the function frame before the function calls the next `loop`, this way the space utilized by recursion is now constant 

This is called _Tai call optimization_

We can only do this optimization can only be donw when a call is at the very end of a procedure 

Identifying tail call
- a call is in tail position if it is the last thing executed before epilogue
- maybe nested in if statement 

E.g.
``` Scala
def f() = {
    def g() = {
        // g could access at f
    }
}
```

- tail call optimiztion is not safe if it is nested in caller


Assembly code of `loop`

```
evaluate arguments 
allocate param chunk 
write arguments into params
JALR

// epilogue 
Reg.savedC = savedPC
Reg.fp = dynmaicLink
stack.pop // frame 
stack.pop // params 
JR(31÷o) 
```


We can rearrange this calling procedure 

```
evluates args
allocate param chunk
write args into param
Reg.savedPC = savedPC
Reg.fp = dynmaicLink
stack.pop
stack.pop
stack.pop
allocate paramB
copy paramA into paramB
JR
```