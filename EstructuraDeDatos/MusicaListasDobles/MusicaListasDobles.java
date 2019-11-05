/*
    Listas Dobles: Main
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Scanner;

public class MusicaListasDobles {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        String r;

        Cancion c1 = new Cancion("Despacito", "Luis Fonsi");
        Cancion c2 = new Cancion("Natural", "Imagine Dragons");
        Cancion c3 = new Cancion("Felices los 4", "Maluma");
        Cancion actual = c1;

        c1.setSiguiente(c2);
        c2.setPrevio(c1);
        
        c2.setSiguiente(c3);
        c3.setPrevio(c2);
        
        System.out.println("Bienvenido a tu reproductor de musica");
        do
        {
        System.out.println("¿Que deseas hacer?");
        System.out.println("1 = Ver todas las canciones en tu playlist");
        System.out.println("2 = Ver cancion reproduciendose");
        System.out.println("3 = Cambiar a la siguiente cancion");
        System.out.println("4 = Cambiar a la cancion anterior");
        System.out.println("5 = Agregar nueva cancion");        
        
        int resp = lector.nextInt();
        lector.nextLine();
        
        switch(resp)
        {
            case 1:
                printLista(c1);
                break;
            case 2:
                System.out.println("La cancion reproduciendose es:");
                System.out.println(actual);
                break;
            case 3:
                siguienteCancion(actual);
                actual = actual.getSiguiente();
                break;
            case 4:
                previoCancion(actual);
                actual = actual.getPrevio();
                break;
            case 5:
                System.out.println("Escribe el nombre de la cancion");
                String nombre = lector.nextLine();
                System.out.println("Escribe el artista de la cancion");
                String artista = lector.nextLine();
                agregarCancion(c1, new Cancion(nombre, artista));
                System.out.println("Cancion agregada");
                break;
        }
        System.out.println("¿Deseas realizar otra accion? (SI/NO)");
        r = lector.nextLine();
        } while(r.equalsIgnoreCase("SI"));
        System.out.println("Adios");
    }

    private static void siguienteCancion(Cancion c) {
        
        if(c.getSiguiente()!= null)
        {
            System.out.println("Reproduciendo siguiente cancion:");
            c = c.getSiguiente();
            System.out.println(c);
        }
        else
        {
            System.out.println("No hay una cancion siguiente.");
            System.out.println("Reproduciendo ultima cancion:");
            System.out.println(c);
        }
    }
    
    private static void previoCancion(Cancion c) {
        if(c.getPrevio()!= null)
        {
            System.out.println("Reproduciendo cancion anterior:");
            c = c.getPrevio();
            System.out.println(c);
        }
        else
        {
            System.out.println("No hay una cancion anterior.");
            System.out.println("Reproduciendo primera cancion:");
            System.out.println(c);
        }
    }
    
    private static void agregarCancion(Cancion c1, Cancion c){
        Cancion ultimo = c1;
        while(ultimo.getSiguiente()!= null){
            ultimo = ultimo.getSiguiente();
        }
        ultimo.setSiguiente(c);
        c.setPrevio(ultimo);
    }
    
    private static void printLista(Cancion c) {
        System.out.println("Canciones en tu Playlist");
        System.out.println(c);

        while(c.getSiguiente()!= null){
            c = c.getSiguiente();
            System.out.println(c);
        }
    }
}
