/************************************************************************************
Ejemplo de uso de condiciones
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class Condiciones1
{
	public static void main(String[] args)
	{
		int temperatura;
		String salida;
		Scanner stdIn = new Scanner(System.in);

		System.out.println("Escribe la temperatura del auto");

		temperatura = stdIn.nextInt();

		/*********************
		or se escribe ||
		and se escribe &&
		diferente se escribe !=
		*********************/

		if (temperatura >= 110)
		{
			System.out.println("Temperatura muy alta. Por favor detente");
			System.out.println("Revisa el nivel y temperatura de aceite");
		}
		else if (temperatura >= 10)
		{
			System.out.println("Todo OK. Puedes continuar");
			System.out.println("¡Buen viaje!");
		}
		else
		{
			System.out.println("Temperatura muy baja. Por favor detente");
			System.out.println("Revisa el nivel y temperatura de aceite");
		}
		/**************************************************************
		SI HAY MAS DE UNA LINEA EN EL IF O ELSE, SE TIENEN QUE PONER {}
		**************************************************************/

		stdIn.nextLine();
		System.out.println("Escribe \"s\" para salir");
		salida = stdIn.nextLine();
		if (salida.equalsIgnoreCase ("s"))
		/***********************************************************************************
		para comparar Strings no se pone ==, sino variable.equals (salida.equals)
		para que no importe la mayuscula de la comparacion se pone variable.equalsIgnoreCase
		***********************************************************************************/
		{
			System.out.println("¡Adios!");
		}
	}
}