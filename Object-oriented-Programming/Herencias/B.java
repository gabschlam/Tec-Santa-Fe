/*
	Herencias: Clase B
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class B extends C 
{
	public String toString()
	{
		return "b";
	}
	
	public void method2()
	{
		System.out.print("b 2 ");
		super.method2();
	}
}
