/*
    Listas Circulares: Clase Jugada
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Jugada {
    private static int NElementos = 0;
    
    private int jugada;
    private Jugada siguiente;
    private Jugada previo;

    public Jugada(int jugada) {
        this.jugada = jugada;
        NElementos++;
    }

    public static int getNElementos() {
        return NElementos;
    }

    public int getJugada() {
        return jugada;
    }

    public void setJugada(int jugada) {
        this.jugada = jugada;
    }

    public Jugada getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Jugada siguiente) {
        this.siguiente = siguiente;
    }

    public Jugada getPrevio() {
        return previo;
    }

    public void setPrevio(Jugada previo) {
        this.previo = previo;
    }

    @Override
    public String toString() {
        return "Cuenta de dedos = " + jugada;
    }
    
    
}
