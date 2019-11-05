/*
	Clases abstractas: Cuadrado
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class Cuadrado extends Figura
{
	private double x;

	public Cuadrado(String color, double x)
	{
		super(color);
		this.x = x;
	}

	@Override // PARA SABER QUE ESTAS SOBRE ESCRIBIENDO UN METODO
	public double area()
	{
		return x*x;
	}

	@Override
	public double perimetro()
	{
		return 4*x;
	}
}