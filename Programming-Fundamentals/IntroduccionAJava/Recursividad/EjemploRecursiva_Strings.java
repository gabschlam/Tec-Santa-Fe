public class EjemploRecursiva_Strings
{
	public static void main(String[] args)
	{
		String nombre = "Gabriel";

		palabras(nombre);
		System.out.println();
	}

	public static void palabras(String nom)
	{
		if (nom.length()<1) 
		{
			return;
		}
		else
		{
			palabras(nom.substring(0, nom.length()-1));
			System.out.printf("%s\t", nom);
		}
	}
}
