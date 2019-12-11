package csf.itesm.proyectobien;

import java.io.Serializable;

public class DatosCarrito implements Serializable {

    /*
        DATOS DEL CARRITO
     */

    private String idUsuario;
    private String idVideojuego;
    private String Nombre;
    private String Cantidad;
    private String Costo;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(String idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getCosto() {
        return Costo;
    }

    public void setCosto(String costo) {
        Costo = costo;
    }
}
