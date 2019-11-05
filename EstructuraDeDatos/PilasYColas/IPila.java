/*
    Pilas y Colas: Interface IPila
    Estructura de Datos
 
    Gabriel Schlam
*/

public interface IPila {
    boolean push(Object elemento);
    boolean pop();
    Object top();
    boolean vaciar();
    boolean esVacia();
}
