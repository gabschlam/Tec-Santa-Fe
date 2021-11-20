public class Empleado
{
	public String nombre;
	public double salario;

	private String puesto = "";

	public void imprimeDatos()
	{
		System.out.println("Nombre:  " + this.nombre);
		System.out.println("Salario: " + this.salario);
		System.out.println("Puesto:  " + this.puesto);
	}

	public boolean setPuesto(String puesto)
	{
		if (puesto.equals("Alumno") || puesto.equals("Profesor")) 
		{
			this.puesto = puesto;
			return true;
		}
		else
			return false;
	}

	public String getPuesto()
	{
		return puesto;
	}
}