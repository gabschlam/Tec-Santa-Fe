/*
	Polimorfismo: Clase Hard
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

import java.util.Scanner;

public class hard
{
	public static void main(String[] args)
	{
		Object[] arr = new Object[2];

		arr[0] = new Object[2];
		arr[1] = new Integer(1);

		System.out.println(arr[0].getClass()==Integer.class?"Integer":"Array"); //IF EN UNA MISMA LINEA
		System.out.println(arr[1].getClass()==Integer.class?"Integer":"Array"); //IF EN UNA MISMA LINEA

		/*IGUAL QUE EL IF DE ARRIBA
		if (arr[0].getClass()==Integer.class) 
			System.out.println("Integer");
		else
			System.out.println("Array");
		
		if (arr[1].getClass()==Integer.class) 
			System.out.println("Integer");
		else
			System.out.println("Array");
		*/
	}
}