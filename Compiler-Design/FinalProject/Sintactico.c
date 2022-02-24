/*
    Equipo 1
    Gabriel Schlam - A01024122
    Alejandra Nissan - A01024682
    Carlos García - A01025948
    David Benjamín Ruíz - A01020825
*/
#include "Lexico.c"

/*
    S -> DIRECTIVAS VARIABLES_GLOBALES FUNCIONES

    DIRECTIVAS -> DIRECTIVA DIRECTIVAS | sof
    DIRECTIVA -> DEFINES | INCLUDES 
    INCLUDES -> #lijlol <string_library> | #lijlol “string_file”
    DEFINES -> #leagdir id VALOR
    VARIABLES_GLOBALES -> VARIABLE_GLOBAL
    FUNCIONES -> FUNCION | FUNCION FUNCIONES 

    ** Gramatica completa se encuentra en documentacion adjunta
*/

/* 
    SYMBOL TYPES:
    0: STRUCT
    1: FUNC_INT
    2: FUNC_CHAR
    3: FUNC_BOOL
    4: FUNC_FLOAT
    5: INT (misparShalem)
    6: CHAR (to)
    7: BOOLTYPE	(buliani)
    8: FLOAT (tsaf)
    9: STRING
    10: DEFINE_INT
    11: DEFINE_CHAR
    12: DEFINE_BOOL
    13: DEFINE_FLOAT
    14: DEFINE_STRING
    15: array_INT
    16: array_CHAR
    17: array_BOOL
    18: array_FLOAT
*/

struct Estructura {
    int type;
    char id[MAX];
    char value[MAX];
    char func[MAX];
};

typedef struct Estructura Estructura;

struct Symbol {
    int type;
    char id[MAX];
    char value[MAX];
    char func[MAX];
    char params[MAX];
    int numElementsStruct;
    struct Estructura elementsStruct[MAX];
    int indicesArray[MAX];
    char elementsArray[MAX_ARRAY][MAX];
    int numIndicesArray;
    int numElementsArray;
};

typedef struct Symbol Symbol;

int numElementsSymbols = 0;

Symbol tablaSimbolos[MAX];

int numSymbolsFunc = 0;

Symbol tablaSimbolosFunc[MAX];

char currentFunc[MAX];
char currentParams[MAX];
char result[MAX];
int remainingDimensions = 0;

void lista_instrucciones();
void llamada_funcion(sT tokenRecibido);

void funcion();

void errmsg(int x, int nL){
    numErr++;
    printf("Error en linea %i: ",nL);
    switch(x){
        case 1: 
            printf("se esperaba el caracter # para la directiva o sof"); 
            break;
        case 2: 
            printf("se esperaba un tipo de directiva: lijlol o leagdir"); 
            break;
        case 3: 
            printf("se esperaba un string file o string library"); 
            break;
        case 4: 
            printf("se esperaba un id para el DEFINE"); 
            break;
        case 5: 
            printf("se esperaba un valor para el DEFINE"); 
            break;
        case 6: 
            printf("se esperaba un tipo para la función"); 
            break;
        case 7: 
            printf("se esperaba un id para la función"); 
            break;
        case 8: 
            printf("falta ;"); 
            break;
        case 9: 
            printf("falta ("); 
            break;
        case 10: 
            printf("falta )"); 
            break;
        case 11: 
            printf("se esperaba un tipo para el parámetro de la función"); 
            break;
        case 12: 
            printf("se esperaba un id para el parametro de la función"); 
            break;
        case 13: 
            printf("falta {"); 
            break;
        case 14: 
            printf("falta }"); 
            break;
        case 15: 
            printf("se esperaba un sof o palabra reservada globali"); 
            break;
        case 16: 
            printf("se esperaba un id para la declaración"); 
            break;
        case 17: 
            printf("se esperaba un valor int"); 
            break;
        case 18: 
            printf("se esperaba un valor char"); 
            break;
        case 19: 
            printf("se esperaba un valor real"); 
            break;
        case 20: 
            printf("se esperaba un valor booleano"); 
            break;
        case 21: 
            printf("se esperaba un tipo para la declaración"); 
            break;
        case 22: 
            printf("no existe id"); 
            break;
        case 23: 
            printf("ya existe variable con ese id"); 
            break;
        case 24: 
            printf("falta = para la asignación"); 
            break;
        case 25: 
            printf("se esperaba valor string o modificador"); 
            break;
        case 26: 
            printf("falta ,"); 
            break;
        case 27: 
            printf("se esperaba valor string"); 
            break;
        case 28: 
            printf("se esperaba un id"); 
            break;
        case 29: 
            printf("la comparación está mal formada"); 
            break;
        case 30: 
            printf("se esperaba un operador relacional"); 
            break;
        case 31: 
            printf("se esperaba un operador aritmético"); 
            break;
        case 32: 
            printf("la operación no se puede realizar por tipos diferentes"); 
            break;
        case 33: 
            printf("la comparación no se puede realizar por tipos diferentes"); 
            break;
        case 34: 
            printf("falta palabra reservada functsia"); 
            break;
        case 35: 
            printf("no es posible reasignar una constante define"); 
            break;
        case 36: 
            printf("el parametro a llamar es de otro tipo al esperado"); 
            break;
        case 37: 
            printf("hace falta la palabra reservada lajazor"); 
            break;
        case 38: 
            printf("hace falta el valor de retorno"); 
            break;
        case 39: 
            printf("el tipo de valor de retorno no es igual al tipo de la función"); 
            break;
        case 40: 
            printf("se esperaba un tipo de valor válido para la variable dentro de la estructura"); 
            break;
        case 41: 
            printf("se esperaba un id para la variable dentro de la estructura"); 
            break;
        case 42: 
            printf("se encontró un ; de más"); 
            break;
        case 43: 
            printf("se esperaba un . para la asignación del valor de la estructura"); 
            break;
        case 44: 
            printf("no existe id dentro de la estructura"); 
            break;
        case 45:
            printf("la dimensión debe ser de tipo numérico");
            break;
        case 46: 
            printf("falta ["); 
            break;
        case 47: 
            printf("falta ]"); 
            break;
        case 48: 
            printf("el indice a asignar debe de ser menor a la dimensión declarada"); 
            break;
        case 49: 
            printf("hay parámetros de menos a los esperados"); 
            break;
        case 50: 
            printf("se esperaba un valor o id para la llamada de función"); 
            break;
        case 51: 
            printf("hace falta %d dimension(es) para la asignación del arreglo", remainingDimensions); 
            break;
        case 52: 
            printf("se esperaba un valor para la dimensión"); 
            break;
    }
    printf("\n");
    exit(1);
}

int checkValueTypeInTable(sT tok) {
    int existe = -1;
    for (int i = 0; i < numSymbolsFunc; i++) {
        if((strcmp(tablaSimbolosFunc[i].id, tok.lexema)==0) && (strcmp(tablaSimbolosFunc[i].func, currentFunc)==0)){
            existe = tablaSimbolosFunc[i].type;
            return existe; 
        }
    }
        
    if (existe == -1){
        for (int i = 0; i < numElementsSymbols; i++) {
            if(strcmp(tablaSimbolos[i].id, tok.lexema)==0){
                existe = tablaSimbolos[i].type;
                return existe; 
            }
        }
    }
    return existe;       
}

struct Symbol getSymbolArray(sT tok) {
    int numIndexes = -1;
    Symbol newSymbol = {}; 
    for (int i = 0; i < numSymbolsFunc; i++) {
        if((strcmp(tablaSimbolosFunc[i].id, tok.lexema)==0) && (strcmp(tablaSimbolosFunc[i].func, currentFunc)==0)){
            numIndexes = tablaSimbolosFunc[i].numIndicesArray;
            return tablaSimbolosFunc[i]; 
        }
    }
        
