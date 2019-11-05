public enum Planeta
{
	MERCURIO (3.303e+23, 2.4397e6),
	VENUS (4.869e+24, 6.0518e6),
	TIERRA (5.976e+24, 6.37814e6),
	MARTE (6.421e+23, 3.3972e6),
	JUPITER (1.9e+27, 7.1492e7),
	SATURNO (5.688e+26, 6.0268e7),
	URANO (8.686e+25, 2.5559e7),
	NEPTUNO (1.024e+26, 2.4746e7);

	private final double mass;
	private final double radius;

	public static final double G =6.67300E-11;

	Planeta(double mass, double radius)
	{
		this.mass = mass;
		this.radius = radius;
	}

	private double mass()
	{
		return mass;
	}

	private double radius()
	{
		return radius;
	}

	public double surfaceGravity()
	{
		return G * mass / (radius*radius);
	}

	public double surfaceWeight(double otherMass)
	{
		return otherMass * surfaceGravity();
	}
}