/*
    Ejemplos de Recursividad: Producto
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Arrays;
import java.util.Scanner;

public class productoElementos
{
	public static void main(String[] args)
	{
		int[] arreglo;

		Scanner lector = new Scanner(System.in);
		
		System.out.println("Escribe la longitud del arreglo");

		arreglo = new int[lector.nextInt()];

		for (int i = 0; i<arreglo.length; i++) 
		{
			System.out.println("Dame la posicion " + (i+1) + " del arreglo");
			arreglo[i] = lector.nextInt();
		}

		producto(arreglo, arreglo.length, 1, 0);

		System.out.println(Arrays.toString(arreglo));
	}

	public static int producto(int[] a, int n, int left, int i)
	{
		if (i == n) 
		{
			return 1;
		}

		int temp = a[i];

		int right = producto(a, n, left * a[i], i+1);

		a[i] = left * right;

		return temp * right;
	}
}