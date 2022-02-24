/* Declaraciones */
%{ 
    #include "Calculadora.tab.h"
    #include <stdio.h>
    #include <stdlib.h>
%}
%option noyywrap

espacios [ \t]+
digito [0-9]

%%

{espacios}      { /* se ignoran */}
"\n"            { return(FIN); } 
{digito}        { 
                    yylval.dval=atoi(yytext);
                    return(NUM)
                }
"+"             { return(MAS);}
"*"             { return(POR);}
"("             { return(PA);}
")"             { return(PC);}

%%