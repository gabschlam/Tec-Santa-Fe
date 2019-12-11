package csf.itesm.proyectobien;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateVid extends StringRequest {

     /*
        REQUEST DE CREACION DE VIDEOJUEGO
     */

    private static final String REGISTER_REQUEST_URL = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.c.videojuegos.php";

    private Map<String, String> params;

    public RequestCreateVid(String nombre, String descripcion, String imagen, String apk, String  costo, String idCategoria, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Nombre", nombre);
        params.put("Descripcion", descripcion);
        params.put("Imagen", imagen);
        params.put("APK", apk);
        params.put("Costo", costo);
        params.put("idCategoria", idCategoria);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
