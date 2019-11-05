/*
	Clases Genéricas: Clase Genérica
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class ClaseGenerica<T1, T2>
{
	public T1 obj1; //DECLARAR VARIABLE DE TIPO T
	public T2 obj2;

	public ClaseGenerica(T1 o1, T2 o2)
	{
		obj1 = o1;
		obj2 = o2;
	}

	public void classType()
	{
		System.out.println("El tipo de T es: " + obj1.getClass().getSimpleName() + " y el valor es: " +  obj1);
		System.out.println("El tipo de T es: " + obj2.getClass().getSimpleName() + " y el valor es: " +  obj2);
	}
}