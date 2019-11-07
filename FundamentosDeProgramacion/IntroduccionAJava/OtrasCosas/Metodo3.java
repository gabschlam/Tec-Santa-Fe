/************************************************************************************
Ejemplo de uso de métodos leyendo desde el teclado usando cuadratica
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class Metodo3
{
	public static void main(String[] args)
	{
		double numero1 = cuadrado(5.0);
		System.out.printf("El cuadrado de %f es %f %n", 4.0, cuadrado(4.0));
		System.out.printf("%.2f %n", numero1);
		imprimeHola();
	}

	public static double cuadrado(double num)
	{
		double cuadrado;
		cuadrado = num*num;
		return cuadrado;
	}

	public static void imprimeHola()
	{
		System.out.printf("Hola a todos");
	}
}