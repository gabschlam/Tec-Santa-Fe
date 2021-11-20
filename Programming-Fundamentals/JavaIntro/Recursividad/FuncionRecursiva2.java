public class FuncionRecursiva2
{
	/*
	PROGRAMA QUE MUESTRA UN CONTEO DEL 1 AL 100 CON
	FUNCION RECURSIVA
	*/

	public static void main(String[] args)
	{
		int n = 100;
		System.out.println("El conteo es: ");
		conteoRecursivo(n);
		System.out.println();
	}

	public static void conteoRecursivo(int num)
	{
		if (num <=0)
			return;
		else
		{
			conteoRecursivo(num -1); //LLAMA DE NUEVO AL METODO HASTA QUE LLEGUE AL UNO Y SUBA OTRA VEZ
			System.out.printf("%d\t", num);
			return;
		}
	}
}