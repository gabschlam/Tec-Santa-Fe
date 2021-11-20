import java.util.Scanner;

public class Arreglos2
{
	public static void main(String[] args)
	{
		int num, cambio;
		double[] arreglo;
		String resp;
		
		Scanner lector = new Scanner(System.in);

		System.out.println("Dame numero de calificaciones");

		num = lector.nextInt();

		arreglo = new double[num];

		
		for(int i=0; i<arreglo.length; i++)
		{
			System.out.println("Dame la calificacion " + (i+1));
			arreglo[i] = lector.nextDouble();
		}

		muestraArreglo(arreglo);

		System.out.println("Quieres cambiar alguna calificacion?");
		lector.nextLine();
		resp = lector.nextLine();

		if (resp.equalsIgnoreCase ("si")) 
		{
			System.out.println("Que calificacion quieres cambiar?");
			cambio = lector.nextInt();
			System.out.println("Dame la calificacion");
			arreglo[cambio-1] = lector.nextInt();
			muestraArreglo(arreglo);
		}
	}

	public static void muestraArreglo(double[] arreglo)
	{
		double total = 0, prom = 0;
		for(int j=0; j<arreglo.length; j++)
			{
				System.out.println("Tus calificaciones son " + arreglo[j]);
				total = total + arreglo[j];
			}
		prom = total / arreglo.length;
		System.out.printf("El promedio es de: %.2f%n", prom);
	}
}