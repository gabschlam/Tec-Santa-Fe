/*
	Sobrecarga Métodos: Clase Main Time
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class MainTime
{
	public static void main(String[] args)
	{
		Time t1 = new Time(32, 20, 20);
		Time t2 = new Time(t1);
		Time t3 = new Time();

		t1.printTime();
		t2.printTime();

		t1.Time();
	}
}