/************************************************************************************
Ejemplo de uso de for
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class For4
{
	public static void main(String[] args)
	{
		for(int i=0; i<=11; i++)
		{
			for(int j=0; j<=11; j++)
			{
				System.out.printf("%d \t ", i*j);
			}
			System.out.println();
		}
	}
}