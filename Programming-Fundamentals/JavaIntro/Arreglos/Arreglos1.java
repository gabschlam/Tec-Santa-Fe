import java.util.Scanner;

public class Arreglos1
{
	public static void main(String[] args)
	{
		double total = 0, prom = 0;
		
		int[] arreglo = {3, 5, 8};
		
		int[] arreglo2 = new int[5]; //definiendo arreglo con 5 elementos//
		
		for(int i=0; i<arreglo.length; i++) //arreglo.length es para que te ponga el tamaño del arreglo//
		{
			System.out.println(arreglo[i]);
		}
		
		System.out.println("Longitud: " + arreglo.length);
		
		arreglo2[0] = 95;
		arreglo2[1] = 81;
		arreglo2[2] = 70;
		arreglo2[3] = 100;
		arreglo2[4] = 65;
		
		for(int i=0; i<arreglo2.length; i++)
		{
			total += arreglo2[i];
		}
		prom = total / arreglo2.length;
		System.out.println("El promedio es de: " + prom);
	}
}