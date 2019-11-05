/*
    Algoritmos de Ordenamiento: Inserción
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Insercion {
    public static void insercionFNacimiento(Persona[] lp)
    {
        for (int i = 1; i < lp.length; i++)
        {
            Persona temp = lp[i];
            int j = i - 1;
            while ((j >= 0) && (temp.getFechaNacimiento() < lp[j].getFechaNacimiento()))
            {
                lp[j + 1] = lp[j];
                j--;
            }
            lp[j + 1] = temp;
        }
    }
   
   public static void insercionNombre(Persona[] lp)
   {
       for (int i = 1; i < lp.length; i++)
        {
            Persona temp = lp[i];
            int j = i - 1;
            while ((j >= 0) && (temp.getNombre().compareTo(lp[j].getNombre())) < 0)
            {
                lp[j + 1] = lp[j];
                j--;
            }
            lp[j + 1] = temp;
        }
   }
   
   public static void insercionaPaterno(Persona[] lp)
   {
       for (int i = 1; i < lp.length; i++)
        {
            Persona temp = lp[i];
            int j = i - 1;
            while ((j >= 0) && (temp.getaPaterno().compareTo(lp[j].getaPaterno())) < 0)
            {
                lp[j + 1] = lp[j];
                j--;
            }
            lp[j + 1] = temp;
        }
   }
    
   public static void insercionaMaterno(Persona[] lp)
   {
       for (int i = 1; i < lp.length; i++)
        {
            Persona temp = lp[i];
            int j = i - 1;
            while ((j >= 0) && (temp.getaMaterno().compareTo(lp[j].getaMaterno())) < 0)
            {
                lp[j + 1] = lp[j];
                j--;
            }
            lp[j + 1] = temp;
        }
   }
}
