package csf.itesm.proyectobien;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateCarrito extends StringRequest {

      /*
        REQUEST DE CREACION DE CARRITO
     */

    private static final String REGISTER_REQUEST_URL = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.c.carrito.php";

    private Map<String, String> params;

    public RequestCreateCarrito(String idUsuario, String idVideojuego, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idUsuario", idUsuario);
        params.put("idVideojuego", idVideojuego);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
