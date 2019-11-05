/*
    Algoritmos de Búsqueda: Búsqueda de Número de forma Binaria
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Arrays;
import java.util.Scanner;

public class numeroBinaria
{
	public static void main(String[] args)
	{	
		int n = 0, res = 0;

		int[] arreglo = {5, -14, 10, 8, 7, 9, 40, -10, 0, 14};

		Scanner lector = new Scanner(System.in);

		System.out.println("Ingresa el numero que quieres buscar");

		n = lector.nextInt();

		res = busquedaBinaria(arreglo, n, 0, arreglo.length-1);

		if (res != -1) 
		{
			System.out.println("El numero si existe en el arreglo, esta en la posicion: " + res);
		} 
		else 
		{
			System.out.println("El numero no se encuentra en el arreglo");
		}		
	}

	public static int busquedaBinaria(int[] arreglo, int num, int inf, int sup)
	{
		Arrays.sort(arreglo);

		int mitad = (inf+sup)/2;

		if ((inf >= sup) && (arreglo[inf] != num))
		{
			return -1;
		}

		else if (arreglo[mitad] == num)
		{
			return mitad;
		}

		else if (num > arreglo[mitad])
		{
			return busquedaBinaria(arreglo, num, mitad+1, sup);
		}

		return busquedaBinaria(arreglo, num, inf, mitad-1);
	}
}