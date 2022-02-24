/* Declaraciones */
%{ 
    #include <stdio.h>
    int yylex();
    void yyerror(char *s);
    int yyparse();
%}

%union{ int dval;}

%token <dval> NUM
%token MAS POR PA PC FIN
%left MAS
%left POR
%type <dval> Expr Term Factor
%start Line

%%
Line:   Expr FIN       { printf("Result: %i\n",$1); }
        ;

Expr:   Expr MAS Term    { $$=$1+$3; }
        | Term
        ;

Term:
        Term POR Factor  { $$=$1*$3; }
        | Factor
        ;

Factor: PA Expr PC       { $$=$2; }
        | NUM            { $$=$1; }
        ;
%%

void yyerror(char *s) {
  printf("%s\n",s);
}

int main(void) {
  yyparse();
}