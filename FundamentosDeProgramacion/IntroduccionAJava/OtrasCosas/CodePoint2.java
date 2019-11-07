public class CodePoint2
{
	public static void main (String[] args)
	{
		String str;
		String compara = args[0];
		String nuevo;


		str = "Es misión del Tecnológico de Monterrey formar personas íntegras, éticas, con  una visión humanística y competitivas internacionalmente en su campo  profesional, que al mismo tiempo sean ciudadanos comprometidos con el  desarrollo económico, político, social y cultural de su comunidad y con el uso sostenible de los recursos naturales.";


		if (str.contains(compara.toLowerCase())) 
			System.out.println("Existe");
		else
			System.out.println("No existe");

		nuevo = str.replace("personas", "alumnos"); //PARA REMPLAZAR LA PRIMERA PALABRA POR LA SEGUNDA

		System.out.println(nuevo);

	}
}
