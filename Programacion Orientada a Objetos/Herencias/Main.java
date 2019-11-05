public class Main 
{
	public static void main(String[] args)
	{
		A a = new A();

		C[] elements = {new A(), new B(), new C(), new D()};
		for (int i = 0; i < elements.length; i++)
		{
			System.out.println(a);
			elements[i].method1();
			System.out.println();
			elements[i].method2();
			System.out.println();
			System.out.println();
		}
	}

	/*
	b -> primer metodo que regresa String de la cadena de clases (A -> B - toString)
	c 1 -> primer method1 de elemento A() (A -> B -> C - method1)
	a 2 c 1 b 2 c 2 -> 
	method2  elemento A() (imprime a 2, se va al method1 del C (imprime c 1), 
	super (primer metodo de sig clase (imprime b2)), 
	super (primer metodo de sig clase (imprime c2))
								
	b -> primer metodo que regresa String de la cadena de clases (A -> B - toString)
	c 1 -> primer method1 de elemento B() (B -> C - method1)
	b 2 c 2 -> method2 de B() (imprime b2), super.method2() (imprime c2)

	b -> primer metodo que regresa String de la cadena de clases (A -> B - toString)
	c 1 -> method1 de elemento C()
	c 2 -> method2 de elemento C()

	b -> primer metodo que regresa String de la cadena de clases (A -> B - toString)
	d 1 b 2 c 2 ....
	b 2 c 2 ....
	*/
}
