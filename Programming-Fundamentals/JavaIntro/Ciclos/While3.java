/************************************************************************************
Ejemplo de uso de while con metodo
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class While3
{
	public static void main(String[] args)
	{
		int numero;

		Scanner lector = new Scanner(System.in);

		System.out.printf("Escribe el numero que quieres el factorial %n");

		numero = lector.nextInt();

		System.out.printf("El factorial es: " + factorial(numero) + "%n");

	}

	public static int factorial (int num)
	{
		int contador = 1;
		int fact = 1;

		while(contador<= num)
		{
			//fact = fact * contador; TAMBIEN PUEDE SER fact *= contador//
			fact *= contador;
			contador++;
		}

		return fact;

	}

}