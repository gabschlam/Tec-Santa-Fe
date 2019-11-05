public class CalculoPerimetro
{
	public static void main(String[] args) 
	{
		Figura cuadrado = new Figura();
		Figura triangulo = new Figura();
		Figura rectangulo = new Figura();
		Figura trapecio = new Figura();

		System.out.println("Perímetro de un cuadrado de 2x2: " + cuadrado.perimetro(2));

		System.out.println("Perímetro de un rectángulo de 2x5: " + rectangulo.perimetro(2, 5));

		System.out.println("Perímetro de un triángulo de 2x3x4: " + triangulo.perimetro(2, 3, 4));

		System.out.println("Perímetro de un trapecio de 5x3.2x3x2: " + trapecio.perimetro(5, 3.2, 2, 3));

	}
}