public class MainStudent
{
	public static void main(String[] args)
	{
		Student s1 = new Student("Gabriel Schlam", 42342);
		
		s1.printStudent();

		System.out.println("Numero de unidades al inicio de semestre: " + s1.getUnits());

		s1.incrementUnits(200);

		System.out.println("Numero de unidades al inicio de semestre: " + s1.getUnits());

	}
}