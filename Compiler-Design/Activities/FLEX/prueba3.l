/* Declaraciones */
%{ #include<math.h>
%}
%option noyywrap

letra [a-zA-Z]
digito [0-9]
id {letra}{letra}*

%%

if          printf("res if\n");
for         printf("res for\n");
{id}        printf("ID Lexema: %s\n", yytext);
{digito}    printf("Digito Lexema: %s Valor: %i\n", yytext, atoi(yytext));
{digito}+   printf("Numero Lexema: %s Valor: %i\n", yytext, atoi(yytext));
.           ECHO;

%%

int main(void){
    yylex();
}