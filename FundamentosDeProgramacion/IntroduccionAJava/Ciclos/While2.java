/************************************************************************************
Ejemplo de uso de while con metodo
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class While2
{
	public static void main(String[] args)
	{
		int numero;

		Scanner lector = new Scanner(System.in);

		System.out.printf("Escribe el numero hasta donde quieres que sume %n");

		numero = lector.nextInt();

		System.out.printf("El factorial es: " + sumatoria(numero) + "%n");

	}

	public static int sumatoria (int num)
	{
		int contador = 0;
		int suma = 0;

		while(contador<= num)
		{

			suma = suma + contador;
			contador++;
		}

		return suma;

	}

}