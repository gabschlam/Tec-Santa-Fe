public class Main
{
	public static void main(String args[])
	{
		ClaseGenerica<Integer, String> obj = new ClaseGenerica<Integer, String>(88, "Hola");
		obj.classType();

		//ClaseGenerica<String> strObj = new ClaseGenerica<String>("Hola");
		//strObj.classType();
	}
}