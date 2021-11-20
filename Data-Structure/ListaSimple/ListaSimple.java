/*
    Listas Simples: Main
    Estructura de Datos
 
    Gabriel Schlam
*/

public class ListaSimple {

    public static void main(String[] args) {
        Persona per1 = new Persona("Antonio", 13);
        per1.setSiguiente(new Persona("Isaac", 42));
        per1.getSiguiente().setSiguiente(new Persona("Heeman", 3000));
        
        //per1.getSiguiente().setSiguiente(null);
        
        per1.setSiguiente(per1.getSiguiente().getSiguiente());
        
        printPersonaLigada(per1);

    }

    private static void printPersonaLigada(Persona per1) {
        System.out.println(per1);

        while(per1.getSiguiente()!= null){
            per1 = per1.getSiguiente();
            System.out.println(per1);
        }
    }
    
}
