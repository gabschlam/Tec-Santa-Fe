package csf.itesm.proyectobien;

import java.io.Serializable;

public class DatosCompra implements Serializable {

    /*
        DATOS DE COMPRA
     */

    private String idUsuario;
    private String idVideojuego;
    private String Nombre;
    private String Fecha;
    private String APK;

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

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getAPK() {
        return APK;
    }

    public void setAPK(String APK) {
        this.APK = APK;
    }
}
