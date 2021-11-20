/*
    Modification of a simple grammar for NLP
    Generating a parsing tree for the structure of the sentence

    Gabriel Schlam
    Programming Languages
*/

% Vocabulary
article(art(el), mas, L0, L) :- terminal(el, L0, L).
article(art(la), fem, L0, L) :- terminal(la, L0, L).
noun(nou(manzana), fem, L0, L) :- terminal(manzana, L0, L).
noun(nou(zanahoria), fem, L0, L) :- terminal(zanahoria, L0, L).
noun(nou(caballo), mas, L0, L) :- terminal(caballo, L0, L).
noun(nou(gallo), mas, L0, L) :- terminal(gallo, L0, L).
verb(ver(come), transitive, L0, L) :- terminal(come, L0, L).
verb(ver(camina), intransitive, L0, L) :- terminal(camina, L0, L).

% Rules for the word to use
terminal(Word, [Word|List], List).

% Grammar rules
sentence(sen(NounPart, VerbPart), L0, L) :-
    subject(NounPart, L0, L1),
    predicate(VerbPart, L1, L),
    % Check that the noun is not part of the VerbPart
    VerbPart \= vp(_, NounPart).

subject(np(Article, Noun), L0, L) :-
    article(Article, G, L0, L1),
    noun(Noun, G, L1, L).

predicate(vp(Verb), L0, L) :-
    verb(Verb, _, L0, L).

predicate(vp(Verb, NounPart), L0, L) :-
    verb(Verb, transitive, L0, L1),
    subject(NounPart, L1, L).