	import java.util.Scanner;

public class fibonacci
{
	public static void main(String[] args)
	{
		int n;
		Scanner lector = new Scanner(System.in);

		System.out.println("Escribe hasta que posicion quieres");
		n = lector.nextInt();

		for(int i = 0; i<n; i++)
		{
			System.out.print(fibonacci(i) + ", ");
		}
		System.out.println();
	}

	public static int fibonacci(int num)
	{
		if(num == 0 || num == 1)
			return num;
		else
			return fibonacci(num-1) + fibonacci(num-2);
 
	}
}	