    if (numIndexes == -1){
        for (int i = 0; i < numElementsSymbols; i++) {
            if(strcmp(tablaSimbolos[i].id, tok.lexema)==0){
                numIndexes = tablaSimbolos[i].numIndicesArray;
                return tablaSimbolosFunc[i]; 
            }
        }
    }
    return newSymbol;       
}

int checkIdOfStruct(sT tok, char id[MAX]) {
    int existe = -1;
    for (int i = 0; i < numSymbolsFunc; i++) {
        if((strcmp(tablaSimbolosFunc[i].id, tok.lexema)==0) && (strcmp(tablaSimbolosFunc[i].func, currentFunc)==0)){
            for (int j = 0; j < tablaSimbolosFunc[i].numElementsStruct; j++)
            {
                if(strcmp(tablaSimbolosFunc[i].elementsStruct[j].id, id)==0){
                    existe = tablaSimbolosFunc[i].elementsStruct[j].type;
                    return existe; 
                }
            }
        }
    }
        
    if (existe == -1){
        for (int i = 0; i < numElementsSymbols; i++) {
            if(strcmp(tablaSimbolos[i].id, tok.lexema)==0){
                for (int j = 0; j < tablaSimbolos[i].numElementsStruct; j++)
                {
                    if(strcmp(tablaSimbolos[i].elementsStruct[j].id, id)==0){
                        existe = tablaSimbolos[i].elementsStruct[j].type;
                        return existe; 
                    }
                } 
            }
        }
    }
    return existe;       
}

void getParams(sT tok) {
    for (int i = 0; i < numElementsSymbols; i++) {
        if(strcmp(tablaSimbolos[i].id, tok.lexema)==0){
            strcpy(currentParams, tablaSimbolos[i].params);
        }
    }
}

void assignValueInTable(sT tok, char value[MAX]) {
    int existe = -1;
    for (int i = 0; i < numSymbolsFunc; i++) {
        if((strcmp(tablaSimbolosFunc[i].id, tok.lexema)==0) && (strcmp(tablaSimbolosFunc[i].func, currentFunc)==0)){
            strcpy(tablaSimbolosFunc[i].value,value);

        }
    }
        
    if (existe == -1){
        for (int i = 0; i < numElementsSymbols; i++) {
            if(strcmp(tablaSimbolos[i].id, tok.lexema)==0){
                strcpy(tablaSimbolos[i].value,value);
            }
        }
    }
}

void assignValueInArray(sT tok, char value[MAX], int position) {
    int existe = -1;
    for (int i = 0; i < numSymbolsFunc; i++) {
        if((strcmp(tablaSimbolosFunc[i].id, tok.lexema)==0) && (strcmp(tablaSimbolosFunc[i].func, currentFunc)==0)){
            strcpy(tablaSimbolosFunc[i].elementsArray[position],value);

        }
    }
        
    if (existe == -1){
        for (int i = 0; i < numElementsSymbols; i++) {
            if(strcmp(tablaSimbolos[i].id, tok.lexema)==0){
                strcpy(tablaSimbolos[i].elementsArray[position],value);
            }
        }
    }
}


void assignValueInTabStruct(sT tok, char id[MAX], char value[MAX]) {
    int existe = -1;
    for (int i = 0; i < numSymbolsFunc; i++) {
        if((strcmp(tablaSimbolosFunc[i].id, tok.lexema)==0) && (strcmp(tablaSimbolosFunc[i].func, currentFunc)==0)){
            for (int j = 0; j < tablaSimbolosFunc[i].numElementsStruct; j++)
            {
                if(strcmp(tablaSimbolosFunc[i].elementsStruct[j].id, id)==0){
                    strcpy(tablaSimbolosFunc[i].elementsStruct[j].value,value);
                }
            }

        }
    }
        
    if (existe == -1){
        for (int i = 0; i < numElementsSymbols; i++) {
            for (int j = 0; j < tablaSimbolos[i].numElementsStruct; j++)
            {
                if(strcmp(tablaSimbolos[i].elementsStruct[j].id, id)==0){
                    strcpy(tablaSimbolos[i].elementsStruct[j].value,value);
                }
            }
        }
    }
}


void includes(void){
    sT tok;
    tok=dameToken();

    // SI TOKEN ES STRING_FILE O STRING_LIBRARY
    if(tok.numToken==6 || tok.numToken==7) {
    }
    else {
        errmsg(3, tok.nL);
    }
    
}

void defines(void){
    sT tok;
    tok=dameToken();

    // SI EL TOKEN ES ID
    if(tok.numToken==0) {
        // Nuevo symbol
        Symbol newSymbol = {};
        // Asignar lexema al symbol
        strcpy(newSymbol.id,tok.lexema);
        // ingresar symbol al tabla de simbolos
        tablaSimbolos[numElementsSymbols] = newSymbol;

        tok=dameToken();
        // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, STRING, BOOL)
        if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==5 || tok.numToken==25) {
            // ASIGNAR VALOR A LEXEMA EN TABLA DE SIMBOLOS
            // Asignar type como define
            switch (tok.numToken)
            {
            case 2:
                tablaSimbolos[numElementsSymbols].type = 10;
                break;
            case 3:
                tablaSimbolos[numElementsSymbols].type = 11;
                break;
            case 4:
                tablaSimbolos[numElementsSymbols].type = 13;
                break;
            case 5:
                tablaSimbolos[numElementsSymbols].type = 14;
                break;
            case 25:
                tablaSimbolos[numElementsSymbols].type = 12;
                break;
            }
            
            strcpy(tablaSimbolos[numElementsSymbols].value,tok.lexema);
            numElementsSymbols++;
        }
        else {
            errmsg(5, tok.nL);
        }
    }
    else {
        errmsg(4, tok.nL);
    }
    
}

void directiva(sT tokRecibido){
    sT tok;
    // SI TOK_RECIBIDO ES GATO (#)
    if (tokRecibido.numToken == 36) {
        tok=dameToken();
    
        // SI TOK ES INCLUDE (lijlol)
        if (tok.numToken == 19){
            includes();
        }
        // SI TOK ES DEFINE (leagdir)
        else if (tok.numToken == 20){
            defines();
        }
        else {
            errmsg(2, tok.nL);  
        }
        
    }
    else {
        errmsg(1, tokRecibido.nL);
    }
    
}

void directivas(void){
    sT tok;
    tok=dameToken();
    // MIENTRAS TOK SEA DIFERENTE DE sof
    while (tok.numToken!=35) {
        directiva(tok);
        tok=dameToken();
    }
    tok=dameToken();

    if (tok.numToken!= 1) {
        errmsg(8, tok.nL);
    }  
    
}

sT declaracion_estructura_global(void){
    sT tok;
    Symbol newSymbol = {};
    int numElements = 0;

    // STRUCT TYPE = 0
    newSymbol.type = 0;
    tok=dameToken();

    if (tok.numToken!=15)
    {
        errmsg(17, tok.nL);
    }

    else {
        int exit = 0;
        while (exit == 0)
        {
            tok = dameToken();
            if (tok.numToken==21 || tok.numToken==22 || tok.numToken==23 || tok.numToken==24) {
                newSymbol.elementsStruct[numElements].type = tok.numToken-16;

                tok = dameToken();
                if (tok.numToken==0)
                {
                    strcpy(newSymbol.elementsStruct[numElements].id, tok.lexema);
                    tok=dameToken();

                    if (tok.numToken==38)
                    {
                        //strcat(newSymbol.value, tok.lexema);
                        numElements++;
                    }
                    else if (tok.numToken==16)
                    {
                        numElements++;
                        exit = 1;
                    }
                    else {
                        errmsg(26, tok.nL);
                    }
                    
                }
                else {
                    errmsg(41, tok.nL);
                }
                
            }
            else {
                errmsg(40, tok.nL);
            }
            
        }

        tok = dameToken();
        if (tok.numToken!=0)
        {
            errmsg(16, tok.nL);
        }
        else{
            strcpy(newSymbol.id, tok.lexema);
        }        
        
    }

    tok=dameToken();

    newSymbol.numElementsStruct = numElements;

    tablaSimbolos[numElementsSymbols] = newSymbol;
    numElementsSymbols++;

    return tok;
}

