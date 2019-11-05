/*
	Excepciones: Calculadora Fracciones
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CalculadoraFracciones
{
	public static void main(String[] args)
	{
		Scanner lector = new Scanner(System.in);
		boolean continuarCiclo = true;

		//Fraccion fraccion = new Fraccion(-4, -5);
		//Fraccion fraccion2 = new Fraccion(6, 5);

		do
		{
			System.out.println("Escoge la operacion deseada:");
			System.out.println("1 = SUMA DE FRACCIONES");
			System.out.println("2 = RESTA DE FRACCIONES");
			System.out.println("3 = MULTIPLICACION DE FRACCIONES");
			System.out.println("4 = DIVISION DE FRACCIONES");
			System.out.println("5 = SIMPLIFICAR FRACCION");
			int resp = lector.nextInt();

			switch(resp)
			{
				case 1:
				{
					try
					{
					System.out.print("Introduzca el numerador de la primera fraccion: ");
					int numerador1 = lector.nextInt();
					System.out.print("Introduzca el denominador de la primera fraccion: ");
					int denominador1 = lector.nextInt();
					Fraccion fraccion1 = new Fraccion(numerador1, denominador1);

					System.out.print("Introduzca el numerador de la segunda fraccion: ");
					int numerador2 = lector.nextInt();
					System.out.print("Introduzca el denominador de la segunda fraccion: ");
					int denominador2 = lector.nextInt();
					Fraccion fraccion2 = new Fraccion(numerador2, denominador2);

					Fraccion res = fraccion1.add(fraccion2);

					System.out.println("El resultado de la suma de fracciones es: " + res.toString());

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
					break;
				}
				case 2:
				{
					try
					{
					System.out.print("Introduzca el numerador de la primera fraccion: ");
					int numerador1 = lector.nextInt();
					System.out.print("Introduzca el denominador de la primera fraccion: ");
					int denominador1 = lector.nextInt();
					Fraccion fraccion1 = new Fraccion(numerador1, denominador1);

					System.out.print("Introduzca el numerador de la segunda fraccion: ");
					int numerador2 = lector.nextInt();
					System.out.print("Introduzca el denominador de la segunda fraccion: ");
					int denominador2 = lector.nextInt();
					Fraccion fraccion2 = new Fraccion(numerador2, denominador2);
					
					Fraccion res = fraccion1.subtract(fraccion2);

					System.out.println("El resultado de la resta de fracciones es: " + res.toString());

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
					break;
				}
				case 3:
				{
					try
					{
					System.out.print("Introduzca el numerador de la primera fraccion: ");
					int numerador1 = lector.nextInt();
					System.out.print("Introduzca el denominador de la primera fraccion: ");
					int denominador1 = lector.nextInt();
					Fraccion fraccion1 = new Fraccion(numerador1, denominador1);

					System.out.print("Introduzca el numerador de la segunda fraccion: ");
					int numerador2 = lector.nextInt();
					System.out.print("Introduzca el denominador de la segunda fraccion: ");
					int denominador2 = lector.nextInt();
					Fraccion fraccion2 = new Fraccion(numerador2, denominador2);
					
					Fraccion res = fraccion1.multiply(fraccion2);

					System.out.println("El resultado de la multiplicacion de fracciones es: " + res.toString());
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
					break;
				}
				case 4:
				{
					try
					{
					System.out.print("Introduzca el numerador de la primera fraccion: ");
					int numerador1 = lector.nextInt();
					System.out.print("Introduzca el denominador de la primera fraccion: ");
					int denominador1 = lector.nextInt();
					Fraccion fraccion1 = new Fraccion(numerador1, denominador1);

					System.out.print("Introduzca el numerador de la segunda fraccion: ");
					int numerador2 = lector.nextInt();
					System.out.print("Introduzca el denominador de la segunda fraccion: ");
					int denominador2 = lector.nextInt();
					Fraccion fraccion2 = new Fraccion(numerador2, denominador2);
					
					Fraccion res = fraccion1.divide(fraccion2);

					System.out.println("El resultado de la division de fracciones es: " + res.toString());
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
					break;
				}
				case 5:
				{
					System.out.print("Introduzca el numerador de la fraccion: ");
					int numerador = lector.nextInt();
					System.out.print("Introduzca el denominador de la fraccion: ");
					int denominador = lector.nextInt();
					Fraccion fraccion = new Fraccion(numerador, denominador);
					
					System.out.println("El resultado de la simplificacion de la fraccion es: " + (fraccion.toLowestTerms()).toString());
					continuarCiclo = false;
					break;
				}
			}
		}while(continuarCiclo);

	}
}