package csf.itesm.objetosvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Actividad3 extends AppCompatActivity {

    // Definimos las constante donde se encunetran nuestros servicios RESTFul
    private static final String REQUEST_URL_A_IMAGEN = "http://ubiquitous.csf.itesm.mx/~raulms/images/people/Frida.jpg";
    private static final String REQUEST_URL_A_STRING = "http://ubiquitous.csf.itesm.mx/~raulms/do/REST/string.app";
    private static final String REQUEST_URL_A_OBJECTO_JSON = "http://ubiquitous.csf.itesm.mx/~raulms/do/REST/ObjetoJSON.app";
    private static final String REQUEST_URL_A_ARREGLO_JSON = "http://ubiquitous.csf.itesm.mx/~raulms/do/REST/ArregloJSON.app?count=2";

    // Definimos los componentes necesarios de la actividad actual
    ProgressDialog barradeProgreso;
    private static final String TAG = "Actividad3";
    private Button botonStringRequest;
    private Button botonObjetoJSONRequest;
    private Button botonArregloJSONRequest;
    private Button botonImagenRequest;
    private View muestraDialogo;
    private TextView muestraTextView;
    private ImageView muestraImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);

        barradeProgreso = new ProgressDialog(this);

        // Hacemos referencia a los componentes de nuestro Layout
        botonStringRequest = (Button)findViewById(R.id.boton_obten_string);
        botonObjetoJSONRequest = (Button)findViewById(R.id.boton_obten_ObjetoJSON);
        botonArregloJSONRequest = (Button)findViewById(R.id.boton_obten_ArregloJSON);
        botonImagenRequest = (Button)findViewById(R.id.boton_obten_imagen);

        // Creamos Listeners para cada boton de la interface
        botonStringRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyStringRequest(REQUEST_URL_A_STRING);
            }
        });

        // Creamos Listeners para cada boton de la interface
        botonObjetoJSONRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyJsonObjectRequest(REQUEST_URL_A_OBJECTO_JSON );
            }
        });

        // Creamos Listeners para cada boton de la interface
        botonArregloJSONRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyJsonArrayRequest(REQUEST_URL_A_ARREGLO_JSON);
            }
        });

        // Creamos Listeners para cada boton de la interface
        botonImagenRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyImageLoader(REQUEST_URL_A_IMAGEN);
            }
        });
    }

    // Metodo volleyStringRequest().
    // Aqui estamos creando una peticion de tipo String y anexamos un listener en espera un String.
    public void volleyStringRequest(String url){

        // REQUEST_TAG es utilizado para cancelar un request
        String  REQUEST_TAG = "csf.itesm.objetosvolley.StringRequest";
        barradeProgreso.setMessage("Cargando datos...");
        barradeProgreso.show();

        // Creamos un objeto StringRequest y definimos el constructor para que tome en tres parametros
        // url: la url de nuestro servicio RESTFul para hacer el request
        // El objeto Listener: de tipo anÃ³nimo, una implementaciÃ³n del Response.Listener(),
        // que tiene un metodo onRespose que recibirÃ¡ la cadena del servicio RestFul
        // Un ErrorListener Object: Igual de tipo interno anÃ³nimo, una implementaciÃ³n de onErrorResponse(VolleyError err)
        // que obtendrÃ¡ una instancia de objeto Volley Error, cabe destacar que en el metodo onResponse(),
        // estamos registrando la salida a LogCat y tambiÃ©n mostrando la respuesta recibida en un AlertDialog.
        // En el metodo onErrorResponse(), simplemente registramos el mensaje de error a LogCat (ver consola de Android Studio).
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                // Inflamos = le decimos donde poner los datos
                // Es decir, le decimos que tamaÃ±o que tienen que tener para que quepan en el layout
                LayoutInflater li = LayoutInflater.from(Actividad3.this);
                muestraDialogo = li.inflate(R.layout.activity_muestra_info, null);
                muestraTextView = (TextView)muestraDialogo.findViewById(R.id.texto_muestra_info);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Actividad3.this);
                alertDialogBuilder.setView(muestraDialogo);
                alertDialogBuilder
                        // definimos un componente que diga OK en la ventana de dialogo
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setCancelable(false)
                        .create();
                // mostramos los datos en pantalla
                muestraTextView.setText(response.toString());
                alertDialogBuilder.show();
                barradeProgreso.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error en: " + error.getMessage());
                barradeProgreso.hide();
            }
        });
        // Agregamos una peticion de tipo string queue utilizando la clase csf.itesm.objetosvolley.Singleton
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }


    // Este metodo es similar al metodo volleyStringRequest().
    // AquÃ­ estamos creando una peticiÃ³n JsonObject y el listener espera un JsonObject.
    public void volleyJsonObjectRequest(String url){
        // REQUEST_TAG es utilizado para cancelar un request
        String  REQUEST_TAG = "csf.itesm.objetosvolley.ObjectRequest";
        barradeProgreso.setMessage("Cargando datos...");
        barradeProgreso.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        LayoutInflater li = LayoutInflater.from(Actividad3.this);
                        muestraDialogo = li.inflate(R.layout.activity_muestra_info, null);
                        muestraTextView = (TextView)muestraDialogo.findViewById(R.id.texto_muestra_info);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Actividad3.this);
                        alertDialogBuilder.setView(muestraDialogo);
                        alertDialogBuilder
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .setCancelable(false)
                                .create();
                        muestraTextView.setText(response.toString());
                        alertDialogBuilder.show();
                        barradeProgreso.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error en: " + error.getMessage());
                barradeProgreso.hide();
            }
        });

        // Anexamos una peticion de tipo JsonObject a la cola
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

    // Este metodo es similar al metodo volleyStringRequest().
    // AquÃ­ estamos creando una peticiÃ³n JsonArray y el listener espera un JsonArray.
    public void volleyJsonArrayRequest(String url){
        // REQUEST_TAG es utilizado para cancelar un request
        String  REQUEST_TAG = "csf.itesm.objetosvolley.ArrayRequest";
        barradeProgreso.setMessage("Cargando datos...");
        barradeProgreso.show();

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        LayoutInflater li = LayoutInflater.from(Actividad3.this);
                        muestraDialogo = li.inflate(R.layout.activity_muestra_info, null);
                        muestraTextView = (TextView)muestraDialogo.findViewById(R.id.texto_muestra_info);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Actividad3.this);
                        alertDialogBuilder.setView(muestraDialogo);
                        alertDialogBuilder
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .setCancelable(false)
                                .create();
                        muestraTextView.setText(response.toString());
                        alertDialogBuilder.show();
                        barradeProgreso.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error en: " + error.getMessage());
                barradeProgreso.hide();
            }
        });
        // Anexamos una peticion de tipo JsonArray a la cola
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }

    // Creamos una funciÃ³n a para hacer un request de Volley para obtener imÃ¡genes
    // Obtenemos la instancia de ImageLoader desde la clase csf.itesm.objetosvolley.Singleton y usamos su metodo get() para descargar la imagen.
    // El metodo get() de ImageLoader tiene un metodo onResponse() para administrar la respuesta.
    // El tipo de respuesta del metodo es un objeto ImageContainer, por lo que para fines de demostraciÃ³n simplemente
    // estamos mostrando las imagenes en el AlertDialog.
    public void volleyImageLoader(String url){
        ImageLoader imageLoader = Singleton.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error al cargar la imagen: " + error.getMessage());
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    LayoutInflater li = LayoutInflater.from(Actividad3.this);
                    muestraDialogo = li.inflate(R.layout.activity_muestra_info, null);
                    muestraImageView = (ImageView)muestraDialogo.findViewById(R.id.muestra_image_view);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Actividad3.this);
                    alertDialogBuilder.setView(muestraDialogo);
                    alertDialogBuilder
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .setCancelable(false)
                            .create();
                    muestraImageView.setImageBitmap(response.getBitmap());
                    alertDialogBuilder.show();
                }
            }
        });
    }

    // Este metodo comprueba la entrada de la peticiÃ³n en el cachÃ©, si la peticiÃ³n ya estÃ¡ presente,
    // entonces puede manejar los datos en consecuencia y en caso de que los datos no estÃ©n presentes,
    // inicia una solicitud de red para obtenerlos
    public void volleyCacheRequest(String url){
        Cache cache = Singleton.getInstance(getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                // https://docs.oracle.com/javase/1.5.0/docs/api/java/io/DataInput.html#modified-utf-8
                String data = new String(entry.data, "UTF-8");
                // Maneja datos para su conversiÃ³n a xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }

    // El metodo ThevolleyInvalidateCache()se utiliza para invalidar la cachÃ© existente a la entrada y  en particular
    public void volleyInvalidateCache(String url){
        Singleton.getInstance(getApplicationContext()).getRequestQueue().getCache().invalidate(url, true);
    }

    //  volleyDeleteCache() se utiliza para eliminar la cachÃ© de la url.
    public void volleyDeleteCache(String url){
        Singleton.getInstance(getApplicationContext()).getRequestQueue().getCache().remove(url);
    }

    // Se utiliza para borrar toda la cachÃ©.
    public void volleyClearCache(){
        Singleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
    }
}