sT declaracion_estructura(void){
    sT tok;
    Symbol newSymbol = {};
    int numElements = 0;

    // STRUCT TYPE = 0
    newSymbol.type = 0;
    strcpy(newSymbol.func,currentFunc);
    tok=dameToken();

    if (tok.numToken!=15)
    {
        errmsg(17, tok.nL);
    }

    else {
        int exit = 0;
        while (exit == 0)
        {
            tok = dameToken();
            if (tok.numToken==21 || tok.numToken==22 || tok.numToken==23 || tok.numToken==24) {
                newSymbol.elementsStruct[numElements].type = tok.numToken-16;
                tok = dameToken();
                if (tok.numToken==0)
                {
                    strcpy(newSymbol.elementsStruct[numElements].id, tok.lexema);

                    tok=dameToken();

                    if (tok.numToken==38)
                    {
                        numElements++;
                    }
                    else if (tok.numToken==16)
                    {
                        numElements++;
                        exit = 1;
                    }
                    else {
                        errmsg(26, tok.nL);
                    }
                    
                }
                else {
                    errmsg(41, tok.nL);
                }
                
            }
            else {
                errmsg(40, tok.nL);
            }
            
        }

        tok = dameToken();
        if (tok.numToken!=0)
        {
            errmsg(16, tok.nL);
        }
        else{
            strcpy(newSymbol.id, tok.lexema);
        }        
        
    }

    tok=dameToken();

    newSymbol.numElementsStruct = numElements;

    tablaSimbolosFunc[numSymbolsFunc] = newSymbol;
    numSymbolsFunc++;

    return tok;
}

sT asignacion_estructura(sT tokenRecibido){
    sT tok;
    int valorType;
    int valorTypeID;
    char id[MAX];
    tok=dameToken();

    // SE BUSCA TOKEN PUNTO
    if (tok.numToken==42) {
        tok=dameToken();

        if (tok.numToken==0)
        {
            valorType = checkIdOfStruct(tokenRecibido, tok.lexema);
            if (valorType != -1)
            {
                strcpy(id, tok.lexema);
                tok=dameToken();

                if (tok.numToken==12)
                {
                    tok=dameToken();

                    if (tok.numToken==0)
                    {
                        valorTypeID = checkValueTypeInTable(tok);

                        if (valorTypeID != -1)
                        {
                            if (valorType != valorTypeID) {
                                errmsg(32, tok.nL);
                            }
                            else {
                                assignValueInTabStruct(tokenRecibido, id, tok.lexema);

                                tok=dameToken();
                                return tok;
                            }
                        }
                        else {
                            errmsg(22, tok.nL);
                        } 
                    }

                    // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, BOOL)
                    else if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==25) {

                        switch (valorType){
                            case 5:
                                if (tok.numToken != 2) {
                                    errmsg(17, tok.nL);
                                }
                                break;
                            case 6:
                                if (tok.numToken != 3) {
                                    errmsg(18, tok.nL);
                                }
                                break;
                            case 7:
                                if (tok.numToken != 25) {
                                    errmsg(20, tok.nL);
                                }
                                break;
                            case 8:
                                if (tok.numToken != 4) {
                                    errmsg(19, tok.nL);
                                }
                                break;
                            }

                            assignValueInTabStruct(tokenRecibido, id, tok.lexema);

                            tok=dameToken();
                            return tok;

                        }
                    
                }
                else {
                    errmsg(24, tok.nL);
                }
                

            }
            else{
                errmsg(44, tok.nL);
            }
        }
        
    }

    else {
        errmsg(43, tok.nL);
    }
    
    
    return tok;
}

sT declaracion_arreglo_global(){
    sT tok;
    Symbol newSymbol = {};
    Symbol currentID;
    int cont = 0;
    int numElementsArray = 1;

    tok = dameToken();
    switch (tok.numToken) {
        case 21:
            // Asignar type como 15 (ARRAY_INT)
            newSymbol.type = 15;
            break;
        case 22:
            // Asignar type como 16 (ARRAY_CHAR)
            newSymbol.type = 16;
            break;
        case 23:
            // Asignar type como 17 (ARRAY_BOOL)
            newSymbol.type = 17;
            break;
        case 24:
            // Asignar type como 18 (ARRAY_FLOAT)
            newSymbol.type = 18;
            break;
        default:
            //No se encontró tipo
            errmsg(21, tok.nL);
            break;
    }

    tok=dameToken();

    if (tok.numToken==0) {
        strcpy(newSymbol.id,tok.lexema);
        tablaSimbolos[numElementsSymbols] = newSymbol;

        tok=dameToken();
        int dimension = 1;
        while(dimension==1){
            //Es un corchete?
            if (tok.numToken == 17)
            {
                tok=dameToken();
                if(dimension==1){
                    //Es id
                    if(tok.numToken==0){
                        currentID = getSymbolArray(tok);
                        if(5==currentID.type){
                            tablaSimbolos[numElementsSymbols].indicesArray[cont] = atoi(currentID.value);
                            numElementsArray = numElementsArray * atoi(currentID.value);
                        }
                        else{
                            errmsg(17, tok.nL);
                        }
                    }
                    //Es num entero
                    else if(tok.numToken==2){
                        tablaSimbolos[numElementsSymbols].indicesArray[cont] = atoi(tok.lexema);
                        numElementsArray = numElementsArray * atoi(tok.lexema);
                    }
                    else{
                        errmsg(45, tok.nL);
                    }

                    tok = dameToken();

                    if (tok.numToken != 18){
                        errmsg(47, tok.nL);
                    }

                    tok = dameToken();

                    if (tok.numToken == 1){
                        dimension = 0;
                    }

                }
            }       
            else{
                errmsg(46, tok.nL);
            }
            cont++; 
        }
        
    }
    else{
        //No se encontró id
        errmsg(16, tok.nL);
    }

    tablaSimbolos[numElementsSymbols].numIndicesArray = cont;
    tablaSimbolos[numElementsSymbols].numElementsArray = numElementsArray;

    numElementsSymbols++;
    return tok;
}

sT declaracion_arreglo(){
    sT tok;
    Symbol newSymbol = {};
    Symbol currentID;
    int cont = 0;
    int numElementsArray = 1;

    strcpy(newSymbol.func,currentFunc);

    tok = dameToken();
    switch (tok.numToken) {
        case 21:
            // Asignar type como 15 (ARRAY_INT)
            newSymbol.type = 15;
            break;
        case 22:
            // Asignar type como 16 (ARRAY_CHAR)
            newSymbol.type = 16;
            break;
        case 23:
            // Asignar type como 17 (ARRAY_BOOL)
            newSymbol.type = 17;
            break;
        case 24:
            // Asignar type como 18 (ARRAY_FLOAT)
            newSymbol.type = 18;
            break;
        default:
            //No se encontró tipo
            errmsg(21, tok.nL);
            break;
    }

    tok=dameToken();

    if (tok.numToken==0) {
        strcpy(newSymbol.id,tok.lexema);
        tablaSimbolosFunc[numSymbolsFunc] = newSymbol;

        tok=dameToken();
        int dimension = 1;
        while(dimension==1){
            //Es un corchete?
            if (tok.numToken == 17)
            {
                tok=dameToken();
                if(dimension==1){
                    //Es id
                    if(tok.numToken==0){
                        currentID = getSymbolArray(tok);
                        if(5==currentID.type){
                            tablaSimbolosFunc[numSymbolsFunc].indicesArray[cont] = atoi(currentID.value);
                            numElementsArray = numElementsArray * atoi(currentID.value);
                        }
                        else{
                            errmsg(17, tok.nL);
                        }
                    }
                    //Es num entero
                    else if(tok.numToken==2){
                        tablaSimbolosFunc[numSymbolsFunc].indicesArray[cont] = atoi(tok.lexema);
                        numElementsArray = numElementsArray * atoi(tok.lexema);
                    }
                    else{
                        errmsg(45, tok.nL);
                    }

                    tok = dameToken();

                    if (tok.numToken != 18){
                        errmsg(47, tok.nL);
                    }

                    tok = dameToken();

                    if (tok.numToken == 1){
                        dimension = 0;
                    }

                }
            }       
            else{
                errmsg(46, tok.nL);
            }
            cont++; 
        }
        
    }
    else{
        //No se encontró id
        errmsg(16, tok.nL);
    }

    tablaSimbolosFunc[numSymbolsFunc].numIndicesArray = cont;
    tablaSimbolosFunc[numSymbolsFunc].numElementsArray = numElementsArray;

    numSymbolsFunc++;
    return tok;
}

