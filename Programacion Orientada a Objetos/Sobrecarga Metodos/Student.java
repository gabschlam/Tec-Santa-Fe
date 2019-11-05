public class Student
{
	private String name;
	private int id;
	private int units;

	public Student(String name, int id)
	{
		this.name = name;
		this.id = id;
		units = 0;
	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return id;
	}

	public int getUnits()
	{
		return units;
	}

	public void incrementUnits(int units)
	{
		this.units += units;
	}

	public boolean hasEnoughUnits()
	{
		if (units > 180) 
		{
			return true;
		}
		return false;
	}

	public void printStudent()
	{
		System.out.println("\"" + name + "\"(#" + id + ")");
	}
}