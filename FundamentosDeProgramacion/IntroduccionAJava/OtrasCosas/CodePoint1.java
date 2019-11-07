public class CodePoint1
{
	public static void main (String[] args)
	{
		String str;
		int val1;

		str = "Niño 𝒫";
		//val1 = str.codePointAt(2); EL CODEPOINT ES LA POSICION DE LA LETRA EN UNICODE

		System.out.printf("El CodePoint es %d\n", str.codePointAt(2)); //EL CODEPOINT ES LA POSICION DE LA LETRA EN UNICODE
		
		System.out.printf("El CodePoint es %d\n", str.codePointAt(1));

		System.out.printf("El CodePoint es %d\n", str.codePointAt(5));

		System.out.println(str);

		System.out.printf("El char es %s\n", str.charAt(2));

	}
}