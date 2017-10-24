# Formal Languages + DFAs + NFAs

## Question: What _is_ in the language and what _is not_ 

Example: a definition of a language 
```
L1 = {R2D2, cat, BBQ}
```


## `Recognition` 
- Given language `L` and word `w`, we ask: "is `w` in `L` ?"
- Can we always create a decision algorithm 
    - NO 


## `Interpretation` / `Translation`
- Given language `L` and word `w` 
    - `w` has a meaning; that is an interpretation or translation 
- E.g.
    - `L` = C language, `w` = int, which is a type 
    - `L` = French, `bonjour` = hello


- Analysis = Recognition + Translation


## Definition: 
- Alphabet (Σ) 
    - **Finite** set of symbols
    - Examples:
        - {0, 1}
        - {a, ..., z}
        - {+, -, int, ..., void, for, ...}
        - `ε` the empty word 
- A set of wrods over some alphabet 
    - may be **infinite**
    - may be **empty**, {} or {ε}
    - Example:
        - all binary strings 
        - all English words 
        - all C++ programs 

## Classes of Language
- Finite (countable)
    - L = {alpha, R2D2, cat, dog}
    - L = all binary strings of 8 bits 
- Regular
    - L = all strings consisting of only the character 'x', E.g. xx, xxx, xxxx
    - L = {alpha, R2D2, cat, bbq} + all binary strings of 8 bits  

Note: All finite language are regular, but vice versa is not true 

- Context Free (no context is required to understand this language)
    - L = the language of balanced brackets, (), (()), ((()))
    - L = {alpha, R2D2, cat, bbq}
- Undecidable 
    - Languages that Turing machines cannot accept 
    - CS341/CS360/5

# DFAs
- Deterministic finite automata 
- A "state machine" that decides if a word is accepted or rejected 

- DFA is a 5-tuple (Σ, Q, q0, A, Δ)
    - Σ - alphabet
    - Q - finite set of states 
    - q0 ∈ Q - a start state 
    - A ⊆ Q - accepting state 
    - Δ: Q x Σ -> Q - transition function 

!["DFA"][page1]


## DEF Recognition algorithm:
- Δ⋆: Q x Σ⋆ -> Q - extended transition function 
- Δ⋆(q, ε) = q
- Δ⋆(q, head::tail) = Δ⋆(Δ(q, head), tail)
- Example:
    - Δ⋆(q, 01) = Δ⋆(Δ(q, 0), 1)

- A word `w` is accepted by the DFA is Δ⋆(q0, w) ∈ A 


# NFAs
- Non-determnistic finite automata
    - state machine where there can be more than one transition out of a state on the same symbol, hence, non-deterministic
- Accepts words if there exists any path to accepting state 

- NFS is a 5-tuple (Σ, Q, q0, A, Δ)
    - Σ - alphabet
    - Q - finite set of states 
    - q0 ∈ Q - a start state 
    - A ⊆ Q - accepting state 
    - Δ: Q x Σ -> 2^Q - transition functions 
    - 2^Q - set of all states

- A words `w` is accepted by the NFA if Δ⋆(q0, w) intersects `A` is not ϕ
    - At least ONE of the final states is accepting 
- Recognition algorithm:
    - Δ⋆: Q x Σ⋆ -> 2^Q - extended transition function 
    - Δ⋆(q, ε) = {q}
    - Δ⋆(q, head::tail) = Uq ∈ Δ(q, head), Δ⋆(q', tail)
    - Example 
        - Δ⋆(A, 0xF) = Δ⋆(Δ(B, 0), xF) U Δ⋆(Δ(E, 0))


!["NFA"][page2]

## ε transition 
- `ε-NFA`
    - NFA that has transition on nothing 
    - Move from state q -> q' without consuming input 
    - Why? 
        - See hand written note

- Note that, every ε-NFA can be converted into a NFA and every NFA can be converted into DFA 


!["ε-NFA"][page3]




## Regular Expression 
- Alternate (to DFA) method for specifying regular language 
- Used everywhere 


Rules:
- R ::= ε 
    - L(ε) = {ε}
- R ::= a ∈ Σ
    - L(a) = {a}
- R ::= R1 | R2
    - L(R1|R2) = L(R1) U L(R2)
- R ::=R1 R2
    - L(R1 R2) = {xy | x ∈ L(R1), y ∈ L(R2)}
- R ::= R⋆
    - L(R⋆) = {x1x2x3 ... xn | ∀ i xi ∈ L(R), n >= 0}



## Kleene's Theorem 
Given a regular language L, then there exists 
- A DFA specifying L 
- A regular expression specifying L  
- An NFA specifying L 
- An ε-NFA specifying L 





[page1]: https://imagehosting-50cd6.firebaseapp.com/IMG_1166.JPG "page 1"
[page2]: https://imagehosting-50cd6.firebaseapp.com/IMG_1167.JPG "page 2"
[page3]: https://imagehosting-50cd6.firebaseapp.com/IMG_1168.JPG "page 3"








