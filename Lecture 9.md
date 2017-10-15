# Nested procedure
E.g.
``` Scala 
def f() = {
    val x=2
    val w=5

    def g() = {
        val y=3
        val w=7
        x + y + h()
    }

    def h() = {
        val z=4
        z + w
        //  ^
        //  which `w` is this ? 
        // the one is g() or the one in f() 
        // if w is 5, then this language is using static/lexical scope 
        //     that is h() uses f()'s `w` 
        //     names are resolved statically enclosing procedure 
        //     (in the source code)
        // else it is called Dynamic scope:
        //     h() uses g()'s `w` (which is 7)
        //     variable reference is resolved at runtime 
        //     (in the dynamic stack at calls)

    }

    g()
}
```


E.g. using nested procedure to implement `while` loop 
``` Scala 
// loop version
def m() = { 
    var i=0;
    var j=0;

    while(i<10) {
        // do something
        i++
        j++
    }

    i+j
}

// nested func version 
def m2() = {
    var i=0;
    var j=0;

    def loop() = {
        if(i<10){
            i++
            j++
            loop()
        }
    }

    loop()
    i+j
}
```


How to access `f`'s `w` from inside of `h` 
- how do we skip `g`'s `'w` 
- dynamic link doesn't really serve our purpose here 
- thus we need a static link 

## Definition: 
- a `static link` is a pointer to the frame of the statically/lexically enclosing procedure of the currently executing procedure
```
    frame

 fp+-> +-----+
h      |     |
       |     |
       |     |
       |dl  pp
       ++----+
        v    +----+
        v    |    |   paramChunk
        v    |    |
        v    |  sl+------------+
        v    +----+            |
       +v----+                 |
       |     |                 |
g      |  w  |                 |
       |     |                 |
       |dl  pp                 |
       ++----+                 |
        v    +----+            |
        v    |    |            |
        v    |    |            |
        v    |  sl+------+     |
        v    +----+      |     |
       +v----+           |     |
       |     |           v     |
f      |  w <-<---------<+<----+
       |     |
       |dl  pp
       +-----+

```