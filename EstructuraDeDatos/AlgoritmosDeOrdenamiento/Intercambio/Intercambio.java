/*
    Algoritmos de Ordenamiento: Intercambio
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Intercambio 
{
    
   public static void intercambioFNacimiento(Persona[] lp)
    {
        for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = i + 1; j < lp.length; j++)
            {
                Persona p1 = lp[i];
                Persona p2 = lp[j];
                if(p1.getFechaNacimiento() > p2.getFechaNacimiento())
                {
                    lp[i] = p2;
                    lp[j] = p1;
                }
            }
        }
    }
   
   public static void intercambioNombre(Persona[] lp)
   {
       for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = i + 1; j < lp.length; j++)
            {
                Persona p1 = lp[i];
                Persona p2 = lp[j];
                if((p1.getNombre()).compareTo(p2.getNombre()) > 0)
                {
                    lp[i] = p2;
                    lp[j] = p1;
                }
            }
        }
   }
   
   public static void intercambioaPaterno(Persona[] lp)
   {
       for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = i + 1; j < lp.length; j++)
            {
                Persona p1 = lp[i];
                Persona p2 = lp[j];
                if((p1.getaPaterno()).compareTo(p2.getaPaterno()) > 0)
                {
                    lp[i] = p2;
                    lp[j] = p1;
                }
            }
        }
   }
    
   public static void intercambioaMaterno(Persona[] lp)
   {
       for (int i = 0; i < lp.length - 1; i++)
        {
            for (int j = i + 1; j < lp.length; j++)
            {
                Persona p1 = lp[i];
                Persona p2 = lp[j];
                if((p1.getaMaterno()).compareTo(p2.getaMaterno()) > 0)
                {
                    lp[i] = p2;
                    lp[j] = p1;
                }
            }
        }
   }
}
