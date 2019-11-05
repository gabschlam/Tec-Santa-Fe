/*
	Clases abstractas: Círculo
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class Circulo extends Figura
{
	private double r;
	private final double pi = 3.1416;

	public Circulo(String color, double r)
	{
		super(color);
		this.r = r;
	}

	@Override // PARA SABER QUE ESTAS SOBRE ESCRIBIENDO UN METODO
	public double area()
	{
		return pi*r*r;
	}

	@Override
	public double perimetro()
	{
		return (2*pi)*r;
	}
}