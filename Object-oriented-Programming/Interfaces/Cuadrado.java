/*
	Interfaces: Cuadrado
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class Cuadrado implements Figura
{
	private double x;

	public Cuadrado(double x)
	{
		this.x = x;
	}

	public double area()
	{
		return x*x;
	}

	public double perimetro()
	{
		return 4*x;
	}
}