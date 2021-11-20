package csf.itesm.proyectobien;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateCat extends StringRequest {

     /*
        REQUEST DE CREACION DE CATEGORIAS
     */

    private static final String REGISTER_REQUEST_URL = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.c.categorias.php";

    private Map<String, String> params;

    public RequestCreateCat(String nombre, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Nombre", nombre);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
