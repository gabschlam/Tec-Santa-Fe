import java.util.*;

public class MainCliente
{
	public static void main(String[] args)
	{
		ArrayList<Cliente> lista = new ArrayList<Cliente>();

		Scanner lector = new Scanner(System.in);

		char respuesta = 'S';
		int count = 0;

		do
		{
			System.out.println("¿Nombre?");
			String nombre = lector.next();
			System.out.println("¿Apellido?");
			String apellido = lector.next(); //SOLO LEE STRING
			System.out.println("¿Edad?");
			int edad = lector.nextInt();
			System.out.println();

			Cliente cliente = new Cliente(nombre, apellido, edad);

			System.out.println("¿Deseas agregar una tarjeta? [S/N]");
			char resp = lector.next().charAt(0);
			System.out.println();

			if (resp == 'S' || resp == 's')
			{
				lector.nextLine();
				System.out.println("¿Numero de tarjeta?");
				String numero = lector.nextLine();
				System.out.println("¿Limite de credito?");
				double saldo = lector.nextDouble();
				System.out.print("¿Fecha de expedicion? [dd mm aaaa] ");
				int dia = lector.nextInt();
				int mes = lector.nextInt();
				int anio = lector.nextInt();

				System.out.println("¿Tipo de Tarjeta de Credito?");
				System.out.println("1 = CLASICA");
				System.out.println("2 = ORO");
				System.out.println("3 = PLATINUM");
				System.out.println("4 = BLACK");

				int tipotc = lector.nextInt();

				TarjetaCredito tc = new TarjetaCredito (numero, saldo, dia, mes, anio, dia, mes, anio + 5);

				switch(tipotc)
				{
					case 1:
						tc.setTC(TipoTC.CLASICA);
						break;
					case 2:
						tc.setTC(TipoTC.ORO);
						break;
					case 3:
						tc.setTC(TipoTC.PLATINUM);
						break;
					case 4:
						tc.setTC(TipoTC.BLACK);
						break;
				}
				cliente.setTarjetaCredito(tc);
			}
			lista.add(cliente);
			count++;
			System.out.println("¿Deseas agregar nuevo cliente? [S/N]");
			respuesta = lector.next().charAt(0);
			System.out.println();
		}while(respuesta == 'S' || respuesta == 's');

		for(int i = 0; i < lista.size(); i++)
		{
			if (lista.get(i) != null) 
				lista.get(i).printCliente();
		}

		/*Cliente c1 = new Cliente("Jose", "Perez", 22);
		Cliente c2 = new Cliente("Maria", "Ximenez", 40);

		TarjetaCredito tc1 = new TarjetaCredito ("5234 6372 4827 2837", 52000, 12, 10, 2015, 12, 10, 2019, TipoTC.CLASICA);
		TarjetaCredito tc2 = new TarjetaCredito ("2080 1020 2010 2839", 20000, 8, 1, 2017, 8, 1, 2021, TipoTC.BLACK);
		
		c1.setTarjetaCredito(tc1);
		c2.setTarjetaCredito(tc2);
		c1.printCliente();
		c2.printCliente();*/
	}
}