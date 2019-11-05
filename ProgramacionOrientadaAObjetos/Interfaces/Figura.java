/*
	Interfaces: Figura
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public interface Figura
{
	/*
	LAS VARIABLES DECLARADAS EN UNA INTERFAZ SON PUBLICAS, ESTETICAS Y FINAL POR DEFAULT.
	LAS VARIABLES DE LAS INTERFACES DEBEN SER INICIALIZADAS EN EL MOMENTO DE LA DECLARACION, DE OTRA FORMA EL COMPILADOR MARCA ERROR
	*/

	public String color = "rojo";

	public double area();
	public double perimetro();

}