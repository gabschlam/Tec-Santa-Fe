/************************************************************************************
Ejemplo de uso de while
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class While1
{
	public static void main(String[] args)
	{
		int contador, numero;

		Scanner lector = new Scanner(System.in);

		contador = 100;

		System.out.printf("Escribe el numero hasta donde quieres que cuente %n");

		numero = lector.nextInt();

		while(contador>= numero)
		{
			System.out.printf("Conteo: %d%n", contador);

			contador--;
		}

	}

}