/*
    Homework 8: Prolog Basic

    Gabriel Schlam
    Programming Languages
*/

%%% in_list
% Base case
in_list([X|_], X).

% Recursion
in_list([_|T], X) :-
    in_list(T, X).

%%% element_at
% Base case. Position 0
element_at(0, [H|_], H).

% Recursion for positive case
element_at(N, [_|T], X) :-
    N > 0, 
    N1 is N-1,
    element_at(N1, T, X).

% Recursion for negative case
element_at(N, [H|T], X) :-
    N < 0, 
    reverse([H|T], L),
    N1 is (-1*N)-1,
    element_at(N1, L, X).

%%% range
% Base case for positive case, going from lower to higher
range(X, Y, Z, []) :-
    Z > 0,
    X >= Y.

% Base case for negative case, going from higher to lower
range(X, Y, Z, []) :-
    Z < 0,
    X =< Y.

% Recursion for positive case, going from lower to higher
range(X, Y, Z, [X|T]) :-
    Z > 0,
    X < Y,
    X1 is X + Z,
    range(X1, Y, Z, T).

% Recursion for negative case, going from higher to lower
range(X, Y, Z, [X|T]) :-
    Z < 0,
    X > Y,
    X1 is X + Z,
    range(X1, Y, Z, T).

%%% remove_doubles
% Base case. Having one element equal in lists
remove_doubles([X], [X]).

% Recursion when first two elements are equal
remove_doubles([H|[H|T]], [H|R]) :-
    remove_doubles([H|T], [H|R]).

% Recursion when first two elements are different
remove_doubles([H|[H1|T]], [H|R]) :-
    H \= H1,
    remove_doubles([H1|T], R).

