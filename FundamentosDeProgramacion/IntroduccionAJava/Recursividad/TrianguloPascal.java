import java.util.Scanner;
public class TrianguloPascal
{
	public static void main(String[] args)
  	{
    	int r,c;
	    Scanner lector = new Scanner(System.in);

    	System.out.println("Dame que renglon quieres obtener");
    	r = lector.nextInt();

	    System.out.println("Dame que columna quieres obtener");
    	c = lector.nextInt();

		if (pascal(r,c) == 0) 
		{
			System.out.println("La posicion no existe");
		}
		else
		{
			System.out.printf("El numero en el Triangulo de Pascal es: %d\n",pascal(r,c));
		}
  	}

  	public static int pascal(int r, int c)
  	{
    	if (c > r) 
    	{
    		return 0;
    	}
    	if(r==1 || c==1 || r==c ) return 1;
    	else if(r==(c-1))
    	{
	 		return r;
    	}
    	else
  		{
	 		return pascal(r-1,c-1)+pascal(r-1,c);
  		}
  	}
}