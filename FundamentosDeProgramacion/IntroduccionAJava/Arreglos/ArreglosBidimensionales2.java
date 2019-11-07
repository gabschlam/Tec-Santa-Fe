import java.util.Random;

public class ArreglosBidimensionales2
{
	public static void main(String[] args)
	{
		Random aleatorio = new Random(); 

		int[][] matriz = new int [10] [10];

		for (int renglones = 0; renglones < matriz.length ; renglones++)
		{
			for (int columnas = 0 ; columnas < matriz[0].length ; columnas ++)
			{
				matriz[renglones][columnas] = aleatorio.nextInt(100)+1;
				System.out.printf("[%d] \t", matriz [renglones][columnas]);
			}
			System.out.println();
		}
	}
}