import java.io.*;

public class EscribirArchivo
{
	public static void main (String[] args) throws IOException
	{
		File archivo;

		archivo = new File("ejemplo1.txt");

		archivo.createNewFile();

		FileWriter escritor = new FileWriter(archivo, true);

		/* NO USAR ESTO
		escritor.write("Ejemplo de archivo.\nSalto de linea.%nOtra Linea")
		*/

		PrintWriter pw = new PrintWriter(escritor);

		pw.printf("ejemplo de archivo con formato. %nNumero %d", 1218);

		escritor.close();
	}
}