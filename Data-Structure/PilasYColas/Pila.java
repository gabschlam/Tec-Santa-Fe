/*
    Pilas y Colas: Clase Pila
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Arrays;

public class Pila implements IPila{
    private static final int TAM_MAX = 6;
    private int tope;
    private Object[] elem;

    public Pila() {
        tope = TAM_MAX;
        elem = new Object[TAM_MAX];
    }
    
    @Override
    public boolean push(Object elemento) {
        if (tope == 0){
            return false;
        }
        tope--;
        elem[tope] = elemento;
        return true;
    }

    @Override
    public boolean pop() {
        if (esVacia()){
            return false;
        }
        elem[tope] = null;
        tope++;
        return true;
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
        elem = new Object[TAM_MAX];
        return true;
    }

    @Override
    public boolean esVacia() {
        return tope == TAM_MAX;
    }

    @Override
    public String toString() {
        return "Pila: " + Arrays.toString(elem);
    }
    
}
