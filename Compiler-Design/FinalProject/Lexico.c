/*
    Equipo 1
    Gabriel Schlam - A01024122
    Alejandra Nissan - A01024682
    Carlos García - A01025948
    David Benjamín Ruíz - A01020825
*/
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<regex.h>
#include<math.h>

#define MAX 100
#define MAX_ARRAY 100

struct Token {
    int numToken;
    char lexema[MAX];
    int nL;
};

typedef struct Token sT;

sT tokDev;
int tokenFlag=0;

FILE *fp;
int indBuf, n;
char fname[MAX];
char buffer[MAX];
int bufVacio=1;
int numLinea=0;
int newLine = 0;
int lastLine = 0;

char reservadas[21][MAX]= {"lijlol","leagdir","misparShalem", "to", "buliani", "tsaf","mibne","im","ajer","bazman","kara","adpes","adpesnl","lajazor","batel","najon","shequer","sof", "globali", "eseder", "functsia"};
char tokens[43][MAX]= {"ID","PYC","VALOR_ENT","VALOR_CHAR","VALOR_REAL","VALOR_STRING", "STRING_FILE", "STRING_LIBRARY", "MODIFICADOR","OPAR", "OPREL", "OPLOG", "ASIG", "PARENTESIS_A", "PARENTESIS_B", "LLAVE_A", "LLAVE_B", "CORCHETE_A", "CORCHETE_B", "INCLUDE", "DEFINE", "INT", "CHAR", "BOOLTYPE", "FLOAT", "BOOL", "ESTRUCTURA", "SI", "SI_NO", "CICLO", "LEER", "ESCRIBE", "ESCRIBENL", "RETURN", "VOID", "FIN", "GATO", "ERROR", "COMA", "VARIABLE_GLOBAL", "ARRAY", "FUNCION", "PUNTO"};
char lexema[30];
int ind=0;
int numRes=21;
int numErr=0;

int esReservada(char palabra[MAX]) {
    int i=0, res=-1;
    while(i<numRes) {
        //printf("palabra reservada: %s\n", palabra);
        if(strcmp(palabra,reservadas[i])==0){
            res=i;
            break;
        }
        else i++;
    }
    return res;
}

int match(const char *string, const char *pattern) {
    regex_t re;
    if (regcomp(&re, pattern, REG_EXTENDED|REG_NOSUB) != 0) return 0;
    int status = regexec(&re, string, 0, NULL, 0);
    regfree(&re);
    if (status != 0) return 0;
    return 1;
}

void imprimeToken(int num){
    printf("Token: %s Lexema: %s\n",tokens[num],lexema);
}