sT asignacion_arreglo(sT tokenRecibido){
    sT tok;
    Symbol currentArray = getSymbolArray(tokenRecibido);
    Symbol currentID;
    int dimensiones = currentArray.numIndicesArray;
    int positionArray = 1;
    int valorType;
    int valorTypeRes;

    tok=dameToken();

    for (int i = 0; i < dimensiones; i++)
    {
        if (tok.numToken == 17)
        {
            tok=dameToken();
            //Es id
            if(tok.numToken==0)
            {
                currentID = getSymbolArray(tok);
                if(5==currentID.type){
                    if (atoi(currentID.value) >= currentArray.indicesArray[i])
                    {
                        errmsg(48, tok.nL);
                    }
                    else {
                        positionArray = positionArray * (atoi(currentID.value) + 1);
                    }
                    
                }
                else{
                    errmsg(17, tok.nL);
                }
            }
            //Es num entero
            else if(tok.numToken==2){
                if (atoi(tok.lexema) >= currentArray.indicesArray[i])
                {
                    errmsg(48, tok.nL);
                }
                else {
                    positionArray = positionArray * (atoi(tok.lexema) + 1);
                }
            }
            else if(tok.numToken==18) {
                errmsg(52, tok.nL);
            }
            else{
                errmsg(45, tok.nL);
            }

            tok = dameToken();

            if (tok.numToken != 18){
                errmsg(47, tok.nL);
            }

            tok=dameToken();

        }
        else if(tok.numToken == 12){
            remainingDimensions = dimensiones-i;
            errmsg(51, tok.nL);
        }
        else {
            errmsg(46, tok.nL);
        }
    }

    positionArray--;

    if (tok.numToken==12)
    {
        tok=dameToken();
        //Es id
        if(tok.numToken==0)
        {
            valorType = checkValueTypeInTable(tok);
            valorTypeRes = currentArray.type;
            if (valorType != -1)
            {
                if ((valorType+10) != valorTypeRes) {
                    errmsg(32, tok.nL);
                }
                else {
                    assignValueInArray(tokenRecibido, tok.lexema, positionArray);
                }
            }
        }
        //Es valor
        else {
            switch (currentArray.type) {
                case 15:
                    if (tok.numToken==2) {
                        assignValueInArray(tokenRecibido, tok.lexema, positionArray);

                    }
                    else {
                        errmsg(17, tok.nL);
                    }
                    break;
                case 16:
                    if (tok.numToken==3) {
                        assignValueInArray(tokenRecibido, tok.lexema, positionArray);

                    }
                    else {
                        errmsg(18, tok.nL);
                    }
                    break;
                case 17:
                    if (tok.numToken==25) {
                        assignValueInArray(tokenRecibido, tok.lexema, positionArray);

                    }
                    else {
                        errmsg(20, tok.nL);
                    }
                    break;
                case 18:
                    if (tok.numToken==4) {
                        assignValueInArray(tokenRecibido, tok.lexema, positionArray);

                    }
                    else {
                        errmsg(19, tok.nL);
                    }
                    break;
            }
        }
    }
    else {
        errmsg(24, tok.nL);
    }
    
    tok=dameToken();

    return tok;
}       
        

sT declaracion_global(sT tokenRecibido){
    sT tok;
    Symbol newSymbol = {};
    switch (tok.numToken) {
        case 21:
            // Asignar type como 5 (INT)
            newSymbol.type = 5;
            break;
        case 22:
            // Asignar type como 6 (CHAR)
            newSymbol.type = 6;
            break;
        case 23:
            // Asignar type como 7 (BOOL)
            newSymbol.type = 7;
            break;
        case 24:
            // Asignar type como 8 (FLOAT)
            newSymbol.type = 8;
            break;
    }
    tok=dameToken();
    if (tok.numToken==0) {
        strcpy(newSymbol.id,tok.lexema);
        tablaSimbolos[numElementsSymbols] = newSymbol;

        tok=dameToken();
        if (tok.numToken == 12)
        {
            tok=dameToken();

            switch (tablaSimbolos[numElementsSymbols].type) {
                case 5:
                    if (tok.numToken==2) {
                        strcpy(tablaSimbolos[numElementsSymbols].value,tok.lexema);
                    }
                    else {
                        errmsg(17, tok.nL);
                    }
                    break;
                case 6:
                    if (tok.numToken==3) {
                        strcpy(tablaSimbolos[numElementsSymbols].value,tok.lexema);
                    }
                    else {
                        errmsg(18, tok.nL);
                    }
                    break;
                case 7:
                    if (tok.numToken==25) {
                        strcpy(tablaSimbolos[numElementsSymbols].value,tok.lexema);
                    }
                    else {
                        errmsg(20, tok.nL);
                    }
                    break;
                case 8:
                    if (tok.numToken==4) {
                        strcpy(tablaSimbolos[numElementsSymbols].value,tok.lexema);
                    }
                    else {
                        errmsg(19, tok.nL);
                    }
                    break;
            }
        
            numElementsSymbols++;

            tok=dameToken();
            return tok;

        }
        else {
            numElementsSymbols++;
            return tok;
        }
        
    }
    else {
        errmsg(16, tok.nL);
        return tok;
    }

    return tok;
}

sT declaracion(sT tokenRecibido){
    sT tok = tokenRecibido;
    Symbol newSymbol = {};
    switch (tok.numToken) {
        case 21:
            // Asignar type como 5 (INT)
            newSymbol.type = 5;
            break;
        case 22:
            // Asignar type como 6 (CHAR)
            newSymbol.type = 6;
            break;
        case 23:
            // Asignar type como 7 (BOOL)
            newSymbol.type = 7;
            break;
        case 24:
            // Asignar type como 8 (FLOAT)
            newSymbol.type = 8;
            break;
    }
    tok=dameToken();

    if (tok.numToken==0) {
        // CHECAMOS SI EXISTE EN TABLA SIMBOLOS DE FUNCION O GENERAL
        int existe = checkValueTypeInTable(tok);

        if (existe != -1){
            errmsg(23, tok.nL);
        }

        strcpy(newSymbol.id,tok.lexema);
        strcpy(newSymbol.func,currentFunc);
        tablaSimbolosFunc[numSymbolsFunc] = newSymbol;

        tok=dameToken();
        if (tok.numToken == 12)
        {
            tok=dameToken();

            switch (tablaSimbolosFunc[numSymbolsFunc].type) {
                case 5:
                    if (tok.numToken==2) {
                        strcpy(tablaSimbolosFunc[numSymbolsFunc].value,tok.lexema);
                    }
                    else {
                        errmsg(17, tok.nL);
                    }
                    break;
                case 6:
                    if (tok.numToken==3) {
                        strcpy(tablaSimbolosFunc[numSymbolsFunc].value,tok.lexema);
                    }
                    else {
                        errmsg(18, tok.nL);
                    }
                    break;        
                case 7:
                    if (tok.numToken==25) {
                        strcpy(tablaSimbolosFunc[numSymbolsFunc].value,tok.lexema);
                    }
                    else {
                        errmsg(20, tok.nL);
                    }
                    break;
                case 8:
                    if (tok.numToken==4) {
                        strcpy(tablaSimbolosFunc[numSymbolsFunc].value,tok.lexema);
                    }
                    else {
                        errmsg(19, tok.nL);
                    }
                    break;
            }  
        
            numSymbolsFunc++;

            tok=dameToken();
            return tok;

        }
        // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, STRING, BOOL)
        else if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==5 || tok.numToken==25) {
            errmsg(24, tok.nL);
            return tok;
        }
        else {
            numSymbolsFunc++;
            return tok;
        }
        
    }
    else {
        errmsg(16, tok.nL);
        return tok;
    }

    return tok;
}

