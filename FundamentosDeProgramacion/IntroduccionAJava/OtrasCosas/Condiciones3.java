/************************************************************************************
Ejemplo de uso de condiciones para buscar numero primo con switch
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class Condiciones3
{
	public static void main(String[] args)
	{
		Scanner lector = new Scanner(System.in);
		int opcion;


		System.out.println("Escribe que numero quieres verificar");
		opcion = lector.nextInt();

		switch(opcion)
		{
			/*******************************
			SE PUEDE PONER MAS DE UN CASE
			POR INSTRUCCION, SIN PONER BREAK
			********************************/
			case 2:
			case 3:
			case 5:
			case 7:
				System.out.println("El numero es primo");
				break;
			case 0:
			case 1:
			case 4:
			case 6:
			case 8:
			case 9:
			case 10:
				System.out.println("El numero no es primo");
				break;
			default:
				System.out.println("Esa opcion no es valida");
		}
	}
}