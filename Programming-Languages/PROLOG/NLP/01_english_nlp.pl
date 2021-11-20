 /*
    Basic case of Natural Language Processing (NLP)
    Using simple sentences

    Gabriel Schlam
    Programming Languages
 */

% Vocabulary
article([the]).
article([a]).
noun([student]).
noun([car]).
noun([students]).
noun([homework]).
verb([programs]).
verb([drives]).

% Grammar rules
sentence(L) :-
    subject(S),
    predicate(P),
    append(S, P, L).

subject(L) :-
    article(A),
    noun(N),
    append(A, N, L).

predicate(L) :-
    verb(L).

predicate(L) :-
    verb(V),
    subject(S),
    append(V, S, L).