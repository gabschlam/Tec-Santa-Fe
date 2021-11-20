package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EliminarVideojuego extends AppCompatActivity {

      /*
        ACTIVIDAD PARA ELIMINAR UN VIDEOJUEGO, EXCLUSIVO DE ADMINISTRADOR
     */

    // Para el Spinner de Videojuegos
    private android.widget.Spinner Spinner;
    private SpinAdapterVid adapter;
    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.videojuegosC.php";
    Videojuego[] videojuegos;

    public static String SERVICIO_DELETE;

    public static final String IDVIDEOJUEGO = "idVideojuego";

    private final String TAG = "ActVideojuego";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_videojuego);

        Spinner = findViewById(R.id.spinner_eliminar);

        // Inicializar Spinner
        videojuegos();

        Button btn_eliminar = findViewById(R.id.button_delete);
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder message = new AlertDialog.Builder(EliminarVideojuego.this);
                message.setTitle("Eliminar Videojuego");
                message.setMessage("¿Estás seguro que quieres eliminar éste videojuego?");

                message.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Videojuego videojuego = (Videojuego) Spinner.getSelectedItem();
                        eliminarVideojuego(videojuego.getIdVideojuego());
                    }

                });
                message.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
                message.create().show();
            }
        });

        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_home = new Intent(EliminarVideojuego.this, Administrador.class);
                startActivity(actividad_home);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(EliminarVideojuego.this, Categories.class);
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_videojuegos = (Button) findViewById(R.id.videojuegos);
        btn_videojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_games = new Intent(EliminarVideojuego.this, Games.class);
                //Para dejar de filtrar videojuegos por categoría
                ListaCategorias.idCategoria = null;
                startActivity(actividad_games);
            }
        }
        );

        // Si cierra sesion, eliminamos datos de SharedPreferences
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.clear();
                e.commit();

                Administrador.rol = null;

                startActivity(new Intent(EliminarVideojuego.this, Login.class));
                finish(); //Termina esta actividad
            }
        });
    }

    // FUNCION Y SERVICIO PARA INICIALIZAR EL SPINNER DE VIDEOJUEGOS
    private void videojuegos() {
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {

                        JSONObject obj=null;
                        Videojuego[] videojuegos = parserJSON.busquedaArreglo(responseArray);

                        adapter = new SpinAdapterVid(EliminarVideojuego.this,
                                android.R.layout.simple_spinner_item,
                                videojuegos);
                        Spinner.setAdapter(adapter); // Set the custom adapter to the spinner

                        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapter) {  }
                        });
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        Singleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    // SERVICIO PARA ELIMINAR EL VIDEOJUEGO
    private void eliminarVideojuego(final String idVideojuego) {

        SERVICIO_DELETE = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.d.videojuegos.php?";

        SERVICIO_DELETE = SERVICIO_DELETE + IDVIDEOJUEGO + "=" + idVideojuego;
        Log.d(TAG, SERVICIO_DELETE.toString());

        JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_DELETE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject autenticacion = (JSONObject) response.get(0);
                    String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                    //Log.d(TAG,response.toString());

                    if (codigo_autenticacion.equals("01")) {

                        Toast.makeText(EliminarVideojuego.this, "Videojuego Eliminado", Toast.LENGTH_LONG).show();
                        Log.d(TAG, response.toString());
                        Intent intent = new Intent(EliminarVideojuego.this, Administrador.class);
                        startActivity(intent);
                    } else if (codigo_autenticacion.equals("04")) {
                        Toast.makeText(EliminarVideojuego.this, "Fallo en el borrado", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Fallo en el borrado");
                    } else if (codigo_autenticacion.equals("05")) {
                        Toast.makeText(EliminarVideojuego.this, "Fallo en el borrado, datos no encontrados", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Datos no encontrados");
                    }
                } catch (JSONException e) {
                    Toast.makeText(EliminarVideojuego.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EliminarVideojuego.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(IDVIDEOJUEGO, idVideojuego);
                return map;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticion);

    }
}
