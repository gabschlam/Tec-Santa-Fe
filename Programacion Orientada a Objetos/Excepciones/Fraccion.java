import java.io.*;
import java.util.InputMismatchException;

public class Fraccion
{
	private int numerador;
	private int denominador;	

	public Fraccion(int numerador, int denominador)
	{
		if (denominador < 0) 
		{
			this.denominador = (denominador * -1);
			this.numerador = (numerador * -1);
		}
		else
		{
			this.numerador = numerador;
			this.denominador = denominador;
		}
	}

	public Fraccion(int numerador)
	{
		this.numerador = numerador;
		this.denominador = 1;
	}

	public Fraccion()
	{
		this.numerador = 0;
		this.denominador = 1;
	}

	public int getNumerador()
	{
		return numerador;
	}

	public int getDenominador()
	{
		return denominador;
	}

	public String toString()
	{
		String texto = numerador + "/" + denominador;
		return texto;
	}

	public double toDouble()
	{
		return numerador / denominador;
	}

	public Fraccion add(Fraccion f) throws ArithmeticException, InputMismatchException
	{
		Fraccion f1 = new Fraccion();
		if (denominador == f.denominador) 
		{
			f1.numerador = numerador + f.numerador;
			f1.denominador = denominador;	
		}
		else
		{
			f1.numerador = (numerador * f.denominador) + (f.numerador * denominador);
			f1.denominador = denominador * f.denominador;
		}
		return f1;
	}

	public Fraccion subtract(Fraccion f) throws ArithmeticException, InputMismatchException
	{
		Fraccion f1 = new Fraccion();
		if (denominador == f.denominador) 
		{
			f1.numerador = numerador - f.numerador;
			f1.denominador = denominador;	
		}
		else
		{
			f1.numerador = (numerador * f.denominador) - (f.numerador * denominador);
			f1.denominador = denominador * f.denominador;
		}
		return f1;
	}

	public Fraccion multiply(Fraccion f) throws ArithmeticException, InputMismatchException
	{
		Fraccion f1 = new Fraccion();
		f1.numerador = numerador * f.numerador; 
		f1.denominador = denominador * f.denominador;
		return f1;
	}

	public Fraccion divide(Fraccion f) throws ArithmeticException, InputMismatchException
	{
		Fraccion f1 = new Fraccion();
		f1.numerador = numerador * f.denominador; 
		f1.denominador = f.numerador * denominador;
		return f1;
	}

	public Fraccion toLowestTerms()
	{
		Fraccion f1 = new Fraccion();
		int divisor = gcd();
		f1.numerador = numerador / divisor;
		f1.denominador = denominador / divisor;
		return f1;
	}

	public int gcd()
	{
		int a = Math.abs(numerador);
		int b = Math.abs(denominador);
		int c;

		if(b == 0)
		{
			return a;
      	}

      	while(b!= 0)
      	{
      		c = a%b;
      		a = b;
      		b = c;
      	}
      	return a;
	}
}