sT asignacion(sT tokenRecibido){
    sT tok;
    char operacion[MAX];
    int valorType;
    int valorTypeRes;
    tok=dameToken();
    if (tok.numToken==12) {
        tok=dameToken();

        if (tok.numToken==0){
            valorType = checkValueTypeInTable(tok);
            valorTypeRes = checkValueTypeInTable(tokenRecibido);
            if (valorType != -1)
            {
                if (valorType >=1 && valorType <=4)
                {
                    if ((valorType+4) != valorTypeRes) {
                        errmsg(32, tok.nL);
                    }
                    else {
                        llamada_funcion(tok);
                        assignValueInTable(tokenRecibido, result);
                        tok=dameToken();
                    }
                    return tok;
                }
                
                else if (valorType != valorTypeRes) {
                    errmsg(32, tok.nL);
                }
                
                else {
                    strcpy(operacion, tok.lexema);
                    tok=dameToken();
                    if (tok.numToken!=9)
                    {
                        if (tok.numToken == 1)
                        {
                            return tok;
                        }

                        else{
                        
                            errmsg(31, tok.nL);
                        }
                    }
                    else {
                        strcat(operacion, tok.lexema);
                        tok=dameToken();
                        if (tok.numToken==0){
                            valorType = checkValueTypeInTable(tok);
                            valorTypeRes = checkValueTypeInTable(tokenRecibido);
                            if (valorType != -1)
                            {
                                if (valorType != valorTypeRes) {
                                    errmsg(32, tok.nL);
                                }
                                else {
                                    strcat(operacion, tok.lexema);
                                    assignValueInTable(tokenRecibido, operacion);
                                    tok=dameToken();
                                    return tok;
                                }
                            }
                            else {
                                errmsg(22, tok.nL);
                            } 
                            
                        }
                        // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, STRING, BOOL)
                        else if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==5 || tok.numToken==25) {
                        
                            valorTypeRes = checkValueTypeInTable(tokenRecibido);

                            switch (valorTypeRes){
                                case 5:
                                    if (tok.numToken != 2) {
                                        errmsg(17, tok.nL);
                                    }
                                    break;
                                case 6:
                                    if (tok.numToken != 3) {
                                        errmsg(18, tok.nL);
                                    }
                                    break;
                                case 7:
                                    if (tok.numToken != 25) {
                                        errmsg(20, tok.nL);
                                    }
                                    break;
                                case 8:
                                    if (tok.numToken != 4) {
                                        errmsg(19, tok.nL);
                                    }
                                    break;
                                case 9:
                                    if (tok.numToken != 5) {
                                        errmsg(27, tok.nL);
                                    }
                                    break;
                                }
                                strcat(operacion, tok.lexema);

                                assignValueInTable(tokenRecibido, operacion);

                                tok=dameToken();
                                return tok;

                        }
                    }
                }
            }
            else {
                errmsg(22, tok.nL);
            } 
            
        }
        // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, STRING, BOOL)
        else if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==5 || tok.numToken==25) {
            
            valorTypeRes = checkValueTypeInTable(tokenRecibido);

            switch (valorTypeRes){
                case 5:
                    if (tok.numToken != 2) {
                        errmsg(17, tok.nL);
                    }
                    break;
                case 6:
                    if (tok.numToken != 3) {
                        errmsg(18, tok.nL);
                    }
                    break;
                case 7:
                    if (tok.numToken != 25) {
                        errmsg(20, tok.nL);
                    }
                    break;
                case 8:
                    if (tok.numToken != 4) {
                        errmsg(19, tok.nL);
                    }
                    break;
                case 9:
                    if (tok.numToken != 5) {
                        errmsg(27, tok.nL);
                    }
                    break;
                }
            
            strcpy(operacion, tok.lexema);

            tok=dameToken();
            if (tok.numToken!=9)
            {
                if (tok.numToken == 1)
                {
                    assignValueInTable(tokenRecibido, operacion);

                    return tok;
                }

                else{
                
                    errmsg(31, tok.nL);
                }
            }
            else {
                strcat(operacion, tok.lexema);
                tok=dameToken();
                if (tok.numToken==0){
                    valorType = checkValueTypeInTable(tok);
                    valorTypeRes = checkValueTypeInTable(tokenRecibido);
                    if (valorType != -1)
                    {
                        if (valorType != valorTypeRes) {
                            errmsg(32, tok.nL);
                        }
                        else {
                            strcat(operacion, tok.lexema);
                            assignValueInTable(tokenRecibido, operacion);

                            tok=dameToken();
                            return tok;
                        }
                    }
                    else {
                        errmsg(22, tok.nL);
                    } 
                            
                }
                // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, STRING, BOOL)
                else if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==5 || tok.numToken==25) {
                       
                    valorTypeRes = checkValueTypeInTable(tokenRecibido);

                    switch (valorTypeRes){
                        case 5:
                            if (tok.numToken != 2) {
                                errmsg(17, tok.nL);
                            }
                            break;
                        case 6:
                            if (tok.numToken != 3) {
                                errmsg(18, tok.nL);
                            }
                            break;
                        case 7:
                            if (tok.numToken != 25) {
                                errmsg(20, tok.nL);
                            }
                            break;
                        case 8:
                            if (tok.numToken != 4) {
                                errmsg(19, tok.nL);
                            }
                            break;
                        case 9:
                            if (tok.numToken != 5) {
                                errmsg(27, tok.nL);
                            }
                            break;
                    }
                    strcat(operacion, tok.lexema);
                    assignValueInTable(tokenRecibido, operacion);

                    tok=dameToken();
                    return tok;
                }
            }
        }
        
    }
    else {
        errmsg(24, tok.nL);
    }
    
    return tok;
}

sT variable_global(sT tokRecibido){
    sT tok;
    // SI EL TOKEN ES ALGUN TIPO (INT, CHAR, BOOL, FLOAT)
    if (tokRecibido.numToken==21 || tokRecibido.numToken==22 || tokRecibido.numToken==23 || tokRecibido.numToken==24) {
        tok = declaracion_global(tokRecibido);
        return tok; 
    }
    else if (tokRecibido.numToken==26) {
        tok = declaracion_estructura_global();
        return tok;
    }
    else if (tokRecibido.numToken==40){
        tok = declaracion_arreglo_global();
        return tok;
    }
    
    else {
        errmsg(21, tok.nL);
    }
    return tok;
}


void variables_globales(void){
    sT tok;
    tok=dameToken();

    if (tok.numToken==39) {
        tok=dameToken();
        // MIENTRAS TOK SEA DIFERENTE DE sof
        while (tok.numToken!=35) {
            tok=variable_global(tok);
            if (tok.numToken!=1)
            {
                errmsg(8, tok.nL);
            }
            
            tok=dameToken();
        }
        tok=dameToken();

        if (tok.numToken!= 1) {
            errmsg(8, tok.nL);
        }   
    }
    else if (tok.numToken==35) {
        tok=dameToken();
        if (tok.numToken==1) {
              
        }  
        else {
            errmsg(8, tok.nL);      
        }
    }
    else {
        errmsg(15, tok.nL);
    }
}

