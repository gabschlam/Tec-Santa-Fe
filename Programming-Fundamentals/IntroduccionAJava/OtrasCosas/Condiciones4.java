/************************************************************************************
Ejemplo de uso de condiciones con switch
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class Condiciones4
{
	public static void main(String[] args)
	{
		Scanner lector = new Scanner(System.in);
		String zip;


		System.out.println("Escribe tu codigo postal");
		zip = lector.nextLine();
		char codigo = zip.charAt(0);

		switch(codigo)
		{
			case '0':
			case '2':
			case '3':
				System.out.println("Region este");
				break;
			case '4':
			case '5':
			case '6':
				System.out.println("Region centro");
				break;
			case '7':
				System.out.println("Region sur");
				break;
			case '8':
			case '9':
				System.out.println("Region este");
				break;
			default:
				System.out.println("Opcion no valida");
		}
	}
}
