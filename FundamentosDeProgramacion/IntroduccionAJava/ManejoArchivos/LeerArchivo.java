import java.io.*;

public class LeerArchivo
{
	public static void main (String[] args) throws IOException
	{
		String nombreArchivo = "ejemplo2.csv";
		String datosLeidos;

		FileReader lector = new FileReader(nombreArchivo);

		BufferedReader br = new BufferedReader(lector);

		while ( (datosLeidos = br.readLine()) != null)
		{
			System.out.println(datosLeidos);
		}

		lector.close();

	}
}