package csf.itesm.proyectobien;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestComprar extends StringRequest {

    /*
        REQUEST DE COMPRA
     */

    private static final String REGISTER_REQUEST_URL = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.comprarUsuario.php";

    private Map<String, String> params;

    public RequestComprar(String idUsuario, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idUsuario", idUsuario);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
