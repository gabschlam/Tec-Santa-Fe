/*
	Objetos: Main Cliente
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class MainCliente
{
	public static void main(String[] args)
	{
		Persona cliente1 = new Persona();
		Persona cliente2 = new Persona();


		//cliente1.nombre = "" - ERROR

		cliente1.printPersona(); // AQUI SE IMPRIMEN LOS VALORES POR DEFAULT

		cliente1.setAlias("Sr.");
		cliente1.setNombre("Gabriel");
		cliente1.setApellido("Schlam");
		cliente1.setEdad(20);

		cliente1.printPersona();

		cliente2.setAlias("Sr.");
		cliente2.setNombre("Jose Maria de los Angeles");
		cliente2.setApellido("Jimenez");
		cliente2.setEdad(180);

		cliente2.printPersona();
	}
}