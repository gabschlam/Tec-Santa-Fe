package csf.itesm.volleyinsertar;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestRegistrar extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/Actividades/19112019/servicio.c.login.php";

    private Map<String, String> params;

    public RequestRegistrar(String usuario, String password, String email, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuario", usuario);
        params.put("password", password);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
