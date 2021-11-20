public class ejemploVariables
{
	public static void main(String[] args)
	{
		int num1;
		int num2;

		num1 = Integer.parseInt(args[0]);
		num2 = Integer.parseInt(args[1]);


		System.out.println("La suma es " + (num1 + num2) );
		System.out.println("La resta es " + (num1 - num2) );
		System.out.println("La multiplicacion es " + (num1 * num2) );
		//'cast' a un numero entero (convertirlo de entero a real)
		System.out.println("La division es " + ( (double)num1 / (double)num2) );
		System.out.println("El modulo es " + (num1 % num2) );
	}
}