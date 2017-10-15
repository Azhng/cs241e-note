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
```

