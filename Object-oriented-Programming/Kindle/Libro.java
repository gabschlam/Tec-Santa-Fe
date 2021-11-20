/*
	Kindle: Clase Libro
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class Libro
{
	private String titulo;
	private String autor;
	private int paginas;

	public Libro()
	{
		
	}
	
	public Libro(String titulo, String autor, int paginas)
	{
		this.titulo = titulo;
		this.autor = autor;
		this.paginas = paginas;
	}
	
	public String getTitulo()
	{
		return titulo;
	}
	
	public String getAutor()
	{
		return autor;
	}
	
	public int getPaginas()
	{
		return paginas;
	}
}
