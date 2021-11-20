/************************************************************************************
Ejemplo de uso de for
Por Gabriel Schlam
************************************************************************************/

import java.util.Scanner;

public class For3
{
	public static void main(String[] args)
	{
		for(int num=1; num<=40; num++)
		{
			System.out.print(num);
			if ((num%2==0)&&(num%3==0))
			{
				System.out.print(" Es par y es divisible entre 3");
			}
			else if (num%2==0)
			{
				System.out.print(" Es par");
			}
			else if (num%3==0)
			{
				System.out.print(" Es divisble entre 3");
			}
			System.out.println();
		}
	}
}