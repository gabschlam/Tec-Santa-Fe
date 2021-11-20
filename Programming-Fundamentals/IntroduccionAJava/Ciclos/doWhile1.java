/************************************************************************************
Ejemplo de uso de do while
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class doWhile1
{
	public static void main(String[] args)
	{
		double lado;
		String respuesta;
		Scanner lector = new Scanner(System.in);

		do
		{
			System.out.println("Dame el lado de un cuadrado para calcular su area");
			lado = lector.nextDouble();
			lector.nextLine();
			System.out.printf("El area es %.2f %n", lado*lado);

			System.out.println("Quieres hacerlo de nuevo? (si o no)");
			respuesta = lector.nextLine();
		} while(respuesta.equalsIgnoreCase("si"));

		System.out.println("¡Gracias!");
	}

}