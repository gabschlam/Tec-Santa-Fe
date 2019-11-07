/************************************************************************************
Ejemplo de uso de condiciones con switch
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class Condiciones2
{
	public static void main(String[] args)
	{
		Scanner lector = new Scanner(System.in);
		int opcion;


		System.out.println("¿En que tarifa quieres viajar? (1-5)");
		System.out.println("1 = Economica");
		System.out.println("2 = Clasica");
		System.out.println("3 = Flexible");
		System.out.println("4 = Business Class");
		System.out.println("5 = First Class");
		opcion = lector.nextInt();

		switch(opcion)
		{
			case 1:
				System.out.println("Tu tarifa es $15,279.00");
				break;
			case 2:
				System.out.println("Tu tarifa es $15,459.00");
				break;
			case 3:
				System.out.println("Tu tarifa es $31,337.00");
				break;
			case 4:
				System.out.println("Tu tarifa es $45,233.00");
				break;
			case 5:
				System.out.println("Tu tarifa es $82,916.00");
				break;
			default:
				System.out.println("Esa opcion no es valida");
		}
	}
}