sT dameToken(void) {
    if(tokenFlag){
        tokenFlag=0;
        return tokDev;
    }
    sT tok;
    tok.numToken=-1;
    if(bufVacio) {
        numLinea++;
        fgets(buffer,MAX,fp);
        n=strlen(buffer);
        indBuf=0;
        bufVacio=0;
    }
    int edo=1;
    ind=0;

    while(indBuf<n && tok.numToken==-1) {
        switch (edo) {
        case 1:
            if (isalpha(buffer[indBuf])){
                edo = 7;
                lexema[ind++]=buffer[indBuf++];
            }
            else {
                if (isdigit(buffer[indBuf])) {
                    edo = 8;
                    lexema[ind++]=buffer[indBuf++];
                }
                else if (buffer[indBuf]=='\'') {
                    edo = 12;
                    lexema[ind++]=buffer[indBuf++];
                }
                else if (buffer[indBuf] == '"'){
                    edo = 14;
                    lexema[ind++]=buffer[indBuf++];
                }
                else if (buffer[indBuf] == ';'){
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 1;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf] == ','){
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 38;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf] == '.') {
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 42;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]=='<') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 18;
                }
                else if (buffer[indBuf]=='>') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 2;
                }
                else if (buffer[indBuf]=='='){
                    lexema[ind++] = buffer[indBuf++];
                    edo = 3;
                }
                else if (buffer[indBuf]=='+') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 4;
                }
                else if (buffer[indBuf]=='-') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 5;
                }
                else if (buffer[indBuf]=='*') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 6;
                }
                else if (buffer[indBuf]=='/') {
                    // se encontro OPAR sencillo
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 9;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]=='!') {
                    lexema[ind++]=buffer[indBuf++];
                    edo = 23;
                }
                else if (buffer[indBuf]=='&' || buffer[indBuf]=='|') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 17;
                }
                else if (buffer[indBuf]=='%') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 16;
                }
                else if (buffer[indBuf]=='(') {
                    // se encontro (
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 13;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]==')') {
                    // se encontro (
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 14;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]=='{') {
                    // se encontro {
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 15;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]=='}') {
                    // se encontro }
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 16;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]=='[') {
                    // se encontro [
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 17;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]==']') {
                    // se encontro ]
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 18;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                else if (buffer[indBuf]=='#') {
                    // se encontro #
                    lexema[ind++]=buffer[indBuf++];
                    lexema[ind]='\0';
                    tok.numToken = 36;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
                // Si es espacio, no hacer nada (ya que no es ERR)
                else if (buffer[indBuf]==' '){
                    indBuf++;
                }
                else if(buffer[indBuf]=='\n'){
                    fgets(buffer,MAX,fp);
                    numLinea++;
                    n=strlen(buffer);
                    indBuf=0;
                    bufVacio=0;
                    newLine=0;
                }
                else{
                    edo = 11;
                }
            }
            break;
        case 2:
            if (buffer[indBuf]=='=') {
                // OPREL con =
                lexema[ind++] = buffer[indBuf++];
            }
            lexema[ind]='\0';
            tok.numToken = 10;
            strcpy(tok.lexema,lexema);
            edo=1;
            ind=0;
            break;
        case 3:
            if (buffer[indBuf]=='=') {
                lexema[ind++] = buffer[indBuf++];
                // se encontro OPREL (==)
                lexema[ind]='\0';
                tok.numToken = 10;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // se encontro ASIG
                lexema[ind]='\0';
                tok.numToken = 12;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            break;
        case 4:
            if (buffer[indBuf]=='+') {
                // OPAR con doble signo
                lexema[ind++] = buffer[indBuf++];
            }
            // se encontro OPAR
            lexema[ind]='\0';
            tok.numToken = 9;
            strcpy(tok.lexema,lexema);
            edo=1;
            ind=0;
            break;
        case 5:
            if (buffer[indBuf]=='-') {
                // OPAR con doble signo
                lexema[ind++] = buffer[indBuf++];
            }
            // se encontro OPAR
            lexema[ind]='\0';
            tok.numToken = 9;
            strcpy(tok.lexema,lexema);
            edo=1;
            ind=0;
            break;
        case 6:
            if (buffer[indBuf]=='*') {
                // OPAR con doble signo
                lexema[ind++] = buffer[indBuf++];
            }
            // se encontro OPAR
            lexema[ind]='\0';
            tok.numToken = 9;
            strcpy(tok.lexema,lexema);
            edo=1;
            ind=0;
            break;
        case 7:
            if (isalpha(buffer[indBuf]) || isdigit(buffer[indBuf])) {
                lexema[ind++] = buffer[indBuf++];
            }
            else {    
                // se encontro ID
                lexema[ind]='\0';
                int res;
                res = esReservada(lexema);//revisamos si es ID o palabra reservada
                if(res!=-1) {   //es una palabra reservada
                    switch (res){
                    case 0:
                        // ENCUENTRA lijlol - TOKEN: INCLUDE
                        tok.numToken = 19;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 1:
                        // ENCUENTRA leagdir - TOKEN: DEFINE
                        tok.numToken = 20;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 2:
                        // ENCUENTRA misparShalem - TOKEN: INT
                        tok.numToken = 21;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 3:
                        // ENCUENTRA to - TOKEN: CHAR
                        tok.numToken = 22;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 4:
                        // ENCUENTRA buliani - TOKEN: BOOLTYPE
                        tok.numToken = 23;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 5:
                        // ENCUENTRA tsaf - TOKEN: FLOAT
                        tok.numToken = 24;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 6:
                        // ENCUENTRA mibne - TOKEN: ESTRUCTURA
                        tok.numToken = 26;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 7:
                        // ENCUENTRA im - TOKEN: SI
                        tok.numToken = 27;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 8:
                        // ENCUENTRA ajer - TOKEN: SI_NO
                        tok.numToken = 28;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 9:
                        // ENCUENTRA bazman - TOKEN: CICLO
                        tok.numToken = 29;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 10:
                        // ENCUENTRA kara - TOKEN: LEER
                        tok.numToken = 30;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 11:
                        // ENCUENTRA adpes - TOKEN: ESCRIBE
                        tok.numToken = 31;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 12:
                        // ENCUENTRA adpesnl - TOKEN: ESCRIBENL
                        tok.numToken = 32;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 13:
                        // ENCUENTRA lajazor - TOKEN: RETURN
                        tok.numToken = 33;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 14:
                        // ENCUENTRA batel - TOKEN: VOID
                        tok.numToken = 34;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 15:
                    case 16:
                        // ENCUENTRA najon / shequer - TOKEN: BOOL
                        tok.numToken = 25;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 17:
                        // ENCUENTRA sof - TOKEN: FIN
                        tok.numToken = 35;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 18:
                        // ENCUENTRA globali - TOKEN: VARIABLE_GLOBAL
                        tok.numToken = 39;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 19:
                        // ENCUENTRA eseder - TOKEN: ARRAY
                        tok.numToken = 40;
                        strcpy(tok.lexema,lexema);
                        break;
                    case 20:
                        // ENCUENTRA functsia - TOKEN: FUNCION
                        tok.numToken = 41;
                        strcpy(tok.lexema,lexema);
                        break;
                    }
                    
                }
                else  {  //es un ID
                    //imprimeToken(0);
                    tok.numToken = 0;
                    strcpy(tok.lexema,lexema);
                }
                edo=1;
                ind=0;
                break;
            }
            break;
        case 8:
            if (isdigit(buffer[indBuf])) {
                lexema[ind++] = buffer[indBuf++];
            }
            else {
                if (buffer[indBuf] == '.') {
                    lexema[ind++] = buffer[indBuf++];
                    edo = 9;
                }
                else{
                    // se encontro VALOR_ENT
                    lexema[ind]='\0';
                    tok.numToken = 2;
                    strcpy(tok.lexema,lexema);
                    edo=1;
                    ind=0;
                }
            }
            break;
        case 9:
            if (isdigit(buffer[indBuf])) {
                lexema[ind++] = buffer[indBuf++];
                edo = 10;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 10:
            if (isdigit(buffer[indBuf])) {
                lexema[ind++] = buffer[indBuf++];
            }
            else {    
                // se encontro VALOR_REAL
                lexema[ind]='\0';
                tok.numToken = 4;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            break;
        case 11:
            printf("error léxico\n");
            tok.numToken = 37;
            lexema[0]=buffer[indBuf];
            lexema[1]='\0';
            strcpy(tok.lexema,lexema);
            break;
        case 12:
            if (match(&buffer[indBuf], "[^']+")) {
                lexema[ind++] = buffer[indBuf++];
                edo = 13;
            }
            else if (buffer[indBuf]=='\'') {  
                lexema[ind++] = buffer[indBuf++];
                // se encontro VALOR_CHAR (vacio)
                lexema[ind]='\0';
                tok.numToken = 3;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 13:
            if (buffer[indBuf]=='\'') {
                // se encontro VALOR_CHAR
                lexema[ind++] = buffer[indBuf++];
                lexema[ind]='\0';
                tok.numToken = 3;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 14:
            if (isalpha(buffer[indBuf]) || isdigit(buffer[indBuf]) || buffer[indBuf] == ' ' || buffer[indBuf] == ':') {
                lexema[ind++] = buffer[indBuf++];
            }
            else if (buffer[indBuf] == '.') {
                lexema[ind++] = buffer[indBuf++];
                edo = 15;
            }
            else if (buffer[indBuf]=='%') {
                lexema[ind++] = buffer[indBuf++];
                edo = 16;
            }
            else if (buffer[indBuf] == '"'){    
                // se encontro VALOR_STRING
                lexema[ind++] = buffer[indBuf++];
                lexema[ind]='\0';
                tok.numToken = 5;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 15:
            if (buffer[indBuf] == 'c'){
                lexema[ind++] = buffer[indBuf++];
                edo = 20;
            }
            else if (buffer[indBuf] == '"'){    
                // se encontro VALOR_STRING
                lexema[ind]='\0';
                tok.numToken = 5;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 16:
            if (buffer[indBuf] == 'c' || buffer[indBuf] == 'd' || buffer[indBuf] == 'f' || buffer[indBuf] == 's'){
                lexema[ind++] = buffer[indBuf++];
                edo = 22;
            }
            else {
                lexema[ind++] = buffer[indBuf++];
                // se encontro OPAR sencillo
                lexema[ind]='\0';
                tok.numToken = 9;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            break;
        case 17:
            if (buffer[indBuf]=='&' || buffer[indBuf]=='|') {
                lexema[ind++] = buffer[indBuf++];
                // se encontro OPLOG
                lexema[ind]='\0';
                tok.numToken = 11;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 18:
            if (isalpha(buffer[indBuf]) || isdigit(buffer[indBuf])) {
                lexema[ind++] = buffer[indBuf++];
            }
            else if (buffer[indBuf] == '.') {
                lexema[ind++] = buffer[indBuf++];
                edo = 19;
            }
            else if (buffer[indBuf] == '=') {
                lexema[ind++] = buffer[indBuf++];
                // se encontro OPREL (==)
                lexema[ind]='\0';
                tok.numToken = 10;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                lexema[ind++] = buffer[indBuf++];
                // se encontro OPREL
                lexema[ind]='\0';
                tok.numToken = 10;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            break;
        case 19:
            if (buffer[indBuf] == 'h'){
                lexema[ind++] = buffer[indBuf++];
                edo = 21;
            }
            else {
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 20:
            if (buffer[indBuf] == '"'){
                lexema[ind++] = buffer[indBuf++];
                // se encontro STRING_FILE
                lexema[ind]='\0';
                tok.numToken = 6;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 21:
            if (buffer[indBuf] == '>'){
                lexema[ind++] = buffer[indBuf++];
                // se encontro STRING_LIBRARY
                lexema[ind]='\0';
                tok.numToken = 7;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 22:
            if (buffer[indBuf] == '"'){
                lexema[ind++] = buffer[indBuf++];
                // se encontro MODIFICADOR
                lexema[ind]='\0';
                tok.numToken = 8;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {    
                // Se encontro ERR
                lexema[ind++] = buffer[indBuf++];
                edo = 11;
            }
            break;
        case 23:
            if (buffer[indBuf] == '=') {
                // se encontro OPREL (!=)
                lexema[ind++] = buffer[indBuf++];
                lexema[ind]='\0';
                tok.numToken = 10;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            else {
                // se encontro OPLOG sencillo
                lexema[ind++]=buffer[indBuf++];
                lexema[ind]='\0';
                tok.numToken = 11;
                strcpy(tok.lexema,lexema);
                edo=1;
                ind=0;
            }
            
            break;
        }
    }
 
    tok.nL=numLinea;
    //printf("TOK: %d - NUM LINEA: %d\n", tok.numToken, numLinea);
    return tok;
}