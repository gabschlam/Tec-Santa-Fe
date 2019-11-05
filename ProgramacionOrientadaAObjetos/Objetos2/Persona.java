/*
	Objetos: Persona
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class Persona
{
	private String nombre;
	private String apellido;
	private int edad;
	private String alias;

	// ESTO SE LLAMA CONSTRUCTOR
	public Persona()
	{
		nombre = "--"; //DEFINES POR DEFAULT LAS VARIABLES;
		apellido = "--"; //DEFINES POR DEFAULT LAS VARIABLES;
		edad = 0; //DEFINES POR DEFAULT LAS VARIABLES;
		alias = "--"; //DEFINES POR DEFAULT LAS VARIABLES;		
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre; //this. PARA NOMBRE DE LA VARIABLE DE LA CLASE
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public void setEdad(int edad)
	{
		this.edad = edad;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getNombre()
	{
		return nombre;
	}

	public String getApellido()
	{
		return apellido;
	}

	public int getEdad()
	{
		return edad;
	}

	public String getAlias()
	{
		return alias;
	}

	public void printPersona()
	{
		System.out.printf("Persona: %s %s %s \n", alias, nombre, apellido);
		System.out.println("Edad: " + edad + " años");
	}	
}