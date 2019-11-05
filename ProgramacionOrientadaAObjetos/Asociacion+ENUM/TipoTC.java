/*
	Asociación con tipo de datos enum: Clase Tipo TC
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

public enum TipoTC
{
	//TIPO (INGRESO MINIMO, ANUALIDAD, MIN, MAX)
	CLASICA (7500, 699, 29, 47),
	ORO (15000, 959, 25, 45),
	PLATINUM (45000, 1999, 16, 33),
	BLACK (100000, 4599, 9.9, 26);

	private final double ingreso;
	private final double anualidad;
	private final double min;
	private final double max;

	private static final int corte = 28;

	private TipoTC(double ingreso, double anualidad, double min, double max)
	{
		this.ingreso = ingreso;
		this.anualidad = anualidad;
		this.min = min;
		this.max = max;
	}

	public double tasaAnual()
	{
		double tiie = (corte*100)/360;
		double ptos = max - min;
		return tiie + ptos;
	}

	public double tasaMensual()
	{
		return (tasaAnual()/360)*30;
	}
}