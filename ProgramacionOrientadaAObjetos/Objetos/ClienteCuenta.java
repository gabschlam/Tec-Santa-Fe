/*
	Objetos: Cliente Cuenta
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public class ClienteCuenta
{
	public static void main(String[] args)
	{
		Cuenta cuenta1 = new Cuenta();
		Cuenta cuenta2 = new Cuenta();


		double cantidad = 4550.20;

		cuenta1.RealizarDeposito(cantidad);

		System.out.println("El Saldo 1 actual es: $ " + cuenta1.ConsultaSaldo());

		cuenta1.RealizarDeposito(200);

		System.out.println("El Saldo 1 actual es: $ " + cuenta1.ConsultaSaldo());

		cuenta2.RealizarDeposito(300200.99);

		System.out.println("El Saldo 2 actual es: $ " + cuenta2.ConsultaSaldo());

	}
}