/*
    Modification of a simple grammar for NLP
    Generating a parsing tree for the structure of the sentence

    Gabriel Schlam
    Programming Languages

    Equipo:
    Samantha Barco
    Christian Dalma
    Juan Francisco Gortarez
*/

% Vocabulary
article(art(el), singular, mas, L0, L) :- terminal(el, L0, L).
article(art(la), singular, fem, L0, L) :- terminal(la, L0, L).
article(art(los), plural, mas, L0, L) :- terminal(los, L0, L).
article(art(las), plural, fem, L0, L) :- terminal(las, L0, L).
noun(nou(manzana), singular, fem, L0, L) :- terminal(manzana, L0, L).
noun(nou(zanahoria), singular, fem, L0, L) :- terminal(zanahoria, L0, L).
noun(nou(caballo), singular, mas, L0, L) :- terminal(caballo, L0, L).
noun(nou(gallo), singular, mas, L0, L) :- terminal(gallo, L0, L).
noun(nou(manzanas), plural, fem, L0, L) :- terminal(manzanas, L0, L).
noun(nou(zanahorias), plural, fem, L0, L) :- terminal(zanahorias, L0, L).
noun(nou(caballos), plural, mas, L0, L) :- terminal(caballos, L0, L).
noun(nou(gallos), plural, mas, L0, L) :- terminal(gallos, L0, L).
verb(ver(come), singular, transitive, L0, L) :- terminal(come, L0, L).
verb(ver(comen), plural, transitive, L0, L) :- terminal(comen, L0, L).
verb(ver(camina), singular, intransitive, L0, L) :- terminal(camina, L0, L).
verb(ver(caminan), plural, intransitive, L0, L) :- terminal(caminan, L0, L).
adjective(adj(grande), singular, mas, L0, L) :- terminal(grande, L0, L).
adjective(adj(grandes), plural, mas, L0, L) :- terminal(grandes, L0, L).
adjective(adj(grande), singular, fem, L0, L) :- terminal(grande, L0, L).
adjective(adj(grandes), plural, fem, L0, L) :- terminal(grandes, L0, L).
adjective(adj(chico), singular, mas, L0, L) :- terminal(chico, L0, L).
adjective(adj(chicos), plural, mas, L0, L) :- terminal(chicos, L0, L).
adjective(adj(chica), singular, fem, L0, L) :- terminal(chica, L0, L).
adjective(adj(chicas), plural, fem, L0, L) :- terminal(chicas, L0, L).

% Rules for the word to use
terminal(Word, [Word|List], List).

% Grammar rules
sentence(sen(NounPart, VerbPart), L0, L) :-
    subject(NounPart, T, L0, L1),
    predicate(VerbPart, T, L1, L),
    % Check that the noun is not part of the VerbPart
    VerbPart \= vp(_, NounPart).

subject(np(Article, Noun), T, L0, L) :-
    article(Article, T, G, L0, L1),
    noun(Noun, T, G, L1, L).

subject(np(Article, Noun, Adjective), T, L0, L) :-
    article(Article, T, G, L0, L1),
    noun(Noun, T, G, L1, L2),
    adjective(Adjective, T, G, L2, L).

predicate(vp(Verb), _, L0, L) :-
    verb(Verb, _, _, L0, L).

predicate(vp(Verb, NounPart), T, L0, L) :-
    verb(Verb, T, transitive, L0, L1),
    subject(NounPart, _, L1, L).