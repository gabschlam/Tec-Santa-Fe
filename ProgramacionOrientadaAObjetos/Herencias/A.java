/*
	Herencias: Clase A
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class A extends B
{
	public void method2()
	{
		System.out.print("a 2 ");
		method1();
		super.method2();
	}
}
