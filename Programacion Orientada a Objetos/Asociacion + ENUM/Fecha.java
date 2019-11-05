public class Fecha
{
	private int dia;
	private int mes;
	private int anio;

	public Fecha(int dia, int mes, int anio)
	{
		//REVISAR SI DIA, MES Y AÑO ESTA EN RANGO
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
	}

	public String FormatoFecha()
	{
		return String.format("%2d/%2d/%d", dia, mes, anio);
	}
}