public class Variables2
{
	public static void main (String[] args)
	{

		char inicialNombre, inicialApellido;
		char codigoControl;

		inicialNombre = 'G';
		inicialApellido = 'S';

		codigoControl = '\n';
		// "\n" es salto de linea, "\r" es tab en la misma linea
		//"\r" empieza a escribir lo que sigue al principio de la misma linea, borrando lo que habia

		String texto = "Mi nombre es ";

		System.out.println(" Hola " + inicialNombre + inicialApellido);
		System.out.println(" Codigo: " + codigoControl);
		System.out.println("    \"Hola de nuevo\" \r " + inicialNombre + inicialApellido);

		System.out.println("Los caracteres de texto son " +texto.length());
		// texto.length = cuenta el numero de caracteres de la variable
		System.out.println("El caracter en 10 es " + texto.charAt(10));
		// texto.charAt = imprime el numero de caracter que se pone de la variable
		//(si se ponen mas caracteres de los que hay marca error)
	}
}