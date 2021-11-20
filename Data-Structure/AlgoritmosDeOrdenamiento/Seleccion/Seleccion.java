/*
    Algoritmos de Ordenamiento: Selección
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Seleccion {
    public static void seleccionFNacimiento(Persona[] lp)
    {
        for (int i = 0; i < lp.length - 1; i++) 
        { 
            int pos = i; 
            for (int j = i + 1; j < lp.length; j++)
            { 
                if (lp[j].getFechaNacimiento() < lp[pos].getFechaNacimiento()) 
                { 
                    pos = j;
                }
            }
            if (pos != i)
            { 
                Persona tmp = lp[i];
                lp[i] = lp[pos];
                lp[pos] = tmp;
            }
        }
    }
   
   public static void seleccionNombre(Persona[] lp)
   {
        for (int i = 0; i < lp.length - 1; i++) 
        { 
            int pos = i; 
            for (int j = i + 1; j < lp.length; j++)
            { 
                if ((lp[j].getNombre()).compareTo(lp[pos].getNombre()) < 0) 
                { 
                    pos = j;
                }
            }
            if (pos != i)
            { 
                Persona tmp = lp[i];
                lp[i] = lp[pos];
                lp[pos] = tmp;
            }
        }
   }
   
   public static void seleccionaPaterno(Persona[] lp)
   {
        for (int i = 0; i < lp.length - 1; i++) 
        { 
            int pos = i; 
            for (int j = i + 1; j < lp.length; j++)
            { 
                if ((lp[j].getaPaterno()).compareTo(lp[pos].getaPaterno()) < 0) 
                { 
                    pos = j;
                }
            }
            if (pos != i)
            { 
                Persona tmp = lp[i];
                lp[i] = lp[pos];
                lp[pos] = tmp;
            }
        }
   }
    
   public static void seleccionaMaterno(Persona[] lp)
   {
        for (int i = 0; i < lp.length - 1; i++) 
        { 
            int pos = i; 
            for (int j = i + 1; j < lp.length; j++)
            { 
                if ((lp[j].getaMaterno()).compareTo(lp[pos].getaMaterno()) < 0) 
                { 
                    pos = j;
                }
            }
            if (pos != i)
            { 
                Persona tmp = lp[i];
                lp[i] = lp[pos];
                lp[pos] = tmp;
            }
        }
   }
}
