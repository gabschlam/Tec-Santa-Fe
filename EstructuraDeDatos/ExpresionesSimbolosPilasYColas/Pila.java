/*
    Ejemplo de expresiones balanceadas con Pilas y Colas: Clase Pila
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Arrays;

public class Pila implements IPila{
    private static final int TAM_MAX = 100;
    private int tope; 
    private char elem[]; 

    public Pila() {
        tope = TAM_MAX;
        elem = new char[TAM_MAX];
    }   
   
    @Override
    public boolean push(char x) 
    { 
	if (tope == 0){
            return false;
        }
	else
	{ 
            tope--;
            elem[tope] = x;
            return true;	
        } 
    } 

    @Override
    public char pop() 
    { 
	if (tope == 0){
            return '0'; 
        }
	else
	{ 
            char element = elem[tope]; 
            tope++; 
            return element; 
        } 
    } 

    @Override
    public Object top() {
        if (esVacia()){
            return null;
        }
        return elem[tope];
    }
    
    @Override
    public boolean vaciar() {
        tope = TAM_MAX;
        elem = new char[TAM_MAX];
        return true;
    }
    
    @Override
    public boolean esVacia() 
    { 
	return tope == TAM_MAX;
    } 

    @Override
    public String toString() {
        return "Pila: " + Arrays.toString(elem);
    }
 }
