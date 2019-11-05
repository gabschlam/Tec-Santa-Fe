/*
	Kindle: Main Kindle
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class MainKindle
{
	public static void main(String[] args)
	{
		Kindle kindle = new Kindle();

		Libro libro1 = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes Saavedra", 1111);
		Libro libro2 = new Libro("Milagro en los Andes", "Nando Parrado", 280);
		Libro libro3 = new Libro("Cálculo de una Variable", "James Stewart", 954);
		Libro libro4 = new Libro("Lord of the flies", "William Golding", 208);

		kindle.addLibro(libro1);
		kindle.addLibro(libro2);
		kindle.addLibro(libro3);
		kindle.addLibro(libro4);
		
		/*kindle.leerLibro("Diario de Ana Frank");
		kindle.leerLibro("Don Quijote de la Mancha");
		
		kindle.delLibro("Milagro en los Andes");
		
		kindle.printUltimo();*/
		kindle.printLibros();
	}
}
