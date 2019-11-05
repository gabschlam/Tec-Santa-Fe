public class Salario
{
	private int antiguedad;
	private double salarioBase;
	private double productividad;
	private int diasTrabajados;

	public Salario(int antiguedad, double salarioBase, double productividad, int diasTrabajados)
	{
		this.antiguedad = antiguedad;
		this.salarioBase = salarioBase;
		this.productividad = productividad;
		this.diasTrabajados = diasTrabajados;
	}

	public double getSalarioBase()
	{
		return salarioBase;
	}

	public double retenciones()
	{
		double total = 0;

		if (salarioBase < 6000) 
		{
			total = salarioTotal() * 0.12;
		}
		else
		{
			total = salarioTotal() * 0.14;
		}

		return total;
	}

	public double incentivoProductividad()
	{
		double total = 0;

		if (productividad >= 0.95) 
		{
			total = salarioBase * 0.25 * antiguedad;
		}
		else if (productividad < 0.95 && productividad >= 0.85) 
		{
			total = salarioBase * 0.17 * antiguedad;
		}
		else if (productividad < 0.85 && productividad >= 0.80) 
		{
			total = salarioBase * 0.12 * antiguedad;
		}

		return total;
		// MENOS DE 80% NO HAY INCENTIVO
	}

	public double incentivoAsistencia()
	{
		double total = 0;

		if ((diasTrabajados / 365) >= 0.90)
		{
			total = salarioBase * 0.10;
		}

		return total;
		/*
		PORCENTAJE DE ASISTENCIA = DIAS TRABAJADOS EN UN AÑO / 365
		MENOS DE 90% DE ASISTENCIA NO HAY INCENTIVO
		*/
	}

	public double antiguedad()
	{
		return (salarioBase/30) * 12 * antiguedad;

		// CORRESPONDIENTE DE 12 DIAS DE SUELDO POR AÑO
	}

	public double salarioTotal()
	{
		return ((incentivoAsistencia() + incentivoProductividad() + antiguedad()) / 12) + salarioBase;
	}

}