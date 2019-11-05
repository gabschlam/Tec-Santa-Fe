/********************************************************************
Este programa fue realizado por Luis Revilla, Simon Metta y Gabriel
Schlam.

EL programa lee una imagen introducida por el usuario pixel por pixel
conociendo su valor RGB y sustituyendo este mismo en un archivo de
texto por caracteres ASCII, mostrando la imagen con caracteres.
|
|
|
MacBook-Pro-de-Simon:Programacion simonmetta$
Imagen creada
MacBook-Pro-de-Simon:Programacion simonmetta$ 
********************************************************************/
import java.awt.Color; //libreria de conocer colores
import java.awt.image.BufferedImage; //libreria de analizar imagenes
import java.io.File; //libreria para leer archivos
import java.io.FileWriter; //libreria para escrivir archivos
import java.io.IOException; //libreria de ecepciones
import java.io.PrintWriter; //libreria para escrivir archivos
import javax.imageio.ImageIO; //libreria de leer imagenes

public class Main
{
	public static void main(String[] args)
    {
        ASCII imagen = new ASCII();//objeto de clase ASCII

        imagen.conversion("AMA.jpg");//definicion de imagen a convertir

        System.out.println("Imagen creada");
    }
}