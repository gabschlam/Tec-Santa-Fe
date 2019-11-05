public class Time
{
	private int hora;
	private int minuto;
	private int segundo;

	public Time()
	{
		this(0, 0, 0);
	}

	public Time(int hora)
	{
		this(hora, 0, 0);
	}

	public Time(int hora, int minuto)
	{
		this(hora, minuto, 0);
	}

	public Time(int hora, int minuto, int segundo)
	{
		if (hora < 0 || hora >= 24) 
			hora = 0;
		else
			this.hora = hora;
		if (minuto < 0 || minuto >= 60) 
			minuto = 0;
		else
			this.minuto = minuto;
		if (segundo < 0 || segundo >= 60) 
			segundo = 0;
		else
			this.segundo = segundo;
	}

	public Time(Time tiempo)
	{
		this(tiempo.hora, tiempo.minuto, tiempo.segundo);
	}

	public void printTime()
	{
		if (hora < 10) 
			System.out.print("0" + hora + ":");
		else
			System.out.print(hora + ":");
		if (minuto < 10) 
			System.out.print("0" + minuto + ":");
		else
			System.out.print(minuto + ":");
		if (segundo < 10) 
			System.out.print("0" + segundo);
		else
			System.out.println(segundo);	
	}

	public void Time()
	{
		System.out.println("Este no es un constructor pero se llama igual");
	}

}