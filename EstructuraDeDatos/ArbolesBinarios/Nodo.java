/*
    Árboles Binarios: Clase Nodo
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Nodo {
    private int dato;
    private Nodo izq;
    private Nodo der;

    public Nodo() {
        this.dato = -1;
        this.izq = null;
        this.der = null;  
    }
    
    public Nodo(int dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
    }

    public Nodo(int dato, Nodo izq, Nodo der) {
        this.dato = dato;
        this.izq = izq;
        this.der = der;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }
    
    public static Nodo insertar(Nodo n, int dato) { 
        if (n == null) {
            return new Nodo(dato);
        }
        else if(n.getDato() > dato)
            n.setIzq(insertar(n.getIzq(), dato));
        else if(n.getDato() < dato)
            n.setDer(insertar(n.getDer(), dato));
        else if(n.getDato() == dato) {
            //n.setDato(dato);
        }
        return n;
    }
    
    public static Nodo buscar(Nodo n, int dato) { 
        if (n == null) {
            return null;
        }
        else if(n.getDato() > dato)
            return buscar(n.getIzq(), dato);
        else if(n.getDato() < dato)
            return buscar(n.getDer(), dato);
        return n;
    }
    
    public static Nodo buscarMin(Nodo n) {
        if (n == null) {
            return null;
        }
        else if(n.getIzq() == null)
            return n;
        else
            return buscarMin(n.getIzq());
    }
    
    public static Nodo buscarMax(Nodo n) {
        if (n == null) {
            return null;
        }
        else if(n.getDer() == null)
            return n;
        else
            return buscarMax(n.getDer());
    }
    
    public static void preOrden(Nodo n) {
        if (n != null) {
            System.out.print(n.getDato() + " ");
            preOrden(n.getIzq());
            preOrden(n.getDer());
        }
    }
    
    public static void inOrden(Nodo n) {
        if (n != null) {
            preOrden(n.getIzq());
            System.out.print(n.getDato() + " ");
            preOrden(n.getDer());
        }
    }
    
    public static void posOrden(Nodo n) {
        if (n != null) {
            preOrden(n.getIzq());
            preOrden(n.getDer());
            System.out.print(n.getDato() + " ");
        }
    }
    
    public static Nodo eliminar(Nodo n, int dato) {
        if (n == null){
            return null;
        }
        else if (buscar(n, dato).getDato() == dato) {
            if (n.getDato() > dato) {
                n.setIzq(eliminar(n.getIzq(), dato));
            } 
            else if (n.getDato() < dato)
                n.setDer(eliminar(n.getDer(), dato)); 
            else {
                if (n.getIzq() != null && n.getDer() != null) {
                    Nodo temp = n;
                    Nodo minDer = buscarMin(temp.getDer());
                    n.setDato(minDer.getDato());
                    eliminar(n.getDer(), minDer.getDato());
                }
                else if (n.getIzq() != null)
                    n = n.getIzq();
                else if (n.getDer() != null)
                    n = n.getDer();
                else
                    n = null;
            }
            return n;
        }
        else
            return null;
    }
    
    public static void printNivel(Nodo raiz) {
        System.out.println("Imprimiendo arbol por nivel...");
	int h = numNiveles(raiz);  
	for (int i = 1; i <= h; i++) {
            printNivelActual(raiz, i);
            System.out.println();
        }
    }
    
    public static int numNiveles(Nodo raiz) { 
	if (raiz == null) {
            return 0;
        } 
        else { 
            int lheight = numNiveles(raiz.getIzq()); 
            int rheight = numNiveles(raiz.getDer()); 
			  
            if (lheight > rheight) {
                return (lheight+1);
            } 
            else {
                return (rheight+1);
            }  
	} 
    } 
  
    private static void printNivelActual (Nodo raiz ,int nivel) { 
        if (raiz == null) {
            return;
        } 
	if (nivel == 1) {
            System.out.print(raiz.getDato() + " ");
        } 
	else if (nivel > 1) { 
            printNivelActual(raiz.getIzq(), nivel-1); 
            printNivelActual(raiz.getDer(), nivel-1); 
	} 
    } 
            
}
