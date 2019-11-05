import java.util.Scanner;

public class MainPunto
{
	public static void main(String[] args)
	{
		Punto p1;
		Punto p2;
		int x1, y1, x2, y2;

		Scanner lector = new Scanner(System.in);

		System.out.println("Dame la posicion en eje x del primer punto");

		x1 = lector.nextInt();

		System.out.println("Dame la posicion en eje y del primer punto");

		y1 = lector.nextInt();

		System.out.println("Dame la posicion en eje x del segundo punto");

		x2 = lector.nextInt();

		System.out.println("Dame la posicion en eje y del segundo punto");

		y2 = lector.nextInt();

		p1 = new Punto(x1, y1);

		p2 = new Punto(x2, y2);

		System.out.println("La distancia es: " + p1.distancia(p2));
	}
}