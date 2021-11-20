package csf.itesm.volleyintro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enviarPeticion(View view) {
        // Mapeamos el componente a utilizar
        final TextView textView = findViewById(R.id.textView);
        // Creamos una cola de volley
        RequestQueue cola = Volley.newRequestQueue(this);
        // Definimos la url a donde vamos a realizar la peticion
        String url = "http://ubiquitous.csf.itesm.mx";
        // Creamos el request de volley utilizando el metodo GET para anexarlo a la cola
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // Solo hacemos un request a un string y deplegamos solo 500 caracteres
                    @Override
                    public void onResponse(String response) {
                        textView.setText("Respuesta de petición: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Algo sucedió con la petición: " + error.getMessage());
            }
        });
        // Agregamos la peticion a la cola
        cola.add(stringRequest);
    }
}
