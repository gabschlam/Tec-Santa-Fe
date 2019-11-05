/*
	Interfaces: Main
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		Scanner lector = new Scanner(System.in);
		
		System.out.println("CREANDO UN CIRCULO");
		System.out.print("Introduce el radio: ");
		double radio = lector.nextDouble();
		
		Figura figura = new Circulo(radio);
		System.out.println("El area del circulo es: " + figura.area());
		System.out.println("El perímetro del círculo es: " + figura.perimetro());
		System.out.println("El color del círculo es: " + figura.color);
		
		System.out.println("CREANDO UN CUADRADO");
		System.out.print("Introduce el lado del cuadrado: ");
		double lado = lector.nextDouble();
		
		figura = new Cuadrado(lado);
		System.out.println("El área del cuadrado es: " + figura.area());
		System.out.println("El perímetro del cuadrado es: " + figura.perimetro());
		System.out.println("El color del cuadrado es: " + figura.color);
		
	}
}