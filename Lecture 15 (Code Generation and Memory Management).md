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
- deallocate?: we need a data structure  -> add header to block/chunks 

```
New Chunk
    _________
   |  size   | <- header 
   |  next   | 
   |---------|
   |  data   |
   |_________|

E.g.
     
     ________
100 | size 8 | <- heap start 
104 |next 108|
    ----------
108 | size 48|
112 |next 156| <- points pass heap means the last chunk in the heap 
116 |        |
120 |        |
124 |        |
128 |        |
132 |        |
136 |        |
140 |        |
144 |        |
148 |        |
152 |        |
156 |        |

```


To implement such heap: 

``` Scala

def setSize(block, size) = assignToAddr(block, size)
def setNext(block, size) = assignToAddr(block, size)
def next(block) = ??? 
def size(block) = ???

def init() = {
    val block = heapStart + 8
    setSize(heapStart, 8)
    setSize(block, heapStart - 8)
    setNext(heapStart, block)
    setNext(block, heapStart + heapSize)
}

def malloc(wanted) = {

    def find(previous) = { 

        val current = next(previous)
        if(size(current) < wanted + 8) {
            find(current)
        } else {
            if(size(current) >= wanted + 16) { 
                // split block 
                val newBlock = current + wanted + 8
                setSize(newBlock, size(current) - (wanted + 8))
                setSize(current, wanted + 8)
                setNext(newBlock, next(current))
                setNext(previous, newBlock)
            } else { 

            }

            setNext(previous, next(current))
            current            
        }
    }

    find(heapStart)
}

// Ondrej: "There is one line of code still missing. Go figure it out yourself"
def free(tofree) = {

    def find(previous) = {
        val current = next(previous) 
        if(current < tofree) {
            find(next(previous))
        } else {

            // merge with current 
            if( (tofree + size(tofree)) == current && current < (heapStart + heapSize) ) {
                setSize(tofree, size(tofree) + size(current))
                setNext(tofree, next(current))
            } else {
                setNext(tofree, current)                
            }


            // merge with previous 
            if( (previous + size(previous)) == tofree && previous > heapSize ) {
                setSize(previous, size(previous) + size(tofree))
            } else {
                setNext(previous, tofree)
            }

        }
    }

    find(heapStart)
}

```











