/*
	Objetos: Cuenta
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class Cuenta
{
	public double saldo;
	public String nombre;

	public double ConsultaSaldo()
	{
		return saldo;
	}

	public void RealizarDeposito(double cantidad)
	{
		saldo = saldo + cantidad;
	}
}