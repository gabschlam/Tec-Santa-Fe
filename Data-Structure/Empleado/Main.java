public class Main
{
	public static void main(String [] args)
	{
		Salario s1 = new Salario(4, 20000, 0.89, 320);
		Empleado e1 = new Empleado("Maria de los Angeles", 1234, s1);
		e1.imprimirNomina();

		Salario s2 = new Salario(10, 40000, 0.92, 345);
		Empleado e2 = new Empleado("Elvis Gonzalez", 1345, s2);
		e2.imprimirNomina();

	}
}	