/************************************************************************************
Ejemplo de uso de for
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class For1
{
	public static void main(String[] args)
	{
		int cont;
		for(cont=1; cont<=10; cont++) //CUANDO DEFINES CONTADOR CON "INT" DENTRO DEL FOR, NO SIRVE PARA FUERA DEL FOR
		{
			System.out.println(cont);
		}
	}
}