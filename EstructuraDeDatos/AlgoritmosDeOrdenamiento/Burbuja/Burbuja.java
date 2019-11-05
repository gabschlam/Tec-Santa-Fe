/*
	Algoritmos de Ordenamiento: Burbuja
	Estructura de Datos
 
	Gabriel Schlam
*/

public class Burbuja {
    public static void burbujaFNacimiento(Persona[] lp)
    {
        for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = 0; j < lp.length - i - 1; j++) 
            {
                if (lp[j].getFechaNacimiento() > lp[j+1].getFechaNacimiento()) 
                {
                   Persona temp = lp[j];
                   lp[j] = lp[j + 1];
                   lp[j + 1] = temp;
                } 
            }
        }
    }
   
   public static void burbujaNombre(Persona[] lp)
   {
       for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = 0; j < lp.length - i - 1; j++) 
            {
                if (lp[j].getNombre().compareTo(lp[j+1].getNombre()) > 0) 
                {
                   Persona temp = lp[j];
                   lp[j] = lp[j + 1];
                   lp[j + 1] = temp;
                } 
            }
        }
   }
   
   public static void burbujaaPaterno(Persona[] lp)
   {
       for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = 0; j < lp.length - i - 1; j++) 
            {
                if (lp[j].getaPaterno().compareTo(lp[j+1].getaPaterno()) > 0) 
                {
                   Persona temp = lp[j];
                   lp[j] = lp[j + 1];
                   lp[j + 1] = temp;
                } 
            }
        }
   }
    
   public static void burbujaaMaterno(Persona[] lp)
   {
      for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = 0; j < lp.length - i - 1; j++) 
            {
                if (lp[j].getaMaterno().compareTo(lp[j+1].getaMaterno()) > 0) 
                {
                   Persona temp = lp[j];
                   lp[j] = lp[j + 1];
                   lp[j + 1] = temp;
                } 
            }
        } 
   }
}
