/*
	Tipo de datos enum: Enum Test
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class EnumTest
{
	Dia dia;

	public EnumTest(Dia dia)
	{
		this.dia = dia;
	}

	public void print()
	{
		switch(dia)
		{
			case LUNES:
				System.out.println("Que dia tan feo!");
				break;
			case VIERNES:
				System.out.println("Mejoro la semana");
				break;
			case SABADO: case DOMINGO:
				System.out.println("Los fines de semana son lo mejor");
				break;
			default:
				System.out.println("Urge que sea viernes!");
		}
	}
}