/************************************************************************************
Ejemplo de uso de métodos leyendo desde el teclado
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class Metodo2
{
	public static void main(String[] args)
	{
		String nombre;

		int edad;

		System.out.println("¿Como te llamas?");

		Scanner lector = new Scanner(System.in);

		nombre = lector.nextLine();

		System.out.println("¿Cual es tu edad?");

		edad = lector.nextInt();

		imprime(nombre, edad);
	}

	public static void imprime(String nom, int ed)
	{
		System.out.printf("¡Hola %s, tienes %d años!%n", nom, ed);

	}

}
