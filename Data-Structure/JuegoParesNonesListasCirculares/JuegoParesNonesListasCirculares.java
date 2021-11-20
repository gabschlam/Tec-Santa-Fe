/*
    Listas Circulares: Main
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Random;
import java.util.Scanner;

public class JuegoParesNonesListasCirculares {

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        boolean resultado;
        String resp;
        
        do
        {
        System.out.println("Pronostica el resultado final (PARES o NONES)");
        String pronostico = lector.nextLine();
        
        resultado = aJugar();
        
        if(resultado == true)
        {
            if(pronostico.equalsIgnoreCase("PARES"))
            {
                System.out.println("¡Pronosticaste pares!");
                System.out.println("¡Ganaste!");
            }
            else
            {
                System.out.println("Pronosticaste nones...");
                System.out.println("¡Perdiste!");
            }
        }
        else
        {
            if(pronostico.equalsIgnoreCase("NONES"))
            {
                System.out.println("¡Pronosticaste nones!");
                System.out.println("¡Ganaste!");
            }
            else
            {
                System.out.println("Pronosticaste pares...");
                System.out.println("¡Perdiste!");
            }
        }
        System.out.println("¿Deseas jugar otra vez? (SI/NO)");
        resp = lector.nextLine();
        }while (resp.equalsIgnoreCase("SI"));
        System.out.println("Adios");
    }

    private static boolean aJugar() {
        Random random = new Random(); 
        int veces = random.nextInt(5)+1;
        
        veces *=(random.nextInt(10)+1);

        Jugada j1 = new Jugada(2);
        Jugada j2 = new Jugada(3);
        Jugada j3 = new Jugada(3);
        Jugada j4 = new Jugada(4);

        j1.setSiguiente(j2);
        j2.setPrevio(j1);
        
        j2.setSiguiente(j3);
        j3.setPrevio(j2);
        
        j3.setSiguiente(j4);
        j4.setPrevio(j3);
        
        j1.setPrevio(j4);
        j4.setSiguiente(j1);
        
        System.out.println("SACANDO CUENTA AL AZAR...");
        //System.out.println(j1);
                
        while((j1.getSiguiente()!= null) && (veces-->0)){
            j1 = j1.getSiguiente();
            //System.out.println(j1);
        }
        System.out.println(j1);
        
        if(j1.getJugada()%2 == 0)
        {
            System.out.println("La cuenta total de los dedos es par");
            return true;
        }
        else
        {
            System.out.println("La cuenta total de los dedos es non");
            return false;
        }
    }
}
