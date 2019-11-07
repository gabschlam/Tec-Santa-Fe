import java.util.Random; // PARA IMPORTAR NUMEROS RANDOM

public class Aleatorios2
{
	public static void main(String[] args)
	{
		Random numerosAleatorios = new Random(); 
		int num;
		int[] arreglo;
		arreglo = new int[100];

		for (int i = 0; i<arreglo.length ; i++) 
		{
			arreglo[i] = numerosAleatorios.nextInt(100)+1;
			System.out.println("El elemento " + i + " es: " +arreglo[i]);
		}
	}
}