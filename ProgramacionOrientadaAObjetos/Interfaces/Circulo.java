/*
	Interfaces: Círculo
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class Circulo implements Figura
{
	private double r;
	private final double pi = 3.1416;

	public Circulo(double r)
	{
		this.r = r;
	}

	public double area()
	{
		return pi*r*r;
	}

	public double perimetro()
	{
		return (2*pi)*r;
	}
}