# Context Sensitive Analysis

## Different definitions of __Type__

### Definition: a __type__ is a collection of values 
### Definition: a __type__ is an interpretation of sequence of bits 
### Definition: a __type__ is a computable property of a program that guarentees some property of its execution 

E.g. 
-  `1001` 
    - unsigned integer: 9
    - two's complement: -7 
- `0100 0001` 
    - ASCII : 'A'



## Type: 

### Machine Language: 
-  32 bits binary string in __location__

### Lacs/C/Java: 
- value of some type in __varriables__

### Lacs
- `Int` 
    - between -2^31 and +2^31-1 with arithmetic module 2^32 
- `(type, ...)=>type`
    - function that takes arguments of specified type returns value of return type 


### Definition: a __type system__ is a set of rules that computes the type of an expression of its subexpressions 
### Definition: a type system is __sound__ if whenever it computes a type `τ` for expression `e`, then `e` evaluates to a value in `τ` 

## To define type rules: it is similar to the definition of connectives from CS 245E 
E.g. 

```
            Γ(ID)=τ                Γ(ID)=τ  Γ ⊢ E:τ
           ___________            _________________
NUM:Int    Γ ⊢ ID : τ              Γ ⊢ ID = E : τ


Γ ⊢ E1:Int   Γ ⊢ E2:Int
_________________________
Γ ⊢ E1 + E2 : Int  
Γ ⊢ E1 - E2 : Int
Γ ⊢ E1 * E2 : Int
Γ ⊢ E1 / E2 : Int


Γ ⊢ E1:τ'   Γ ⊢ E2:τ
_____________________
Γ ⊢ E1 ; E2 : τ


Γ ⊢ E1:Int   Γ ⊢ E2:Int  Γ ⊢ E3:τ    Γ ⊢ E4:τ
_____________________________
Γ ⊢ if (E1 == E2) E3 else E4 : τ
Γ ⊢ if (E1 != E2) E3 else E4 : τ
Γ ⊢ if (E1 <= E2) E3 else E4 : τ
Γ ⊢ if (E1 >= E2) E3 else E4 : τ
Γ ⊢ if (E1 >  E2) E3 else E4 : τ
Γ ⊢ if (E1 <  E2) E3 else E4 : τ

         _
Γ ⊢ E': (τ)=>τ'   ∀i Γ ⊢ Ei : τi
___________
       _
Γ ⊢ E'(E) :


```