void comparacion(void){
    sT tok;
    tok=dameToken();
    int valCompType = -1;
    if (tok.numToken==0) {
        int valueType = checkValueTypeInTable(tok);
        if (valueType == -1) {
            errmsg(22, tok.nL);
        }
        else {
            tok=dameToken();
            if (tok.numToken==10) {
                tok=dameToken();
                if (tok.numToken==0)
                {
                    int valueType2 = checkValueTypeInTable(tok);
                    if (valueType2 == -1) {
                        errmsg(22, tok.nL);
                    }
                    else if (valueType != valueType2) {
                        errmsg(33, tok.nL);
                    }
                    
                }
                else{
                    switch (valueType){
                    case 5:
                        if (tok.numToken != 2) {
                            errmsg(17, tok.nL);
                        }
                        break;
                    case 6:
                        if (tok.numToken != 3) {
                            errmsg(18, tok.nL);
                        }
                        break;
                    case 7:
                        if (tok.numToken != 25) {
                            errmsg(20, tok.nL);
                        }
                        break;
                    case 8:
                        if (tok.numToken != 4) {
                            errmsg(19, tok.nL);
                        }
                        break;
                    case 9:
                        if (tok.numToken != 5) {
                            errmsg(27, tok.nL);
                        }
                        break;
                    }
                }
            }
            else {
                errmsg(30, tok.nL);
            }
        }
    }
    else if (tok.numToken==25) {
        // ES BOLEANO, NO SE HACE NADA
    }
    // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, STRING, BOOL)
    else if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==5 || tok.numToken==25) {
        valCompType = tok.numToken;
        tok=dameToken();
        if (tok.numToken==10) {
            tok=dameToken();
            if (tok.numToken==0) {
                int valueType = checkValueTypeInTable(tok);
                if (valueType == -1) {
                    errmsg(22, tok.nL);
                }
                else {
                    switch (valueType){
                        case 5:
                            if (tok.numToken != 2) {
                                errmsg(17, tok.nL);
                            }
                            break;
                        case 6:
                            if (tok.numToken != 3) {
                                errmsg(18, tok.nL);
                            }
                            break;
                        case 7:
                            if (tok.numToken != 25) {
                                errmsg(20, tok.nL);
                            }
                            break;
                        case 8:
                            if (tok.numToken != 4) {
                                errmsg(19, tok.nL);
                            }
                            break;
                        case 9:
                            if (tok.numToken != 5) {
                                errmsg(27, tok.nL);
                            }
                            break;
                        }
                    }
            }
            // SI EL TOKEN ES DIFERENTE VALOR DEL PRIMER VALOR A COMPARAR
            else if (tok.numToken!=valCompType) {
                errmsg(33, tok.nL);
            }
        }
        else {
            errmsg(30, tok.nL);
        }
    }
    else {
        errmsg(29, tok.nL);
    }  
    
}

sT comparaciones(void){
    sT tok;
    int exit = 0;
    comparacion();
    tok=dameToken();
    while (exit == 0)
    {
        if (tok.numToken==11)
        {
            comparacion();
            tok=dameToken();
        }
        else {
            exit = 1;
        }
    }
    return tok;
    
}

void si(void) {
    sT tok;
    tok=dameToken();
    if (tok.numToken==13) {
        tok = comparaciones();
        if (tok.numToken==14){
            tok=dameToken();
            if (tok.numToken==15){
                int exit = 0;
                tok=dameToken();
                while (exit == 0)
                {
                    lista_instrucciones(tok);
                    tok=dameToken();
                    if (tok.numToken==16){
                        exit = 1;
                    }
                }
                
            }
            else{
                errmsg(13, tok.nL);
            }

        }

        else{
            errmsg(10, tok.nL);
        }
    }
    else{
        errmsg(9, tok.nL);
    }
}

void si_no(void) {
    sT tok;
    tok=dameToken();

    if (tok.numToken==15){
        int exit = 0;
        tok=dameToken();
        while (exit == 0)
        {
            lista_instrucciones(tok);
            tok=dameToken();
            if (tok.numToken==16){
                exit = 1;
            }
        }        
    }
    else{
        errmsg(13, tok.nL);
    }
    
}

void condicional(void){
    sT tok;
    si();
    tok=dameToken();
    int exit = 0;
    while (exit == 0)
    {
        if (tok.numToken == 27)
        {
            si();
            tok=dameToken();
        }
        else {
            exit = 1;
        }
    }
    
    if (tok.numToken == 28)
    {
        si_no();
    }
}

void ciclo(void){
    sT tok;
    tok=dameToken();
    if (tok.numToken==13) {
        tok=comparaciones();
        if (tok.numToken==14) {
            tok=dameToken();
            if (tok.numToken==15){
                int exit = 0;
                tok=dameToken();
                while (exit == 0)
                {
                    lista_instrucciones(tok);
                    tok=dameToken();
                    if (tok.numToken==16){
                        exit = 1;
                    }
                }
                
            }
            else {
                errmsg(14, tok.nL);
            }
        }
        else{
            errmsg(10, tok.nL);
        }
        
        
    }
}

void llamada_funcion(sT tokenRecibido){
    sT tok;
    strcpy(result, tokenRecibido.lexema);

    tok=dameToken();
    if (tok.numToken==13) {
        strcat(result, tok.lexema);
        tok=dameToken();
        //char* str = getParams(tokenRecibido);
        getParams(tokenRecibido);
        // Returns first token
        char *valueType = strtok(currentParams, "-");
        // Keep printing tokens while one of the
        // delimiters present in str[].
        while (valueType != NULL)
        {
            if (tok.numToken==0) {
                strcat(result, tok.lexema);
                // CHECAMOS SI EXISTE EN TABLA SIMBOLOS DE FUNCION O GENERAL
                int existe = checkValueTypeInTable(tok);

                if (existe == -1) {
                    errmsg(22, tok.nL);
                }
                // SI EL VALOR DEL ID ES LO QUE LA FUNCION ESPERA
                else if ((existe == atoi(valueType)) || ((existe-5) == atoi(valueType))) {
                    valueType = strtok(NULL, ",");
                    // SI HAY OTRO PARAMETRO ESPERADO
                    if (valueType != NULL) {
                        tok=dameToken();
                        strcat(result, tok.lexema);
                        // SI ES COMA
                        if (tok.numToken==38)
                        {
                            tok=dameToken();
                        }
                        else {
                            errmsg(49, tok.nL);
                        }
                    }
                    else {
                        tok=dameToken();
                        strcat(result, tok.lexema);
                        // SI NO ES PARENTESIS
                        if (tok.numToken!=14) {
                            errmsg(10, tok.nL);
                        }
                    }
                }
                else {
                    errmsg(36, tok.nL);
                }
                
            }
            else if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==25) {  
                // CHECAR TIPO DE VARIABLE
                switch (atoi(valueType))
                {
                case 5:
                    if (tok.numToken != 2){
                        errmsg(17, tok.nL);
                    }
                    
                    break;
                case 6:
                    if (tok.numToken != 3){
                        errmsg(18, tok.nL);
                    }
                    break;
                case 7:
                    if (tok.numToken != 25){
                        errmsg(20, tok.nL);
                    }
                    break;
                case 8:
                    if (tok.numToken != 4){
                        errmsg(19, tok.nL);
                    }
                    break;
                }

                valueType = strtok(NULL, ",");
                // SI HAY OTRO PARAMETRO ESPERADO
                if (valueType != NULL) {
                    tok=dameToken();
                    strcat(result, tok.lexema);
                    // SI ES COMA
                    if (tok.numToken==38)
                    {
                        tok=dameToken();
                    }
                    else {
                        errmsg(49, tok.nL);
                    }
                }
                else {
                    tok=dameToken();
                    strcat(result, tok.lexema);
                    // SI NO ES PARENTESIS
                    if (tok.numToken!=14) {
                        errmsg(10, tok.nL);
                    }
                }
            }
            else{
                
                errmsg(50, tok.nL);
                
            }
        }
    }

    else {
        errmsg(9, tok.nL);
    }
    
}

