public class Empleado
{
	private String nombre;
	private int numeroEmpleado;
	private Salario salario;
	
	public Empleado(String nombre, int numeroEmpleado, Salario salario)
	{
		this.nombre = nombre;
		this.numeroEmpleado = numeroEmpleado;
		this.salario = salario;
	}

	public String getNombre()
	{
		return nombre;
	}

	public int getNumeroEmpleado()
	{
		return numeroEmpleado;
	}

	public Salario getSalario()
	{
		return salario;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setNumeroEmpleado(int numeroEmpleado)
	{
		this.numeroEmpleado = numeroEmpleado;
	}

	public void setSalario(Salario salario)
	{
		this.salario = salario;
	}

	public void imprimirNomina()
	{
		System.out.println("-------------------------------------------------------");
		System.out.println("Empleado No.: " + numeroEmpleado);
		System.out.println(nombre);
		System.out.println("Salario Neto: " + salario.salarioTotal());
		System.out.printf("Total de Retenciones: %.2f\n", salario.retenciones());
		System.out.println("-------------------------------------------------------");
	}
}