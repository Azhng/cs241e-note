# Lecture 8: Procedure

A `procedure` is an abstraction that encapsulates a reusable piece of code 
- the `caller` transfers control to `callee` (the procedure) by modifying `PC`
    - passing arguments for procedure parameters 
- `callee` (procedure) transfers control back to `callee` 
    - returns a value 


`caller` and `callee` must agree on __`conventions`__ 
- where (memory/registers) arguments/return values are found 
- type of parameters/return value 
- which registers `callee` may modify 
- who allocates / free memory 
- `callee` allocates / deallocates the frame 
- `caller` modifies `Reg.savedPC` (caller-save)
- `callee` preserves `Reg.framePtr` (callee-save)
- `callee` preserves `Reg.stackPtr` (callee-save)
- `callee` modifies `Reg.result` for return value (caller-save)
- `callee` modifies all other registers (caller-save)
- `callee` frees the parameters chunk 
- `caller` allocates the parameter chunk and passess its address in `Reg.allocated`

## Definition: 
- `dynamic link` is a pointer to the frame at the procedure that called the current procedure 
- `prologue/epilogue` is the block of code at beginning/end of a procedure that sets up/cleans up the frame and register 

## CS 241E convention 
### Caller 
``` MIPS
"eval args",
"write to temp_vars",
"stack.allocate (parameters)",
"param1 = temp1",
"param2 = temp2",
LIS (Reg.targetPC),
Use (proc),                           # load callee address into register 
JALR (Reg.targetPC)                   # Reg.targetPC = $8
```

### Callee
``` MIPS
// prologue
Define(proc)                          # defining a label 
"Reg.savedParamPtr = Reg.allocated", 
"dynamicLink = Reg.framePtr",     
"stack.allocate (frame)",             # we can use allocated register here 
"savePC = Reg.savedPC",               # save the return address into a variable 
"Reg.framePtr = Reg.allocated",
"paramPtr = Reg.savedParamPtr",


"body",                               # body of the procedure code

// epilogue
Reg.savedPC = savedPC,                # read back the savedPC value to register 
"Reg.framePtr = dynamicLink", 
"stack.pop(frame)",        
"stack.pop(parameters)",
JR (Reg.savedPC)                      # Reg.savedPC = $31
```


## Stack

### Callee' Frame
```
 ______________
|   size       |
|   reserved   |
|   vars       |
|   temp vars  |
|______________|
|  savedPC     |
|  dynamicLink | <- a.k.a. old frame pointer 
|  paramPtr    | 
|______________|
|   ...        |
|   ...        |
```

### Param chunk 
```
 ______________
|   size       |
|   reserved   |
|______________|
|              |
|  parameters  |
|              |
|______________|
```


### Caller's Frame
```
 ______________
|   ...        |
|   ...        |
```


## When I call a procedure, which registers might chagne? (caller save)
## When I call a procedure, which registers may not chagne? (callee save)

``` Scala
// eliminateVarAccessA5
def eliminateVarAccessA5
// if v is a varaible/temp variable (but not parameter)
//     same as the eliminateVarAccessA3
//     read/write v at offset in frame (Reg.framePtr)
// else if it is a parameter 
//     read paramPtr from frame (Reg.scratch)
//     read/wrte v in parameter chunk 

```