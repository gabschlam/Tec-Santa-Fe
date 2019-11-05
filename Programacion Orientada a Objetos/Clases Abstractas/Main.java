import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		Scanner lector = new Scanner(System.in);
		
		System.out.println("CREANDO UN CIRCULO");
		System.out.print("Introduce el radio: ");
		double radio = lector.nextDouble();
		System.out.print("Introduce el color: ");
		String color = lector.next();
		
		Figura figura = new Circulo(color, radio);
		System.out.println("El area del circulo es: " + figura.area());
		System.out.println("El perímetro del círculo es: " + figura.perimetro());
		System.out.println("El color del círculo es: " + figura.getColor());
		
		System.out.println("CREANDO UN CUADRADO");
		System.out.print("Introduce el lado del cuadrado: ");
		double lado = lector.nextDouble();
		System.out.print("Introduce el color: ");
		color = lector.next();
		
		figura = new Cuadrado(color, lado);
		System.out.println("El área del cuadrado es: " + figura.area());
		System.out.println("El perímetro del cuadrado es: " + figura.perimetro());
		System.out.println("El color del cuadrado es: " + figura.getColor());
		
	}
}