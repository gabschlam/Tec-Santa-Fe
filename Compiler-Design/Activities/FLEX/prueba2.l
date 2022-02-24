/* Declaraciones */
%option noyywrap

/* Buscara la palabra amor y escribira odio */
ws [ ]
%%

{ws}amor{ws}    printf("odio");
{ws}amor        printf("odio");
amor{ws}        printf("odio");

%%

int main(void){
    yylex();
}