package csf.itesm.proyectobien;

import java.io.Serializable;

public class Categoria implements Serializable {

    /*
        DATOS DE CATEGORIA
     */

    private String idCategoria;
    private String Nombre;

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
