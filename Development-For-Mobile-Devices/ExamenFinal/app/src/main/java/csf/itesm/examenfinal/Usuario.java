package csf.itesm.examenfinal;

public class Usuario {

    private String idLogin;
    private String Nombre;
    private String Appaterno;
    private String imagen;
    private String Clave_auto;

    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getAppaterno() {
        return Appaterno;
    }

    public void setAppaterno(String appaterno) {
        Appaterno = appaterno;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getClave_auto() {
        return Clave_auto;
    }

    public void setClave_auto(String clave_auto) {
        Clave_auto = clave_auto;
    }
}