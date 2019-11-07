public class Variables1
{
	public static void main (String[] args)
	{
		//Declarar variables:
		int num1;
		double num2, num3 = 0;

		double pi = 3.14;

		final double velLuz = 299792458.0; //final double = no se puede cambiar despues

		num3++; //Operacion unaria
		++num3; //Operacion unaria

		num1 = (int)pi; //redondea pi con CAST

		num2 = (num1+num3) / 2 ;

		num2 += num1; //es igual a num2 = num2+num1

		System.out.println(num1);
		System.out.println(num2);
		System.out.println(Math.sqrt(num2++));
		//Hizo raiz de 5.5 (num2 antes de sumarle 1 por el ++)
		//Despues sumo 1 al acabar la linea y se imprime abajo
		System.out.println(num2);


	}
}