/*
    Pilas y Colas: Main
    Estructura de Datos
 
    Gabriel Schlam
*/

public class PilasyColas {

    public static void main(String[] args) {
        Pila p = new Pila();
        
        p.push(1);
        p.push("no soy 100tifiko :V");
        p.push(25);
        
        System.out.println(p.top());
        
        p.pop();
        
        System.out.println(p.top());
        
        System.out.println(p);
        
        p.push("ola ke ase");
        System.out.println(p);
    }
    
}
