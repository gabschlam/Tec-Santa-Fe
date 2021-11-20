/*
    Algoritmos de Ordenamiento: Intercambio - Clase Persona
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Persona {
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private int fechaNacimiento;

    public Persona(String nombre, String aPaterno, String aMaterno, int fechaNacimiento) {
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getaPaterno() {
        return aPaterno;
    }

    public void setaPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public String getaMaterno() {
        return aMaterno;
    }

    public void setaMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " " + aPaterno + " " + aMaterno + ". Fecha de Nacimiento: " + fechaNacimiento;
    }

    
}
