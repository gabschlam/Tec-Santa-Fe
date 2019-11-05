/*
    Listas Simples: Clase Persona
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Persona {
    
    private String nombre;
    private int edad;
    private Persona siguiente;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Persona getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Persona siguiente) {
        this.siguiente = siguiente;
        System.gc();
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Edad: " + edad + ". ¿Tiene siguiente? " + (siguiente==null?"No":"Si");
    }
    
    
}
