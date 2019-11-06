/*
El siguiente código tiene como finalidad generar un sistema de nómina de una empresa.
A lo largo del programa el sistema va a ir preguntando que se quiere hacer para que el usuario vaya ingresando la información, 
la cual se guarda en un archivo .csv que se va acompletando cada vez que se ingresa un nuevo dato.
*/

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Proyecto
{
	public static void main (String[] args) throws IOException
	{
		int eleccion;
		String resp;
		int son = 0;
		String datosLeidos;

		File archivo;
  		archivo = new File("Datos.csv");

		String nombreArchivo = "Datos.csv";

		String[] arreglo;
		arreglo = new String[8];

		Scanner lector = new Scanner(System.in); 

  		FileWriter escritor = new FileWriter(archivo, true);
  		PrintWriter pw = new PrintWriter(escritor);

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		while((datosLeidos = br.readLine()) != null)
  		{
  			son++;
  		}
  		leer.close();

		do //SE HACE TODO DENTRO DE UN DO WHILE POR SI EL OPERADOR QUIERE HACER MAS DE UNA ACCION
		{
			System.out.println("Bienvenido al Sistema de Nomina");
			System.out.println("Menu:");
			System.out.println("Elige que quiere hacer hoy (1-7)");
			System.out.println("1 = Consulta de Usuarios");
			System.out.println("2 = Nuevo Usuario");
			System.out.println("3 = Dada de Baja de Usuario");
			System.out.println("4 = Editar Informacion de Usuario");
			System.out.println("5 = Asignaciones y Deducciones");
			System.out.println("6 = Generar Nomina de todos los usuarios");
			System.out.println("7 = Generar Recibo de todos los usuarios");
			System.out.println("8 = Generar Lista de Todos los Usuarios");
			eleccion = lector.nextInt();
			System.out.println();

			switch (eleccion) //SE DEFINE LA ACCION DEPENDE LA RESPUESTA DEL USUARIO
			{
				case 1:
					System.out.println("Consulta de usuarios:");
					consulta(arreglo, nombreArchivo); //SE LLAMA AL METODO CORRESPONDIENTE
					break;
				case 2:
					System.out.println("Nuevo Usuario:");
					nuevo(arreglo, archivo); //SE LLAMA AL METODO CORRESPONDIENTE
					break;
				case 3:
					System.out.println("Dada de Baja de Usuario:");
					eliminar(arreglo, nombreArchivo, archivo); //SE LLAMA AL METODO CORRESPONDIENTE
					break;
				case 4:
					System.out.println("Editar Informacion de Usuario");
					editar(arreglo, nombreArchivo, archivo, son); //SE LLAMA AL METODO CORRESPONDIENTE
					break;
				case 5:
					System.out.println("Asignaciones y Deducciones:");
					asigdedu(arreglo, nombreArchivo, archivo, son); //SE LLAMA AL METODO CORRESPONDIENTE
					break;
				case 6:
					System.out.println("Generacion de Nomina de Todos los Usuarios");
					genenom(arreglo, nombreArchivo); //SE LLAMA AL METODO CORRESPONDIENTE
					break;				
				case 7:
					System.out.println("Generacion de Recibo de Todos los Usuarios");
					recibo(arreglo, nombreArchivo); //SE LLAMA AL METODO CORRESPONDIENTE
					break;
				case 8:
					System.out.println("Generando Lista de Usuarios");
					lista(arreglo, nombreArchivo); //SE LLAMA AL METODO CORRESPONDIENTE
					break;		
			}
			lector.nextLine();
			System.out.println("Desea regresar al menu?");
			resp = lector.nextLine();

		}while (resp.equalsIgnoreCase ("si"));

	escritor.close();
	}

	public static void consulta(String[] arreglo, String nombreArchivo) throws IOException
	{
		String nombre;

		Scanner lector = new Scanner(System.in);
		System.out.println("Que nombre deseas buscar?");
  		nombre = lector.nextLine();
  		String datosLeidos;
  		int cont = 0;

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		while((datosLeidos = br.readLine()) != null )
  		{
  			if(datosLeidos.contains(nombre))
  			{
  				arreglo = datosLeidos.split(",");

  				System.out.println("Existe");
  				System.out.println("Nombre: " + arreglo[0]);
  				System.out.println("Apellido: " + arreglo[1]);
  				System.out.println("Cargo: " + arreglo[2]);
  				System.out.println("Sueldo Base: " + arreglo[3]);
 				System.out.println("Fecha De Ingreso: " + arreglo[4]);
 				System.out.println("Numero De Cuenta De Nomina: " + arreglo[5]);
  				System.out.println("Dias Trabajados: " + arreglo[6]);
  				System.out.println("Asignaciones: " + arreglo[7]);
  				System.out.println("Deducciones: " + arreglo[8]);

  				cont += 1;
 			}
		}
		if(cont==0)
		{
  			System.out.println("No existe el usuario");
		}
		leer.close();
	}

	public static void nuevo(String[] arreglo, File archivo) throws IOException
	{
		FileWriter escritor = new FileWriter(archivo, true);
		PrintWriter pw = new PrintWriter(escritor);
		Scanner lector = new Scanner(System.in);
		Random nominaAleatorio = new Random();
		String nom, ape, car, sue, fec, num, dia, asi, ded, ans;
		int ale;

		do
		{
			System.out.println("Nombre:");
			nom = lector.nextLine();

			System.out.println("Apellido:");
			ape = lector.nextLine();

			System.out.println("Cargo:");
			car = lector.nextLine();

			System.out.println("Sueldo Base:");
			sue = lector.nextLine();

			System.out.println("Fecha de Ingreso:");
			fec = lector.nextLine();

			System.out.println("Numero de Nomina Generado");
			ale = nominaAleatorio.nextInt(900000)+100000;
			num = Integer.toString(ale);
			System.out.println("Nuevo Numero de Nomina: " + num);

			System.out.println("Numero de Dias Trabajados:");
			dia = lector.nextLine();

			pw.printf("%s,%s,%s,%s,%s,%s,%s,0.0,0.0,\n", nom, ape, car, sue, fec, num, dia);

			System.out.println("Desea Introducir Nuevo Usuario?");
			ans = lector.nextLine();
		} while (ans.equalsIgnoreCase("si"));

		escritor.close();
	}

	public static void eliminar(String[] arreglo, String nombreArchivo, File archivo) throws IOException
	{
		String nombre;

		Scanner lector = new Scanner(System.in);
		System.out.println("Que nombre deseas eliminar?");
  		nombre = lector.nextLine();
  		String datosLeidos;
  		int cont = 0;
  		int a = 0;

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		while((datosLeidos = br.readLine()) != null )
  		{

  			if(datosLeidos.contains(nombre))
  			{

 			}
 			else
 			{
 				if(a == 0)
 				{
 				FileWriter escritor = new FileWriter(archivo);
				PrintWriter pw = new PrintWriter(escritor);
 				pw.println(datosLeidos);
 				escritor.close();
 				a++;
 				}
 				else
 				{
 				FileWriter escritor = new FileWriter(archivo, true);
				PrintWriter pw = new PrintWriter(escritor);
 				pw.println(datosLeidos);
 				escritor.close();
 				}
 			}
		}

		if(cont==0)
		{
  			System.out.println("No existe el usuario");
		}
		else
		{
			System.out.println("Exito");
		}
		leer.close();
	}

	public static void editar(String[] arreglo, String nombreArchivo, File archivo, int son) throws IOException
	{
		String nombre;

		Scanner lector = new Scanner(System.in);
		System.out.println("Que nombre deseas editar?");
  		nombre = lector.nextLine();
  		String datosLeidos;
  		int cont = 0;
  		int a = 0;
  		int b = 0;
      int faltan = 0;

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		while((datosLeidos = br.readLine()) != null)
  		{

  			if(datosLeidos.contains(nombre))
  			{
  				FileWriter escritor = new FileWriter(archivo, true);
				PrintWriter pw = new PrintWriter(escritor);

  				arreglo = datosLeidos.split(",");
  				System.out.println("Existe... Editando");
  				System.out.println("1 = Nombre");
  				System.out.println("2 = Apellido");
  				System.out.println("3 = Cargo");
  				System.out.println("4 = Sueldo Base");
  				System.out.println("5 = Fecha de Ingreso");
  				System.out.println("6 = Dias Trabajados");
  				b = lector.nextInt();

  				switch(b)
  				{
  					case 1:					
  						System.out.println("Introduce nuevo nombre");
  						lector.nextLine();
  						arreglo[0] = lector.nextLine();

  						for(int i = 0; i < arreglo.length; i++)
  						{
  							pw.printf("%s,", arreglo[i]);
  						}
  						pw.println();
  						break;

  					case 2:
  						System.out.println("Introduce nuevo apellido");
  						lector.nextLine();
  						arreglo[1] = lector.nextLine();

  						for(int i = 0; i < arreglo.length; i++)
  						{
  							pw.printf("%s,", arreglo[i]);
  						}
  						pw.println();
  						break;

  					case 3:
  						System.out.println("Introduce nuevo cargo");
  						lector.nextLine();
  						arreglo[2] = lector.nextLine();

  						for(int i = 0; i < arreglo.length; i++)
  						{
  							pw.printf("%s,", arreglo[i]);
  						}
  						pw.println();
  						break;

  					case 4:
  						System.out.println("Introduce nuevo sueldo base");
  						lector.nextLine();
  						arreglo[3] = lector.nextLine();

  						for(int i = 0; i < arreglo.length; i++)
  						{
  							pw.printf("%s,", arreglo[i]);
  						}
  						pw.println();
  						break;

  					case 5:
  						System.out.println("Introduce nueva fecha de ingreso");
  						lector.nextLine();
  						arreglo[4] = lector.nextLine();

  						for(int i = 0; i < arreglo.length; i++)
  						{
  							pw.printf("%s,", arreglo[i]);
  						}
  						pw.println();
  						break;

  					case 6:
  						System.out.println("Introduce nueva cantidad de dias trabajados");
  						lector.nextLine();
  						arreglo[6] = lector.nextLine();

  						for(int i = 0; i < arreglo.length; i++)
  						{
  							pw.printf("%s,", arreglo[i]);
  						}
  						pw.println();
  						break;

  				}
                faltan++;
  				escritor.close();

  				cont++;
 			}
 			else
 			{
 				if(a == 0)
 				{
 				   FileWriter escritor = new FileWriter(archivo);
				    PrintWriter pw = new PrintWriter(escritor);
 				   pw.println(datosLeidos);
 				   escritor.close();
 				   a++;
                   faltan++;
 				}
 				else
 				{
 				   FileWriter escritor = new FileWriter(archivo, true);
				    PrintWriter pw = new PrintWriter(escritor);

                    if(faltan >= son)
                    {
                        return;
                    }

 				   pw.println(datosLeidos);
 				   escritor.close();
                   faltan++;
 				}
 			}
		}

		if(cont==0)
		{
  			System.out.println("No existe el usuario");
		}
		else
		{
			System.out.println("Exito");
		}
		leer.close();
	}

	public static void asigdedu(String[] arreglo, String nombreArchivo, File archivo, int son) throws IOException
	{
		String nombre;

		Scanner lector = new Scanner(System.in);
		System.out.println("Que nombre deseas agregar asignaciones y deducciones?");
  		nombre = lector.nextLine();
  		String datosLeidos;
  		int cont = 0;
  		int a = 0;
  		int asided = 0;
  		int p = 0;
  		int faltan = 0;

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		while((datosLeidos = br.readLine()) != null)
  		{
  			if(datosLeidos.contains(nombre))
  			{
  				String algoMas;
  				arreglo = datosLeidos.split(",");
  				cont++;
  				double total = Double.parseDouble(arreglo[7]);
  				double total2 = Double.parseDouble(arreglo[8]);
  				double dias;
  				int elec;

  				System.out.println("Que deseas agregar?");
  				System.out.println("1 = Asignaciones");
  				System.out.println("2 = Deducciones");
  				asided = lector.nextInt();

  				switch(asided)
  				{
  					case 1:
  						do
  						{
  							System.out.println("Asignaciones, que desea asignar?");
  							System.out.println("1 = Bonos");
  							System.out.println("2 = Dias Feriados");
  							System.out.println("3 = Horas Extras");
  							elec = lector.nextInt();
  							double HorEx = 0, porDia = 0, porHora = 0, bono = 0, feriados = 0;

  							switch(elec)
  							{
  								case 1:
  									System.out.printf("El sueldo base del usuario es de %s\n", arreglo[3]);
  									System.out.println("De cuanto consta el bono?");
  									bono = lector.nextDouble();
  									total += bono;
  									System.out.println("Exito");
  									break;

  								case 2:
  									int cantDias;
  									System.out.printf("El sueldo base del usuario es de %s\n", arreglo[3]);
  									System.out.println("Cuantos dias feriados se trabajo");
  									cantDias = lector.nextInt();
  									porDia = (Double.parseDouble(arreglo[3]))/20;
  									feriados = (porDia * cantDias);
  									total += feriados;
  									System.out.println("Exito");
  									break;

  								case 3:
  									int cantHoras;
  									System.out.printf("El sueldo base del usuario es de %s\n", arreglo[3]);
  									System.out.println("Cuantas horas extras se trabajo?");
  									cantHoras = lector.nextInt();
  									porDia = (Double.parseDouble(arreglo[3]))/20;
  									porHora = porDia/8;
  									HorEx = (porHora * cantHoras * 1.5)/2;
  									total += HorEx;
  									System.out.println("Exito");
  									break;								
  							}
  							algoMas = "";
  							System.out.println("Desea asignar algo mas?");
  							lector.nextLine();
  							algoMas = lector.nextLine();
  						} while (algoMas.equalsIgnoreCase("si"));
  						p++;
  						break;

  					case 2:
  						do
  						{
  							System.out.println("Deducciones, que deaea deducir?");
  							System.out.println("1 = IVA");
  							System.out.println("2 = ISR");
  							System.out.println("3 = Prestamo");
  							elec = lector.nextInt();

  							switch(elec)
  							{
  								case 1:
  									double iva;
  									System.out.printf("El sueldo base del usuario es de %s\n", arreglo[3]);
  									System.out.println("IVA Asignado");
  									iva = ((Double.parseDouble(arreglo[3]))*.16)/2;
  									total2 += iva;
  									System.out.println("Exito");
  									break;

  								case 2:
  									double isr, dinero, pors = 0;
  									System.out.printf("El sueldo base del usuario es de %s\n", arreglo[3]);
  									System.out.println("ISR Asignado");
  									dinero = Double.parseDouble(arreglo[3])/2;
  										if(dinero <= 496.07)
  										{
  											pors = .0192;
  										}
  										else if (dinero >= 496.08 && dinero <= 4210.41)
  										{
  											pors = .064;
  										}
  										else if (dinero >= 4210.42 && dinero <= 7399.42)
  										{
  											pors = .1088;
  										}
  										else if (dinero >= 7399.43 && dinero <= 8601.50)
  										{
  											pors = .16;
  										}
  										else if (dinero >= 8601.51 && dinero <= 10298.35)
  										{
  											pors = .1792;
  										}
  										else if (dinero >= 10298.36 && dinero <= 20770.29)
  										{
  											pors = .2136;
  										}
  										else if (dinero >= 20770.30 && dinero <= 32736.83)
  										{
  											pors = .2352;
  										}
  										else if (dinero >= 32736.84 && dinero <= 62500)
  										{
  											pors = .30;
  										}
  										else if (dinero >= 62500.01 && dinero <= 83333.33)
  										{
  											pors = .32;
  										}
  										else if (dinero >= 83333.34 && dinero <= 145833.33)
  										{
  											pors = .34;
  										}
  										else if (dinero >= 145833.34 && dinero <= 208333.33)
  										{
  											pors = .36;
  										}
  										else if (dinero >= 208333.34 && dinero <= 266666.67)
  										{
  											pors = .38;
  										}
  										else if (dinero >= 266666.68)
  										{
  											pors = .40;
  										}
  									isr = dinero * pors;
  									total2 += isr;
  									System.out.println("Exito");
  									break;

  								case 3:
  									double prestamo;
  									System.out.printf("El sueldo base del usuario es de %s\n", arreglo[3]);
  									System.out.println("De cuanto consta el prestamo");
  									prestamo = lector.nextDouble();
  									total2 += prestamo;
  									System.out.println("Exito");
  									break;
  							}
  							System.out.println("Desea deducir algo mas?");
  							lector.nextLine();
  							algoMas = lector.nextLine();
  						} while (algoMas.equalsIgnoreCase("si"));
  						p++;
  						break;
  				}
  				FileWriter escritor = new FileWriter(archivo, true);
				  PrintWriter pw = new PrintWriter(escritor);

  				if (p == 1)
  				{
  					arreglo[7] = String.valueOf(total);
  					arreglo[8] = String.valueOf(total2);

  					for(int i = 0; i < arreglo.length; i++)
  					{
  						pw.printf("%s,", arreglo[i]);
  					}
  					pw.println();
  					faltan++;
  				}
  				escritor.close();
 			}
 			else
 			{
 				if(a == 0)
 				{
 					FileWriter escritor = new FileWriter(archivo);
					PrintWriter pw = new PrintWriter(escritor);
 					pw.println(datosLeidos);
 					escritor.close();
 					a++;
 					faltan++;
 				}
 				else
 				{
 					FileWriter escritor = new FileWriter(archivo, true);
					PrintWriter pw = new PrintWriter(escritor);
					
					if(faltan >= son)
					{
						return;
					}

 					pw.println(datosLeidos);
 					escritor.close();
 					faltan++;
 				}
 			}
		}
		leer.close();

		if(cont == 0)
		{
  			System.out.println("No existe el usuario");
		}
		else
		{
			System.out.println("Exito");
		}
	}

	public static void genenom(String[] arreglo, String nombreArchivo) throws IOException
	{
  		String datosLeidos;
  		double suelbas = 0, asig = 0, dedu = 0, total = 0, jefe = 0;

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		System.out.println("Nombre\t\t|Apellido\t\t|Numero de Nomina\t|Sueldo Base\t|Asignaciones\t|Deducciones\t|Total\t");
  		System.out.println("--------------------------------------------------------------------------------------------------------------------------");

  		while((datosLeidos = br.readLine()) != null )
  		{
  			arreglo = datosLeidos.split(",");

  			suelbas = Double.parseDouble(arreglo[3]);
  			asig = Double.parseDouble(arreglo[7]);
  			dedu = Double.parseDouble(arreglo[8]);
  			total = (suelbas + asig) - dedu;
  			jefe += total;

  			System.out.printf("%s\t\t|%s   \t\t|%s\t\t\t|%s\t\t|%s\t\t|%s\t\t|%.2f\t\n", arreglo[0], arreglo[1], arreglo[5], arreglo[3], arreglo[7], arreglo[8], total);

		}
		System.out.println("El jefe debe pagar: $" + jefe + " en total");
		leer.close();
	}

	public static void recibo(String[] arreglo, String nombreArchivo) throws IOException
	{
  		String datosLeidos;
  		double suelbas = 0, asig = 0, dedu = 0, total = 0;

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		System.out.println("|Nombre   \t|Apellido\t|Cargo    \t|Sueldo   \t|Fecha Ingreso\t\t|Nomina  \t|Asignaciones\t|Deducciones\t|Total\t");
  		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");

  		while((datosLeidos = br.readLine()) != null )
  		{
  			arreglo = datosLeidos.split(",");

  			suelbas = Double.parseDouble(arreglo[3]);
  			asig = Double.parseDouble(arreglo[7]);
  			dedu = Double.parseDouble(arreglo[8]);
  			total = (suelbas + asig) - dedu;

  			for (int i = 0; i < arreglo.length; i++)
  			{
  				System.out.printf("|%s     \t", arreglo[i]);
  			}
  			System.out.println();

		}
		leer.close();
	}

	public static void lista(String[] arreglo, String nombreArchivo) throws IOException
	{
  		String datosLeidos;
  		double suelbas = 0, asig = 0, dedu = 0, total = 0;

  		FileReader leer = new FileReader(nombreArchivo);
  		BufferedReader br = new BufferedReader(leer);

  		System.out.println("|Nombre    \t\t|Apellido    \t\t|Cargo    \t\t|Nomina\t");
  		System.out.println("----------------------------------------------------------------------------------");

  		while((datosLeidos = br.readLine()) != null )
  		{
  			arreglo = datosLeidos.split(",");

  			System.out.printf("|%s    \t\t|%s    \t\t|%s   \t\t|%s\t", arreglo[0], arreglo[1], arreglo[2], arreglo[5]);
  			System.out.println();
		}

		leer.close();
	}
}