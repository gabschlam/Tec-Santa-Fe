/*
    Árboles Binarios: Main
    Estructura de Datos
 
    Gabriel Schlam
*/

public class ArbolesBinarios {

    public static void main(String[] args) {
        Nodo raiz = new Nodo(10);
        
        Nodo.insertar(raiz, 5);
        Nodo.insertar(raiz, 40);
        Nodo.insertar(raiz, 1);
        Nodo.insertar(raiz, 7);
        Nodo.insertar(raiz, 50);
        
        Nodo.printNivel(raiz);
        
        System.out.println("Preorden: ");
        Nodo.preOrden(raiz);
        System.out.println("");
        
        System.out.println("Inorden: ");
        Nodo.inOrden(raiz);
        System.out.println("");
        
        System.out.println("Posorden: ");
        Nodo.posOrden(raiz);
        System.out.println("");
        
        Nodo.eliminar(raiz, 40);
        
        Nodo.printNivel(raiz);
        
    }

    
}
