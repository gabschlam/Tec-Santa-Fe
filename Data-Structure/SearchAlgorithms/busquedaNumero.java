/*
    Algoritmos de Búsqueda: Búsqueda de Número
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Arrays;
import java.util.Scanner;

public class busquedaNumero
{
	public static void main(String[] args)
	{
		int n = 0, busqueda = 0;

		int[] arreglo = {5, -14, 10, 8, 7, 9, 40, -10, 0, 14};

		Scanner lector = new Scanner(System.in);

		System.out.println("Ingresa el numero que quieres buscar");

		n = lector.nextInt();

		for (int i = 0; i< arreglo.length; i++)
		{
			if (n == arreglo[i])
			{
				busqueda = 1;
				System.out.println("El numero se encuentra en la posicion: " + i);
				break;
			}
		}

		if (busqueda != 1) 
		{
			System.out.println("El numero no se encuentra en el arreglo");
		}

	}
}