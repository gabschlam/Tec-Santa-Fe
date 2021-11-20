/*
    Algoritmos de Ordenamiento: Merge Sort
    Estructura de Datos
 
    Gabriel Schlam
*/

public class MergeSort {
    
    public static void mergeSort(Persona lp[], int low, int high) 
    {
        if (low < high)
        {
            int mid = (low + high)/2;
            
            mergeSort(lp, low, mid);
            mergeSort(lp , mid + 1, high);
            
            merge(lp, low, mid, high); 
        }
    }
   
    public static void merge(Persona lp[], int low, int mid, int high) 
    {
        int n1 = mid - low + 1; 
        int n2 = high - mid;
        
        Persona temp1[] = new Persona [n1];
        Persona temp2[] = new Persona [n2];

        for (int i = 0; i < n1; i++)
        {
            temp1[i] = lp[low + i];
        }
        for (int j = 0; j < n2; j++)
        {
            temp2[j] = lp[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = low; 
        
        while (i < n1 && j < n2) 
        { 
            if (temp1[i].getFechaNacimiento() <= temp2[j].getFechaNacimiento()) 
            {
                lp[k] = temp1[i];
                i++;
            }
            else
            { 
                lp[k] = temp2[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) 
        {
            lp[k] = temp1[i]; 
            i++;
            k++;
        }
        
        while (j < n2) 
        {
            lp[k] = temp2[j]; 
            j++;
            k++;
        }
    }
}