void leer(void){
    sT tok;
    tok=dameToken();
    if (tok.numToken==13) {
        tok=dameToken();
        if(tok.numToken==8) {
            char mod = (tok.lexema)[2];
            tok=dameToken();
            if (tok.numToken==38) {
                tok=dameToken();
                if (tok.numToken==0)
                {
                    int valueType = checkValueTypeInTable(tok);
                    if (valueType == -1) {
                        errmsg(22, tok.nL);
                    }
                    else {
                        switch (mod){
                        case 'c':
                            if (valueType != 6) {
                                errmsg(18, tok.nL);
                            }
                            break;
                        case 'd':
                            if (valueType != 5) {
                                errmsg(17, tok.nL);
                            }
                            break;
                        case 'f':
                            if (valueType != 8) {
                                errmsg(19, tok.nL);
                            }
                            break;
                        case 's':
                            if (valueType != 9) {
                                errmsg(27, tok.nL);
                            }
                            break;
                        }
                    }
                    tok=dameToken();
                    if (tok.numToken!=14) {
                        errmsg(10, tok.nL);
                    }
                }
                
                else {
                    errmsg(28, tok.nL);
                }
            }
            else{
                errmsg(26, tok.nL);
            }
            
        }
        else{
            errmsg(25, tok.nL);

        }
    } 

    else{
        errmsg(9, tok.nL);
    }

}

void escribe(void){
    sT tok;
    tok=dameToken();
    if (tok.numToken==13) {
        tok=dameToken();
        if (tok.numToken==5){
            tok=dameToken();
            if (tok.numToken!=14) {
                errmsg(10, tok.nL);
            }
        }
        else if(tok.numToken==8) {
            char mod = (tok.lexema)[2];
            tok=dameToken();
            if (tok.numToken==38) {
                tok=dameToken();
                if (tok.numToken==0)
                {
                    int valueType = checkValueTypeInTable(tok);
                    if (valueType == -1) {
                        errmsg(22, tok.nL);
                    }
                    else {
                        switch (mod){
                        case 'c':
                            if (valueType != 6) {
                                errmsg(18, tok.nL);
                            }
                            break;
                        case 'd':
                            if (valueType != 5) {
                                errmsg(17, tok.nL);
                            }
                            break;
                        case 'f':
                            if (valueType != 8) {
                                errmsg(19, tok.nL);
                            }
                            break;
                        case 's':
                            if (valueType != 9) {
                                errmsg(27, tok.nL);
                            }
                            break;
                        }
                    }
                }
                
                else {      
                    switch (mod){
                    case 'c':
                        if (tok.numToken != 3) {
                            errmsg(18, tok.nL);
                        }
                        break;
                    case 'd':
                        if (tok.numToken != 2) {
                            errmsg(17, tok.nL);
                        }
                        break;
                    case 'f':
                        if (tok.numToken != 4) {
                            errmsg(19, tok.nL);
                        }
                        break;
                    case 's':
                        if (tok.numToken != 5) {
                            errmsg(27, tok.nL);
                        }
                        break;
                    }
                }
                tok=dameToken();
                if (tok.numToken!=14) {
                    errmsg(10, tok.nL);
                }
            }
            else{
                errmsg(26, tok.nL);
            }
            
        }
        else{
            errmsg(25, tok.nL);

        }
    } 

    else{
        errmsg(9, tok.nL);
    }
}

void escribenl(void){
    sT tok;
    tok=dameToken();
    if (tok.numToken==13) {
        tok=dameToken();
        if (tok.numToken==5){
            tok=dameToken();
            if (tok.numToken!=14) {
                errmsg(10, tok.nL);
            }
        }
        else if(tok.numToken==8) {
            char mod = (tok.lexema)[2];
            tok=dameToken();
            if (tok.numToken==38) {
                tok=dameToken();
                if (tok.numToken==0)
                {
                    int valueType = checkValueTypeInTable(tok);
                    if (valueType == -1) {
                        errmsg(22, tok.nL);
                    }
                    else {
                        switch (mod){
                        case 'c':
                            if (valueType != 6) {
                                errmsg(18, tok.nL);
                            }
                            break;
                        case 'd':
                            if (valueType != 5) {
                                errmsg(17, tok.nL);
                            }
                            break;
                        case 'f':
                            if (valueType != 8) {
                                errmsg(19, tok.nL);
                            }
                            break;
                        case 's':
                            if (valueType != 9) {
                                errmsg(27, tok.nL);
                            }
                            break;
                        }
                    }
                }
                
                else {      
                    switch (mod){
                    case 'c':
                        if (tok.numToken != 3) {
                            errmsg(18, tok.nL);
                        }
                        break;
                    case 'd':
                        if (tok.numToken != 2) {
                            errmsg(17, tok.nL);
                        }
                        break;
                    case 'f':
                        if (tok.numToken != 4) {
                            errmsg(19, tok.nL);
                        }
                        break;
                    case 's':
                        if (tok.numToken != 5) {
                            errmsg(27, tok.nL);
                        }
                        break;
                    }
                }
                tok=dameToken();
                if (tok.numToken!=14) {
                    errmsg(10, tok.nL);
                }
            }
            else{
                errmsg(26, tok.nL);
            }
            
        }
        else{
            errmsg(25, tok.nL);

        }
    } 

    else{
        errmsg(9, tok.nL);
    }
}

sT instruccion(sT tokenRecibido){
    sT tok;
    tok=tokenRecibido;
    // SI ES ALGUN TIPO (INT, CHAR, BOOL, FLOAT)
    if (tok.numToken==21 || tok.numToken==22 || tok.numToken==23 || tok.numToken==24) {
        tok = declaracion(tok);
    }
    else if (tok.numToken==26) {
        tok = declaracion_estructura();
    }
    else if (tok.numToken==40){
        tok = declaracion_arreglo();
    }
    // SI TOKEN ES ID
    else if (tok.numToken==0) {
        // CHECAMOS SI EXISTE EN TABLA SIMBOLOS DE FUNCION O GENERAL
        int existe = checkValueTypeInTable(tok);
        if (existe == -1){
            errmsg(22, tok.nL);
        }
        else if (existe >=1 && existe <=4)
        { 
            llamada_funcion(tok);
            tok=dameToken();
        }

        else if (existe >=10 && existe <=14)
        {
            errmsg(35, tok.nL);
        }
        else if (existe >=15 && existe <=18)
        {
            tok=asignacion_arreglo(tok);
        }

        else if (existe == 0)
        {
            tok=asignacion_estructura(tok);
        }
        
        else {
            tok=asignacion(tok);
        }
        
    }
    else if (tok.numToken==27) {
        condicional();
        tok=dameToken();
    }
    else if (tok.numToken==29) {
        ciclo();
        tok=dameToken();
    }
    else if (tok.numToken==30) {
        leer();
        tok=dameToken();
    }
    else if (tok.numToken==31) {
        escribe();
        tok=dameToken();
    }
    else if (tok.numToken==32) {
        escribenl();
        tok=dameToken();
    }
    else if (tok.numToken==35) {
        tok=dameToken();
    }

    return tok;
}

void lista_instrucciones(sT tokenRecibido){
    sT tok;
    tok = instruccion(tokenRecibido);
    if (tok.numToken!=1)
    {
        errmsg(8, tok.nL);

    }
    
}

