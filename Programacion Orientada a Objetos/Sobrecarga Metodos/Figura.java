public class Figura
{
	double perimetro(double a)
	{
		return 4*a;
	}

	double perimetro(int a)
	{
		return 4*a;
	}


	double perimetro(double a, double b)
	{
		return 2*(a+b);
	}

	double perimetro(double a, double b, double c)
	{
		return a+b+c;
	}


	double perimetro(double x, double a, double b, double c)
	{
		return x+a+b+c;
	}
}