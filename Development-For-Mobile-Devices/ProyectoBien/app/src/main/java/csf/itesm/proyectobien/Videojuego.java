package csf.itesm.proyectobien;

import java.io.Serializable;

public class Videojuego implements Serializable {

     /*
        DATOS DE VIDEOJUEGOS
     */

    private String idVideojuego;
    private String Nombre;
    private String Imagen;
    private String APK;
    private String Descripcion;
    private String Costo;
    private String idCategoria;
    private String nomCategoria;

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

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getAPK() {
        return APK;
    }

    public void setAPK(String APK) {
        this.APK = APK;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCosto() {
        return Costo;
    }

    public void setCosto(String costo) {
        Costo = costo;
    }

    public String getidCategoria() {
        return idCategoria;
    }

    public void setidCategoria(String idcategoria) {
        idCategoria = idcategoria;
    }

    public String getnomCategoria() {
        return nomCategoria;
    }

    public void setnomCategoria(String nomcategoria) {
        nomCategoria = nomcategoria;
    }
}