void cuerpo(void){
    sT tok;
    tok=dameToken();
    int exit = 0;

    while (exit == 0)
    {   
        if (tok.numToken == 33) {
            exit = 1;
        }
        else if (tok.numToken == 16) {
            errmsg(37, tok.nL);

        }
        
        else {
            lista_instrucciones(tok);
        }
        tok=dameToken();
    }

    // SI EL TOKEN ES ALGUN VALOR (ENTERO, CHAR, REAL, BOOL)
    if (tok.numToken==2 || tok.numToken==3 || tok.numToken==4 || tok.numToken==25) {  
        // CHECAR TIPO DE RETORNO
        switch (tok.numToken)
        {
        case 2:
            if (tablaSimbolos[numElementsSymbols-1].type != 1){
                errmsg(39, tok.nL);
            }
            
            break;
        case 3:
            if (tablaSimbolos[numElementsSymbols-1].type != 2){
                errmsg(39, tok.nL);
            }
            break;
        case 4:
            if (tablaSimbolos[numElementsSymbols-1].type != 4){
                errmsg(39, tok.nL);
            }
            break;
        case 25:
            if (tablaSimbolos[numElementsSymbols-1].type != 3){
                errmsg(39, tok.nL);
            }
            break;
        }
        tok=dameToken();
    }

    else if (tok.numToken==0) {
        // CHECAMOS SI EXISTE EN TABLA SIMBOLOS DE FUNCION O GENERAL
        int existe = checkValueTypeInTable(tok);

        if (existe == -1){
            errmsg(22, tok.nL);
        }
        else {
            // CHECAR TIPO DE RETORNO
            switch (existe)
            {
            case 5:
            case 10:
                if (tablaSimbolos[numElementsSymbols-1].type != 1){
                    errmsg(39, tok.nL);
                }
                
                break;
            case 6:
            case 11:
                if (tablaSimbolos[numElementsSymbols-1].type != 2){
                    errmsg(39, tok.nL);
                }
                break;
            case 7:
            case 12:
                if (tablaSimbolos[numElementsSymbols-1].type != 3){
                    errmsg(39, tok.nL);
                }
                break;
            case 8:
            case 13:
                if (tablaSimbolos[numElementsSymbols-1].type != 4){
                    errmsg(39, tok.nL);
                }
                break;
            }
    
            tok=dameToken();
        }
    }

    else {
        errmsg(38, tok.nL);
    }
    
    if (tok.numToken!= 1) {
        errmsg(8, tok.nL);
    }
}

void parametros(void){
    sT tok;
    Symbol newSymbol = {};
    char parameter[MAX];

    tok=dameToken();

    if (tok.numToken==34) {
        tok=dameToken();
        strcpy(tablaSimbolos[numElementsSymbols-1].params,"");

        if (tok.numToken!=14){
            errmsg(10, tok.nL);
        }
    }
    // SI EL TOKEN ES ALGUN TIPO (INT, CHAR, BOOL, FLOAT)
    else if (tok.numToken==21 || tok.numToken==22 || tok.numToken==23 || tok.numToken==24) {
        // Nuevo symbol
        newSymbol.type = tok.numToken-16;
        sprintf(parameter, "%d", tok.numToken-16); 
        strcpy(tablaSimbolos[numElementsSymbols-1].params,parameter);
        tok=dameToken();
        if (tok.numToken==0) {
            // Asigna lexema a symbol y se guarda en tabla de simbolos de funcion
            strcpy(newSymbol.id,tok.lexema);
            strcpy(newSymbol.func,currentFunc);
            tablaSimbolosFunc[numSymbolsFunc] = newSymbol;
            numSymbolsFunc++;
            tok=dameToken();
            if (tok.numToken==38){
                int exit = 0;
                while (exit == 0)
                {
                    tok=dameToken();
                    if (tok.numToken==21 || tok.numToken==22 || tok.numToken==23 || tok.numToken==24) {
                        // Nuevo symbol
                        Symbol newSymbol = {};
                        newSymbol.type = tok.numToken-16;
                        sprintf(parameter, "%d", tok.numToken-16); 
                        strcat(tablaSimbolos[numElementsSymbols-1].params, "-");
                        strcat(tablaSimbolos[numElementsSymbols-1].params, parameter);
                        tok=dameToken();
                        if (tok.numToken==0) {
                            // Asigna lexema a symbol y se guarda en tabla de simbolos de funcion
                            strcpy(newSymbol.id,tok.lexema);
                            strcpy(newSymbol.func,currentFunc);
                            tablaSimbolosFunc[numSymbolsFunc] = newSymbol;
                            numSymbolsFunc++;
                            tok=dameToken();
                            if (tok.numToken==38){
                                continue;
                            }
                            else if (tok.numToken!=14){
                                errmsg(10, tok.nL);
                            }
                            else {
                                exit = 1;
                            }
                        }
                        else {
                            errmsg(12, tok.nL);
                        }
                    }
                    else {
                        errmsg(11, tok.nL);
                    }
                }
                
            }
            else if (tok.numToken!=14){
                errmsg(11, tok.nL);
            }
        }
        else {
            errmsg(12, tok.nL);
        }
    }
    else {
        errmsg(11, tok.nL);
    }
    
    
}

void funcion(void){
    sT tok;
    
    tok=dameToken();   
    // Nuevo symbol
    Symbol newSymbol = {};
    // SI TOK ES ALGUN TIPO DE VARIABLE
    switch (tok.numToken) {
        case 21:
            // Asignar type como 1 (FUNC_INT)
            newSymbol.type = 1;
            break;
        case 22:
            // Asignar type como 2 (FUNC_CHAR)
            newSymbol.type = 2;
            break;
        case 23:
            // Asignar type como 3 (FUNC_BOOL)
            newSymbol.type = 3;
            break;
        case 24:
            // Asignar type como 4 (FUNC_FLOAT)
            newSymbol.type = 4;
            break;
        default:
            errmsg(6, tok.nL);
            break;
    }
    tok=dameToken();
    // SI EL TOKEN ES ID
    if(tok.numToken==0) {
        // Asignar lexema al symbol
        strcpy(newSymbol.id,tok.lexema);
        strcpy(currentFunc,tok.lexema);
        // ingresar symbol al tabla de simbolos
        tablaSimbolos[numElementsSymbols] = newSymbol;
        numElementsSymbols++;
        tok=dameToken();
        if (tok.numToken==13) {
            parametros();
            tok=dameToken();

            if (tok.numToken==15){
                cuerpo();
                tok=dameToken();
                if (tok.numToken == 41){
                    funcion();
                }
                else if (tok.numToken!= 16) {
                    errmsg(14, tok.nL);
                }
            }
            else {
                errmsg(13, tok.nL);
            }
            
        }
        else {
            errmsg(9, tok.nL);
        }
    }
    else {
        errmsg(7, tok.nL);
    }
}

void funciones(void){
    sT tok;
    tok=dameToken();
    int exit = 0;
    while (exit == 0){
        if (tok.numToken != 41)
        {
            errmsg(34, tok.nL);
        }
        funcion();
        tok=dameToken();
        
        if (tok.numToken==1)
        {
            tok=dameToken();
            if (tok.numToken == 41) {
                errmsg(42, tok.nL);
            }
            
        }

        else if (tok.numToken!=41)
        {
            exit = 1;
        }
        
    }
}

void s(void){
    directivas();
    variables_globales();
    funciones();
}

int main(void)
{
    printf("\nIngresa el archivo a analizar: ");
    gets(fname);
    fp=fopen(fname,"r");
    if(fp == NULL){
        printf("Error al abrir archivo\n");
        return 0;
    }
    s();
    if(!numErr){
        printf("Compilado con 0 errores y 0 warnings\n");
    }
    else {
        printf("%i errores generados.\n",numErr);
    }
  	fclose(fp);

    return 0;
}
