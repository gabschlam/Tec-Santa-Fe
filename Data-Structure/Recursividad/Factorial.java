/*
    Ejemplos de Recursividad: Número Factorial
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Scanner;

public class Factorial
{
	public static void main(String[] args)
	{
		int n;

		Scanner lector = new Scanner(System.in);
		
		System.out.println("Escribe el numero del que quieres su factorial");

		n = lector.nextInt();

		System.out.println("El factorial es: " + factorial(n));
	}

	public static int factorial(int n)
	{
		if (n==1) 
			return 1;
		else
			return n * factorial(n-1);
	}
}