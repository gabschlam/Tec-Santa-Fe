import java.util.Scanner;

public class DivisionEntreCeroSinExc
{
	public static void main(String[] args)
	{
		Scanner lector = new Scanner(System.in);
		
		System.out.print("Introduzca un numerador entero: ");
		int numerador = lector.nextInt();
		System.out.print("Introduzca un denominador entero: ");
		int denominador = lector.nextInt();
		
		int resultado = cociente(numerador, denominador);
		System.out.printf("\nResultado: %d / %d = %d\n", numerador, denominador, resultado);
	}

	public static int cociente(int numerador, int denominador)
	{
		return numerador / denominador;
	}
	
}