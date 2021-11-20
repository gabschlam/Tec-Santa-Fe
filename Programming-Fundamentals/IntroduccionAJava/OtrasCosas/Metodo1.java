/************************************************************************************
Ejemplo de uso de métodos
Por Gabriel Schlam
************************************************************************************/

public class Metodo1
{
	public static void main(String[] args)
	{
		String nombre = "Gabriel";
		String otroNombre = "Pedro";

		imprime(nombre);

		System.out.println("Entre llamadas al metodo");

		imprime(otroNombre);
	}

	public static void imprime(String nom)
	{
		System.out.printf("¡Hola %s desde el metodo!%n", nom);
		//Cuando pones nom o cualquier otra variable, imprime un string antes estalecido

	}

}

