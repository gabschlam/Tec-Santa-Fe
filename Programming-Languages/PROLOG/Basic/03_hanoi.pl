/*
    Define a relation to solve the steps for Towers of Hanoi

    Gabriel Schlam
    Programming Languages
*/

% Base case
hanoi(1, Label, Origin, Destination, _) :-
    write("Move "),
    write(Label),
    write(" from "),
    write(Origin),
    write(" to "),
    write(Destination),
    nl.

% Recursive predicate
hanoi(Rings, _, Origin, Destination, Temp) :-
    Rings > 1,
    % Initialice variable
    N_1 is Rings - 1,
    hanoi(N_1, N_1, Origin, Temp, Destination),
    hanoi(1, Rings, Origin, Destination, Temp),
    hanoi(N_1, N_1, Temp, Destination, Origin),
    % Stop prolog from trying to find other solutions
    !.

% Call function with 4 arguments, for user
hanoi(Rings, Origin, Destination, Temp) :-
    hanoi(Rings, _, Origin, Destination, Temp).
