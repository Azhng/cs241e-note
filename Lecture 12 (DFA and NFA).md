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


## Intersection of two DFAs 
- Let P be the state of DFA1 and R be the states of DFA2 
- DFA1 intersect DFA2 = 
    - Q = set of all pairs of states from P and R 
    - q0 = pair of corresponding to start state of P and R 
    - A = state-pairs where both are accepting 
    - Δ(q, a) = q' if and only if Δ(p, a) = p' and Δ(q, a) = q'
    - Example: 

!["Intersection of DFA"][page4]



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

!["ε-NFA"][page3]


- Note that, every ε-NFA can be converted into a NFA and every NFA can be converted into DFA 



## Regular Expression 
- Alternate (to DFA) method for specifying regular language 
- Used everywhere 


Rules:
- R ::= ε 
    - L(ε) = {ε}
    - empty state 
- R ::= a ∈ Σ
    - L(a) = {a}
    - letters from alphabet
- R ::= R1 | R2
    - L(R1|R2) = L(R1) U L(R2)
    - or 
- R ::=R1 R2
    - L(R1 R2) = {xy | x ∈ L(R1), y ∈ L(R2)}
    - concat 
- R ::= R⋆
    - L(R⋆) = {x1x2x3 ... xn | ∀ i xi ∈ L(R), n >= 0}
    - repetition 

E.g.: 
- (ab)⋆ = {ε, ab, abab, ........}
- a+ = a(a)⋆


## Kleene's Theorem 
Given a regular language L, then there exists 
- A DFA specifying L 
- A regular expression specifying L  
- An NFA specifying L 
- An ε-NFA specifying L 

That means that there exists ε-NFA can be converted into an NFA and an NFA can be converted into a DFA 

!["ε-NFA to DFA"][page5]

## Example:
1. The language of all English words ending in "ing"
    - `[a-zA-Z]*(ing)`
2. The language of all English words containing "issi"
    - `[a-zA-Z]*(issi[A-Za-z]*)*`
3. The language of all binary strings with even-length runs of 0s and even-length runs of 1s
    - `((11)*(00)*)*`
4. The union of (1) and (2)
    - `[a-zA-Z]*(issi[A-Za-z]*)*ing`


## Recognition vs Scanning 
### Recognition
- Is word `w` in language `L` 

### Scanning
- Given a string, divide into tokens that are in language `L`
- Examples
    - Split sentence "R2D2 bbqs lizards on Dagobah" into "words"
        - {R2D2, bbqs, lizards, on, Dagobah}
    - Split C++ program "int main() {return 0; }" into "words"
        - {int, main, (, ), {, return, 0, ;, }}
    - Split string "0xDEADBEEF 42 0xABBA" into "words"
        - {0xDEADBEEF, 42, 0xABBA}


### Scanning - Maximal Munch
- *Input*: string `w`, language `L` (specifies set of valid tokens)
- *Output*: sequence of words, `w1`, `w2`, ..., `wn` such that:
    - `w` = `w1w2w2` ... `wn`
    - `wi` ∈ `L` for all `i` 
    - For each `i`, `wi` is the longest prefix of `wi`, `wi+1` ... `wn` that is in `L`
    - or ERROR 
- Question: is it always possible that :
    - Let `L` be the language of possible tokens
    - `L*` is the language of words that can be scanned 
    - Possible to scan a string `w` if and only if `w` ∈ `L*`

!["idk what she was talking about"][page6]

- Question: is scanning output always unique? 
    - consider L = {aa, aaa} and w = {aaaaa}
    - do we output {aaa, aa} or {aa, aaa} ?
    - ε-NFA:

!["i think i was checking tinder when she was talking about this"][page7]

    - There is no way of knowing that the user intent was,
        - Scanning output is not unique 
        - maximal munch choose {aaa, aa} to ensure unique tokenization 
        - but no necessarily the intended one 
        - E.g. if w = {aaaa}
            - then the output with maximal munch is {aaa} follow by an error 
- It gives unique solution 
- but it cannot always find valid solution even if it exists 


### Scanning - Extended Maximal Munch
- *Input*: string `w`, language `L` (specifies set of valid tokens)
- *Output*: sequence of words, `w1`, `w2`, ..., `wn` such that:
    - `w` = `w1w2w2` ... `wn`
    - `wi` ∈ `L` for all `i` 
    - For each `i`, `wi` is the longest prefix of `wi`, `wi+1` ... `wn` that is in `L`
    - or ERROR 
- Use DFA for `L` to recongnize `w`; at the first state where there is no possible transition out (i.e. it gets stuck attempting to recongnize `w`)
    1. if in an *non-accepting state* back track to last-visited accepting state 
        1. if *NO accepting state*s have been visited, ERROR - there is no token
        2. Otherwise, output token of that accepting state and *RESET DFA* to start state 
    2. If in an accepting state, output token of that accepting state and *RESET DFA* to start state 
- However extended maximal munch is also not perfect 

- Example 
``` C 
int i = 4, j = 5;
i +++ j;
// is this: (i++) + j = 9 or i + (++j) = 10 ? 
// maxiaml munch returns the first result 

i +++++ j;
// we know this should be (i++) + (++j)
// maximal much tokenizes as {++, ++, +} which prodcues an ERROR in C 
```

``` C++
vector<vector<int>> v; // (pre-C++11)
//               ^~
//               error in C++11
// maximal much scans as {vector, <, vector, <, int, >>}
```


[page1]: https://imagehosting-50cd6.firebaseapp.com/IMG_1166.JPG "page 1"
[page2]: https://imagehosting-50cd6.firebaseapp.com/IMG_1167.JPG "page 2"
[page3]: https://imagehosting-50cd6.firebaseapp.com/IMG_1168.JPG "page 3"
[page4]: https://imagehosting-50cd6.firebaseapp.com/IMG_1173.JPG "page 4"
[page5]: https://imagehosting-50cd6.firebaseapp.com/IMG_1174.JPG "page 5"
[page6]: https://imagehosting-50cd6.firebaseapp.com/IMG_1175.JPG "page 6"
[page7]: https://imagehosting-50cd6.firebaseapp.com/IMG_1176.JPG "page 7"








