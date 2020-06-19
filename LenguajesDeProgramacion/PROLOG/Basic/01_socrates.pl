/*
    First example of Prolog

    Gabriel Schlam
    Programming Languages
*/

% Facts
man(socrates).
man(plato).

woman(sara).

% Rule
mortal(X) :- man(X). 

mortal(X) :- woman(X).