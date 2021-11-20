/*
    Algoritmos de Ordenamiento: Inserción - Main
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        
        long startTime = System.nanoTime();
        
        Main m = new Main();
        Persona[] lp = m.inicializaLista(1000);
        System.out.println("Ordenamiento por Fecha de Nacimiento");
        Insercion.insercionFNacimiento(lp);
        m.imprimeLista(lp);
        /*System.out.println("Ordenamiento por Nombre");
        Insercion.insercionNombre(lp);
        m.imprimeLista(lp);
        System.out.println("Ordenamiento por Apellido Paterno");
        Insercion.insercionaPaterno(lp);
        m.imprimeLista(lp); 
        System.out.println("Ordenamiento por Apellido Materno");
        Insercion.insercionaMaterno(lp);
        m.imprimeLista(lp);*/
        
        long endTime = System.nanoTime();
 
	long timeElapsed = endTime - startTime;
 
	System.out.println("Tiempo de ejecucion en nanosegundos: " + timeElapsed);
	System.out.println("Tiempo de ejecucion en milisegundos: " + timeElapsed / 1000000);
        
    }
    
    private Persona[] inicializaLista(int tam)
    {
        Random random = new Random(); 

        Persona[] lp = new Persona[tam];
        
        for (int i = 0; i < lp.length; i++)
        {
           lp[i] = new Persona("Juan", "Perez", "Gonzalez", random.nextInt(2000)+1);
        }
        
        return lp;
    }
    
    private void imprimeLista(Persona[] lp)
    {
       for (int i = 0; i < lp.length; i++)
       {
           System.out.println(lp[i]);
       }
    }
}
