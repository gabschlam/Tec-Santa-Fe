/* Declaraciones */
%{ #include<math.h>
int words = 0;
int wordsLine = 0;
int line = 0;
%}
%option noyywrap

letra [a-zA-Z]
esp [\t ]

%%

{letra}+ { words++;wordsLine++;}
{esp}
"\n" {printf("Numero de palabras en linea %d: %d\n", ++line, wordsLine); wordsLine=0;}

%%

int main(void){
    yylex();
    printf("Numero de palabras en linea %d: %d\n", ++line, wordsLine);
    printf("Numero de lineas en archivo: %d\n", line);
    printf("Numero de palabras totales: %d\n", words);

    return 0;
}