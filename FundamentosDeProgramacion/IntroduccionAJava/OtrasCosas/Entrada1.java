import java.util.Scanner; // este codigo es escencial para leer del teclado

public class Entrada1
{
	public static void main (String[] args)
	{
		String nombre;

		Scanner stdIn = new Scanner(System.in); // este codigo es escencial para leer del teclado

		System.out.println("¿Como te llamas?");

		nombre = stdIn.nextLine(); // esta funcion lee una frase escrita por el teclado

		System.out.println("Hola " + nombre);

	}
}