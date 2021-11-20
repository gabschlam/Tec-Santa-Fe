/*
    Listas Dobles: Clase Canción
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Cancion {
    private String titulo;
    private String artista;
    private Cancion siguiente;
    private Cancion previo;
    private Cancion ultimo;

    public Cancion(String titulo, String artista) {
        this.titulo = titulo;
        this.artista = artista;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
    
    public Cancion getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Cancion siguiente) {
        this.siguiente = siguiente;
    }

    public Cancion getPrevio() {
        return previo;
    }

    public void setPrevio(Cancion previo) {
        this.previo = previo;
    }
 
    @Override
    public String toString() {
        return "Titulo: " + titulo + ". Artista: " + artista;
    }
    
}
