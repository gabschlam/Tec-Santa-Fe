 /*
    Modification of a simple grammar for NLP

    Gabriel Schlam
    Programming Languages
 */

% Vocabulary
article(mas, L0, L) :- terminal(el, L0, L).
article(fem, L0, L) :- terminal(la, L0, L).
noun(fem, L0, L) :- terminal(manzana, L0, L).
noun(fem, L0, L) :- terminal(zanahoria, L0, L).
noun(mas, L0, L) :- terminal(caballo, L0, L).
noun(mas, L0, L) :- terminal(gallo, L0, L).
verb(L0, L) :- terminal(come, L0, L).

% Rules for the word to use
terminal(Word, [Word|List], List).

% Grammar rules
sentence(L0, L) :-
    subject(L0, L1),
    predicate(L1, L).

subject(L0, L) :-
    article(G, L0, L1),
    noun(G, L1, L).

predicate(L0, L) :-
    verb(L0, L).

predicate(L0, L) :-
    verb(L0, L1),
    subject(L1, L).