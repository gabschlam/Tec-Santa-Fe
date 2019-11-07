import java.util.Scanner;
public class FuncionRecursiva3
{
	/*
	PROGRAMA QUE MUESTRA UN CONTEO DEL 1 AL 100 CON
	FUNCION RECURSIVA
	*/

	public static void main(String[] args)
	{
		int num1 = 0;
		int num2 = 0;

		Scanner lector = new Scanner(System.in);

		System.out.println("De que numero quieres");
		num1 = lector.nextInt();
		System.out.println("Hasta que numero quieres");
		num2 = lector.nextInt();

		System.out.println("El conteo es: ");
		conteoRecursivo(num1, num2);
		System.out.println();
	}

	public static void conteoRecursivo(int num1, int lim)
	{
		if (lim < num1)
			return;
		else
		{
			conteoRecursivo(num1,(lim-1)); //LLAMA DE NUEVO AL METODO HASTA QUE LLEGUE AL UNO Y SUBA OTRA VEZ
			System.out.printf("%d\t", lim);
			return;
		}
	}
}