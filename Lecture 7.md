# Control structures : `If` / `While`

```
if(e1, op, e2) T else E 
```

Evaluation code: 
``` Scala 
def evaluate(e1: Expression, op: Operator, e2; Expression): Code = {
    withTempVar{t1 => 
        block(
            evaluate(e1),
            "write t1 <- $3",
            evaluate(e2),
            "read $4 <- t1",
            "op, bne/beq", // left as exercise, branch to the else label
            T,
            beq "endLabel",
            Define("elseLabel"),
            E
            Define("endLabel")
        )
    }
}
```

`while` is similar to `if` 


# Arrays 
- contiguous area of memory 
- keep track of address at element 0 
- to access element `i`, add `c*i` to start address 
- need to read(`deref`)/write(`assignToAddr`) memory at computed address 


# Register Allocation 
- assigns registers / stack locations to variables 

Pipeline: 

`code-with-variables -(register alloc)-> code-with-registers / stack locations`

## Definiton: 
=> A variable is `live at program point p` its value at `p` may be read later in the program

## Definition 
=> the `live range` at a variable is the set at program points where it is live 

Varaibles can share a register if their `live ranges` are disjoint 

E.g.
``` Scala
t1 = a + b   // only t1 is alive at this point 
t2 = t1 + c  // t2 is live, t1 is (no longer live) dead 
t3 = t2 + d  // t3 is live, t1/t2 are dead
t4 = t3 + e  // t4 is probably live, it depnds on what comes after, t1/t2/t3 are dead
```

E.g.
``` Scala
t1 = a * b      // t1 is live                              r1 = a * b
t2 = c * d      // t2 is live                              r2 = c * d 
t3 = t1 + t2    // t3 is live, t1/t2 are dead              r1 = r1 + r2
t4 = e * f      // t3/t4 are live, t1/t2 are dead          r2 = e * f
t5 = t3 - t4    // t3/t5 are live, t1/t2/t4 are dead       r2 = r1 - r2
g = t3 + t5     // t3/t5 probably are live                 g  = r1 + r2
```
  
  
- The start of the live range is always after a write to a variable 
- The end of the live range is always before a read 
- A variable is dead if:
    - it is never read in the rest of the program 
    - it will be overwritten before it is read 

## Definition 
=> `interference graph`:
- vertices = variables 
- edge if two variables live at same time 
```
t1 (r1)         t2 (r2)
   +----------+



  (r1)           (r2)
  t3            t4
    +---------+
    |
    |
    |
    +
   t5(r2)
```

## Definition
=> a `colouring` assigns a colour(register) to each vertiex so that each edge connects different colours/registers 

Finding a minial clouring for an arbitrary graph is NP-hard 

Simple greedy tend to work well: 
``` 
foreach vertex v {
    colour v with lowest-numbered color not yet used by any neighbours 
}
```

If graph requires more colours than registers, allocated some colors to memory locations on stack 