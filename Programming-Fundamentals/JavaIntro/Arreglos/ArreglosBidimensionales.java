import java.util.Random;

public class ArreglosBidimensionales
{
	public static void main(String[] args)
	{
		int[][] matriz = new int [3] [2];

		matriz [0][0] = 7;
		matriz [0][1] = 8;
		matriz [1][0] = 2;
		matriz [1][1] = 4;
		matriz [2][0] = 8;
		matriz [2][1] = 3;

		for (int renglones = 0; renglones < matriz.length ; renglones++)
		{
			for (int columnas = 0 ; columnas < matriz[0].length ; columnas ++)
			{
				System.out.printf("[%d] \t", matriz [renglones][columnas]);
			}
			System.out.println();
		}
	}
}