package csf.itesm.proyectobien;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestRegLogin extends StringRequest {

     /*
        REQUEST DE CREACION DE USUARIO
     */

    private static final String REGISTER_REQUEST_URL = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.register.php";

    private Map<String, String> params;

    public RequestRegLogin(String usuario, String password, String rol, String email, String nombre, String apellido, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuario", usuario);
        params.put("password", password);
        params.put("rol", rol);
        params.put("email", email);
        params.put("nombre", nombre);
        params.put("apellido", apellido);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
