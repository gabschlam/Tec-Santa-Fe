import java.util.Random;

public class ArreglosBidimensionales3
{
	public static void main(String[] args)
	{
		Random aleatorio = new Random(); 

		int suma, acum, posi = 0, posj = 0;

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

		acum = matriz[0][0] + matriz[1][0] + matriz[0][1] + matriz[1][1];

		for (int i = 0; i < (matriz.length - 1 ); i++)
		{
			for (int j = 0 ; j < (matriz[0].length - 1) ; j++)
			{
				suma = matriz[i][j] + matriz[i+1][j] + matriz[i][j+1] + matriz[i+1][j+1];
				
				if (suma > acum) 
				{
					acum = suma;
					posi = i;
					posj = j;
				}
			}
		}
		System.out.printf("La suma mayor es de: %d \n", acum);
		System.out.printf("La posicion es: (%d, %d) \n", posi, posj);
	}
}