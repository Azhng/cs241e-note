# Code Generation:

we go from `parse tree (scope and types)` to `code tree` 

### Definition: a *syntax-directed translation* generates code for each node of a tree by combining the code that it generated for each subtree 

"yeah this is easy just go figure it out yourself" - Ondrej 



# Heap (memory management)

### Definition: a *heap* is some data structure for managing memory manages memory so it can be allocated and freed (returned) at any time 

## Operations: 
- `new` / `malloc` - allocate new block of memory of given size 
- `free` / `delete` - returns memory to allocation pool (to be resued) 

## To implement an heap
- easy allocation: increment a pointer 
- deallocate?: we need a data structure 

