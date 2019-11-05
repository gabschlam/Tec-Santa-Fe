/*
    Ejemplos de Recursividad: Potencia
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Scanner;

public class Potencia
{
	public static void main(String[] args)
	{
		int x, n;

		Scanner lector = new Scanner(System.in);
		
		System.out.println("Escribe el numero base");

		x = lector.nextInt();

		System.out.println("Escribe la potencia");

		n = lector.nextInt();

		System.out.println("El resultado es: " + potencia(x, n));
	}

	public static int potencia(int x, int n)
	{
		if (n==0) 
			return 1;
		else
			return x * potencia(x, n-1);
	}
}