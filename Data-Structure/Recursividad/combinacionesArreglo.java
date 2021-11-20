/*
    Ejemplos de Recursividad: Combinaciones Arreglo
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class combinacionesArreglo
{
	public static void main(String[] args)
	{
		int k;

		int[] arreglo;

		Scanner lector = new Scanner(System.in);
		
		System.out.println("Escribe la longitud del arreglo");

		arreglo = new int[lector.nextInt()];

		for (int i = 0; i<arreglo.length; i++) 
		{
			System.out.println("Dame el elemento " + (i+1) + " del arreglo");
			arreglo[i] = lector.nextInt();
		}

		System.out.println("Escribe la longitud de las combinaciones deseadas");

		k = lector.nextInt();

		Arrays.sort(arreglo);

		combinaciones(arreglo, "", 0, arreglo.length, k);

	}

	public static void combinaciones(int[] a, String salida, int i, int n, int k)
	{
		if (k == 0) 
		{
			System.out.println("{ " + salida + "}");
			return;
		}
		
		for (int j = i; j < n; j++)
		{
			combinaciones(a, salida + (a[j]) + " ", j + 1, n, k - 1);

			while (j < n - 1 && a[j] == a[j + 1]) 
			{
				j++;
			}
		}
	}

}