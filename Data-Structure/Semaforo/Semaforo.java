public class Semaforo
{
	private Lampara verde;
	private Lampara amarillo;
	private Lampara rojo;

	public Semaforo()
	{
		verde = new Lampara(false, 0);
		amarillo = new Lampara(false, 0);
		rojo = new Lampara(false, 0);
	}

	public void cambiaRojo()
	{
		System.out.println();
		System.out.println("CAMBIANDO SEMAFORO A ROJO...");		
		verde.apagar();
		verde.setIntensidad(0);
		System.out.println("Verde apagado");
		amarillo.encender();
		amarillo.setIntensidad(5);
		System.out.println("Amarillo encendido");
		amarillo.apagar();
		amarillo.setIntensidad(0);		
		System.out.println("Amarillo apagado");
		rojo.encender();
		rojo.setIntensidad(5);
		System.out.println("Rojo encendido");
	}

	public void cambiaVerde()
	{
		System.out.println();
		System.out.println("CAMBIANDO SEMAFORO A VERDE...");		
		rojo.apagar();
		rojo.setIntensidad(0);
		System.out.println("Rojo apagado");
		amarillo.encender();
		amarillo.setIntensidad(5);		
		System.out.println("Amarillo encendido");
		amarillo.apagar();
		amarillo.setIntensidad(0);
		System.out.println("Amarillo apagado");
		verde.encender();
		verde.setIntensidad(5);
		System.out.println("Verde encendido");
	}

}