# Cheney's Copying Garbage Collector 

- split heap into two havles "semispaces" 
- allocate in from-space 
- when from-space is full:
    - compact from from-space to to-space, 
    - switch from-space and to-space 
    
```
    _______
   | code  |
   |       |
   |       |
   |       |
   |_______|
   | used  | <- heapStart 1/4
   |       |
   |       |
   |       |
   | - - - |
   | free  | <- frome-space / heapPtr 
   |       |
   |       |
   |_______|
   |       | <- to-space / heap Middle 1/2 / semi-space top
   |       |
   |       |
   |       |
   |_______| <- heap top 3/4
   | stack | 
   |       |
   |       |
   |       |
   |_______|


```

Algorithm 

``` Scala
def init = {
    heapPtr      = heapStart
    semiSpaceTop = heapMiddle 
}

def allocate(wanted) = {

    if(heapPtr + wanted > semiSpaceTop) {
        gc();
    }

    val ret = heapPtr;
    heapPtr = heapPtr + wanted 
    ret 
}

def gc() = {

    val toSpace = if(semiSpaceTop == heapMiddle) heapMiddle else heapStart
    var free = toSpace

    var scan = dynamicLink // "top" of stack, not including frame of gc 

    while(scan < memSize) {
        forwardPtr(scan)
        scan = scan + size(scan)
    }

    scan = toSpace
    while(scan < free) {
        forwardPtr(scan)
        scan = scan + size(scan)
    }

    semiSpaceTop = toSpace + semiSpaceSize
    heapPtr = free

}

def forwardPtr(block) = {
    for each offset `o` in `block` that holds a pointer {
        val newAddr = copy(deref(block+o)) // copy the pointed chunk into toSpace and return the new address in toSpace 
        assignToAddr(block + o, newAddr)
    }
}

def copy(block) = {
    if(`block` is not in fromSpace) { block }
    else {
        if(size(block) >= 0) { // not yet copied 
            copyChunk(free, block)
            setNext(block, free) // leave forwarding 
            setSize(block, 0 - size(block)) // mark block as copied 
            free = free + size(free)
        }
        next(block) // return forwarding address, which is the address of block on the toSpace 
    }
}

```




