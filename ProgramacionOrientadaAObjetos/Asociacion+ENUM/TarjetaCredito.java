/*
	Asociación con tipo de datos enum: Clase Tarjeta Crédito
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

//EJEMPLO COMPOSICION
public class TarjetaCredito
{
	private String numero;
	private double saldo;
	private Fecha fechaExpiracion;
	private Fecha fechaExpedicion;
	private TipoTC tipotc;

	public TarjetaCredito(String numero, double saldo, int diaexpe, int mesexpe, int anioexpe, int diaexpi, int mesexpi, int anioexpi)
	{
		this.numero = numero;
		this.saldo = saldo;
		fechaExpedicion = new Fecha(diaexpe, mesexpe, anioexpe);
		fechaExpiracion = new Fecha(diaexpi, mesexpi, anioexpi);
	}

	public TarjetaCredito(String numero, double saldo, int diaexpe, int mesexpe, int anioexpe, int diaexpi, int mesexpi, int anioexpi, TipoTC tipotc)
	{
		this.numero = numero;
		this.saldo = saldo;
		fechaExpedicion = new Fecha(diaexpe, mesexpe, anioexpe);
		fechaExpiracion = new Fecha(diaexpi, mesexpi, anioexpi);
		this.tipotc = tipotc;
	}

	public String getNumero()
	{
		return numero;
	}

	public double getSaldo()
	{
		return saldo;
	}

	public void setTC(TipoTC tipotc)
	{
		this.tipotc = tipotc;
	}

	public void compras(double cantidad)
	{
		if (cantidad > saldo) 
			System.out.println("Fondos insuficientes");
		else
		{
			saldo = saldo - cantidad;
			System.out.println("Compra realizada");
		}
	}

	public void printTipoTarjeta()
	{
		System.out.println("Tipo de Tarjeta: " + tipotc);
		System.out.printf("Tasa de interes mensual: $%.2f\n", tipotc.tasaMensual());
		System.out.printf("Tasa de interes anual: $%.2f\n", tipotc.tasaAnual());
	}

	public void printTC()
	{
		System.out.printf("Tarjeta de Credito: %s\nSaldo: %.2f\n", numero, saldo);
		printTipoTarjeta();
		System.out.printf("Fecha de Expedicion: %s\nFecha de Expiracion: %s\n", fechaExpedicion.FormatoFecha(), fechaExpiracion.FormatoFecha());
	}
}