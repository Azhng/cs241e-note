// (ctd)

Implement a stack: 
- designate storage (register) to store address at top of stack (stack pointer)
- push a word, decrement sp by 4 
- pop a word, 
  - increment sp by 4 

convention: use $30 as the sp, $30 is initialized to the end of memory 

E.g. read "c" (two wrods from top of stack) into $1 
- __**symbol table**__ maps each variable to stack offset 

Note: symbol table is created in compile time 

``` MIPS
LW (1, 8, 30)
```

Define: **frame pointer** is a copy of stck pointer made at start of procedure thatdoes not change for the during of the procedure invocation. It enables us to modify stack pointer

Convention: $29 is the frame pointer 

 ## Summary 

stack pointer: $30
- changes during a procedure invocation (push/pop) 
- the value is only known at run-time 

frame pointer
- stay constant within a procedure invocation 
- only known at run-time 

symbol table 
- maps each variable to offsets from frame pointer 
- only around during compile time 



## Assignment Convention:
- all data in memory will be organized in "chunks"
- block of consecutive memory locations 
- always push/pop a chunk at a time 
- offsets indexed by variables (symbol table variable -> offset in chunk)
- read/write variable in chunk 
    - need address of chunk in some register: the **base** register (run-time value)
    - add offset of variable, using **LW**/**SW** (compile time constant)
    - the size of the chunk is inferred by compiler during compile time by calculating the sizes of all variables 

```
 __________
|   size   | 0 ~ reserved
|    all   | 4 ~ reserved 
|    a     | 8
|    b     | 12
|____c_____| 16
```

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