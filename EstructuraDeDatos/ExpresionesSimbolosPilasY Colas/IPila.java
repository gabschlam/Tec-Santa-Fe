/*
    Ejemplo de expresiones balanceadas con Pilas y Colas: Interfáz IPila
    Estructura de Datos
 
    Gabriel Schlam
*/

public interface IPila {
    boolean push(char elemento);
    char pop();
    Object top();
    boolean vaciar();
    boolean esVacia();
}
