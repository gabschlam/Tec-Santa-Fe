public class ejemploRaices
{
	public static void main(String[] args)
	{
		Double a;
		Double b;
		Double c;

		a = Double.parseDouble(args[0]);
		b = Double.parseDouble(args[1]);
		c = Double.parseDouble(args[2]);

		System.out.println ("La raiz 1 es " + (((-b)) + Math.sqrt((b*b) - (4*a*c))) / (2*a));
		System.out.println ("La raiz 2 es " + (((-b)) - Math.sqrt((b*b) - (4*a*c))) / (2*a));
	}
}