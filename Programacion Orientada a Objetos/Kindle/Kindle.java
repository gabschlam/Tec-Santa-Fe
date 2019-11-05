import java.util.ArrayList;

public class Kindle
{
	private ArrayList<Libro> kindle = new ArrayList<Libro>();
	private Libro libro;
	
	public Kindle()
	{
		
	}
	
	public void addLibro(Libro libro)
	{
		kindle.add(0, libro);
	}
	
	public void delLibro(String titulo)
	{
		for(int i = 0; i < kindle.size(); i++)
		{
			if(kindle.get(i).getTitulo().equals(titulo))
			{
				kindle.remove(i);
			}
		}
	}
	
	public void printLibros()
	{
		for(int i = 0; i < kindle.size(); i++)
		{
			System.out.println("Libro " + (i+1) + ":" + kindle.get(i).getTitulo() + "," + kindle.get(i).getAutor() + "," + kindle.get(i).getPaginas());
		}
	}
	
	public void leerLibro(String titulo)
	{
		Libro libro;
		int error = 0;
		
		for(int i = 0; i < kindle.size(); i++)
		{
			if(kindle.get(i).getTitulo().equals(titulo))
			{
				libro = kindle.get(i);
				kindle.add(0, libro);
				kindle.remove(i+1);
				System.out.println("El usuario esta leyendo el libro: " + kindle.get(0).getTitulo());
			}
			else
				error = 1;
		}
		
		if(error == 1)
		{
			System.out.println("Este libro no existe");
		}
		
	}
	
	public void printUltimo()
	{
		System.out.println("El libro leido por ultima vez es: " + kindle.get(0).getTitulo());
	}
	
}
