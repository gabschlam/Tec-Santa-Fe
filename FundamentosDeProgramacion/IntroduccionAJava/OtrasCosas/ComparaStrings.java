public class ComparaStrings
{
	public static void main (String[] args)
	{
		String str1 = "arbol";
		String str2 = "barco";
		String str3 = "arboleda";

		int comparacion;

		comparacion = str1.compareTo(str2);

		System.out.printf("Comparacion 1 con 2: %d %n", comparacion);

		comparacion = str1.compareTo(str3);

		System.out.printf("Comparacion 1 con 3: %d %n", comparacion);

		comparacion = str3.compareTo(str2);

		System.out.printf("Comparacion 3 con 2: %d %n", comparacion);

	}
}