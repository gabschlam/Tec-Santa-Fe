class sistemaRH
{
	public static void main(String[] args)
	{
		Empleado emp1 = new Empleado();
		Empleado emp2 = new Empleado();
		Empleado[] empleados = new Empleado[10];

		empleados[0] = new Empleado();
		empleados[0].nombre = "Maria";

		emp1.nombre = "Gabriel";
		emp1.salario = 230000.50;

		if (!emp1.setPuesto("Estudiante"))
		{
			System.out.println("Error de asignacion");
		}


		emp2.nombre = "Paco";
		emp2.setPuesto("Profesor");

		emp1.imprimeDatos();
		emp2.imprimeDatos();

		System.out.println("El puesto de empleado 2 es " + emp2.getPuesto());
	}
}