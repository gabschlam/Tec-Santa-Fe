public class Lampara
{
	private boolean encendido;
	private double intensidad;

	public Lampara(boolean encendido, double intensidad)
	{
		this.encendido = encendido;
		this.intensidad = intensidad;
	}

	public void encender()
	{
		encendido = true;
	}

	public void apagar()
	{
		encendido = false;
	}

	public boolean getEstado()
	{
		return encendido;
	}

	public void setIntensidad(double intensidad)
	{
		this.intensidad = intensidad;
	}

	public double getIntensidad()
	{
		return intensidad;
	}
}