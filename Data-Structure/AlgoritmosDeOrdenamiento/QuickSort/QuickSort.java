/*
    Algoritmos de Ordenamiento: Quick Sort
    Estructura de Datos
 
    Gabriel Schlam
*/

public class QuickSort {
    
    public static void quickSort(Persona[] p, int low, int high) 
    { 
        if (low < high) 
        { 
            int i = particion(p, low, high); 
            quickSort(p, low, i-1); 
            quickSort(p, i+1, high); 
        } 
    } 
    
    public static int particion(Persona[] p, int low, int high) 
    { 
        int pivote = p[high].getFechaNacimiento();  
        int i = low-1;
        for (int j = low; j < high; j++) 
        {
            if (p[j].getFechaNacimiento() <= pivote) 
            { 
                i++; 
                Persona temp = p[i]; 
                p[i] = p[j]; 
                p[j] = temp;
            } 
        }
        Persona temp = p[i+1]; 
        p[i+1] = p[high]; 
        p[high] = temp; 
  
        return i+1; 
    }
}
