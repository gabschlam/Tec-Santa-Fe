import java.util.Random; // PARA IMPORTAR NUMEROS RANDOM

public class Aleatorios
{
	public static void main(String[] args)
	{
		Random numerosAleatorios = new Random(); 
		int num, num2;

		for (int i = 1; i<=5 ; i++) 
		{
			num = numerosAleatorios.nextInt(10)+1; // DENTRO DEL NEXTINT SE PONE EL NUMERO PARA DELIMITAR, CON +1 FUERA DEL () VA DEL 0 AL 10
			num2 = numerosAleatorios.nextInt(1000)+20; // PARA DEL -10 AL 10, PORQUE CON EL 21 ES DEL 0 AL 20, CON -10 SE BAJA 10 AL RANGO
			System.out.println("El numero es: " + num2);
		}

		double numeroDouble;
		for (int i = 1; i<=5 ; i++) 
		{
			numeroDouble = numerosAleatorios.nextDouble()*10; //SIN NUMERO EN () ES DEL 0 AL 1, PARA MAS RANGO SE MULTIPLICA FUERA DEL ()
			System.out.printf("El numero es: %.2f %n", numeroDouble);
		}
	}
}