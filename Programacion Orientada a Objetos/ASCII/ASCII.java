import java.awt.Color; //libreria de conocer colores
import java.awt.image.BufferedImage; //libreria de analizar imagenes
import java.io.File; //libreria para leer archivos
import java.io.FileWriter; //libreria para escrivir archivos
import java.io.IOException; //libreria de ecepciones
import java.io.PrintWriter; //libreria para escrivir archivos
import javax.imageio.ImageIO; //libreria de leer imagenes

public class ASCII
{
	private BufferedImage img; //variable de imagen
	private double pixval; //variable del valor del pixel
	private PrintWriter pwt; //escritor
	private FileWriter fwt; //escritor

	public ASCII()
	{
		try
		{
			pwt = new PrintWriter(fwt = new FileWriter("ASCII.txt", true)); //se crea el escritor de archivo
		}
		catch (IOException ex){}
	}

	public void conversion(String nombre)
	{
		try
		{
			img = ImageIO.read(new File(nombre)); //lee el nombre de la imagen, conociendo cual utilizar
		}
		catch (IOException e){}

		for (int i = 0; i < img.getHeight(); i++) //columna de pixeles
		{
			for (int j = 0; j < img.getWidth(); j++) //fila de pixeles
			{
				Color pixcol = new Color(img.getRGB(j, i)); //consigue el valor RGB del pixel
				pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol.getGreen() * 0.11)));
				print(asciiChar(pixval)); //imprime en el archivo el caracter dependiendo de valor RGB
				if(j == img.getWidth() - 1)
				{
					pwt.println(""); //salto de linea al final de la fila de pixeles
				}
			}
			try
			{
				pwt.flush();
				fwt.flush();
			}
			catch (Exception ex){}
		}
	}

	public String asciiChar(double x) //con valor RGB conoce por que caracter sustituir en archivo
	{
		String str = " ";
		if (x >= 240)
		{
			str = " ";
		}
		else if (x >= 210)
		{
			str = ".";
		}
		else if (x >= 190)
		{
			str = "*";
		}
		else if (x >= 170)
		{
			str = "+";
		}
		else if (x >= 120)
		{
			str = "^";
		}
		else if (x >= 110)
		{
			str = "&";
		}
		else if (x >= 80)
		{
			str = "8";
		}
		else if (x >= 60)
		{
			str = "#";
		}
		else
		{
			str = "@";
		}
		return str;
	}

	public void print(String str) //imprime el caracter ASCII en el archivo
	{
		try
		{
			pwt.print(str);
			pwt.flush();
			fwt.flush();
		}
		catch (Exception ex){}
	}
}