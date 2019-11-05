import java.util.*;
import java.util.Scanner;

public class MainPlaneta
{
	public static void main(String[] args)
	{
		Scanner lector = new Scanner(System.in);

		System.out.println("Dame tu peso");



		double earthWeight = lector.nextDouble();
		double mass = earthWeight/Planeta.TIERRA.surfaceGravity();

		for (Planeta p : Planeta.values()) 
		{
			System.out.printf("Tu peso en %S es %f%n", p, p.surfaceWeight(mass));
			
		}
	}
}