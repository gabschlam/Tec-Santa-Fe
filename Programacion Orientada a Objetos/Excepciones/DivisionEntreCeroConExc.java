import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class DivisionEntreCeroConExc
{
	public static void main(String[] args)
	{
		Scanner lector = new Scanner(System.in);
		boolean continuarCiclo = true;

		do
		{
			try
			{
				System.out.print("Introduzca un numerador entero: ");
				int numerador = lector.nextInt();
				System.out.print("Introduzca un denominador entero: ");
				int denominador = lector.nextInt();
				int resultado = cociente(numerador, denominador);
				System.out.printf("\nResultado: %d / %d = %d\n", numerador, denominador, resultado);

				continuarCiclo = false;
			}
			catch(ArithmeticException arithmeticException)
			{
				System.err.printf("\nException: %s\n", arithmeticException); //flujo de error estandar
				System.out.printf("Cero es un denominador invalido. Intente nuevamente \n\n");
			}
			catch (InputMismatchException inputMismatchException) 
			{
				System.err.printf("\nException: %s\n", inputMismatchException);
				lector.nextLine(); //COMO OCURRIO UNA EXCEPCION, nextInt NUNCA LEYO CON EXITO, DEBEMOS LEER ESA ENTRADA COMO nextLine
				System.out.printf("Solo puede introducir enteros. Intente nuevamente \n\n");	
			}
		}while(continuarCiclo);
	}

	public static int cociente(int numerador, int denominador) throws ArithmeticException, InputMismatchException
	{
		return numerador / denominador;
	}
}


