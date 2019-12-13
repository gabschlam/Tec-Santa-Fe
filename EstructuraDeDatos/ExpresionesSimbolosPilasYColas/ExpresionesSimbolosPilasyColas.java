/*
    Ejemplo de expresiones balanceadas con Pilas y Colas: Main
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Scanner;

public class ExpresionesSimbolosPilasyColas {
    
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
	String e;
        String resp;
        char[] exp;
        
        do
        {
            System.out.println("Ingresa una expresion: ");
            e = lector.nextLine();
        
            exp = e.toCharArray();

            if (Balance(exp) == true) 
                System.out.println("La expresion esta balanceada"); 
            else
                System.out.println("La expresion no esta balanceada");
            
            System.out.println("¿Deseas revisar otra expresion? (SI/NO)");
            resp = lector.nextLine();
        } while(resp.equalsIgnoreCase("SI"));
    } 
    
    public static boolean parCoincide(char c1, char c2) 
    { 
	if ((c1 == '(') && (c2 == ')')) 
            return true; 
	else if ((c1 == '[') && (c2 == ']')) 
            return true; 
        else if ((c1 == '{') && (c2 == '}')) 
            return true; 
	else
            return false; 
	} 

    public static boolean Balance(char exp[]) 
    { 
	Pila pila = new Pila();
	
	for(int i = 0; i < exp.length; i++) 
	{
            // SI exp[i] ES UN PARENTESIS QUE ABRE, PUSH EN EL SIG IF
            
            if (exp[i] == '{' || exp[i] == '(' || exp[i] == '[') 
                pila.push(exp[i]); 
                
            /*SI exp[i] ES UN PARENTESIS QUE CIERRA, POP A LA PILA PARA VER
            SI EL PAR COINCIDE... EN EL SIG IF*/
            
            if (exp[i] == '}' || exp[i] == ')' || exp[i] == ']') 
            {
                //SI HAY UN PARENTESIS QUE CIERRA SIN PAR, REGRESA FALSO

		if (pila.esVacia()) 
		{
                    return false; 
                }
                
		else if ((parCoincide(pila.pop(), exp[i])) == false) 
		{ 
                    return false; 
		} 
            }		
	}
        /* SI HAY ALGO MAS EN LA EXPRESION, ENTONCES ES UN PARENTESIS QUE ABRE
        SIN UNO QUE CIERRA. ENTONCES SE COMPRUEBA QUE ESTE VACIA*/
		
	if (pila.esVacia()) 
            return true;
	else
	{
            return false; 
	} 
    } 
} 
