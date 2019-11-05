/*
	Asociación con tipo de datos enum: Clase Cliente Main
	Programación Orientada a Objetos
 
	Gabriel Schlam
*/

import java.util.*;

public class ClienteMain
{
    public static void main(String[] args){
        TarjetaCredito tc = new TarjetaCredito("5224123412341234", 48000.00, 12, 10, 2015, 12, 10, 2019);
        
        Cliente c = new Cliente(1243,"Juan", "Pérez", 28);
        c.setTarjetaCredito(tc);
        
        c.printCliente();
        
        //tc.printTC();
        
        
    }
}
