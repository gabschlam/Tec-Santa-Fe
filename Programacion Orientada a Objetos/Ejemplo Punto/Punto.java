public class Punto
{
	private int x;
	private int y;

	public Punto (int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public double distancia(Punto p)
	{
		double dist = 0;

		if (p.x == x && p.y == y) 
		{
			dist = 0; 	
		}
		else
		{
			dist = Math.sqrt((Math.pow((x - p.x), 2)) + (Math.pow((y - p.y), 2)));
		}

		return dist;
	}
}