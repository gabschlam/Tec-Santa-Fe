/*
    Examples of mathematical function

    Gabriel Schlam
    Programming Languages
*/

square(X, Y) :-
    Y is X * X.
 
 quadratic(A, B, C, X1, X2) :-
    A \= 0,
    D is B * B - 4 * A * C,
    D > 0,
    X1 is (-B + sqrt(D)) / (2 * A),
    X2 is (-B - sqrt(D)) / (2 * A).
 
 quadratic(0, B, C, X1, X2) :-
    X1 is -C / B,
    X2 is -C / B.

/*
% Base case fact
factorial(0, 1).

%Recursive rule
factorial(N, R) :-
    N > 0,
    N1 is N - 1,
    factorial(N1, R1),
    R is R1 * N.
*/

factorial(N, R) :-
    factorial(N, 1, R).
% Tail recursion for factorial
% Base case fact
factorial(0, A, A).
%Recursive rule
factorial(N, A, R) :-
    N > 0,
    A1 is A * N,
    N1 is N - 1,
    factorial(N1, A1, R).