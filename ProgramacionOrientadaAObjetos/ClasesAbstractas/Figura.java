/*
	Clases abstractas: Figura
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public abstract class Figura
{
	private String color;

	public Figura(String color)
	{
		this.color = color;
	}

	abstract double area();
	abstract double perimetro();

	public String getColor()
	{
		return color;
	}
}