package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateCategoria extends AppCompatActivity {

     /*
        ACTIVIDAD PARA ACTUALIZAR UNA CATEGORIA, EXCLUSIVO DE ADMINISTRADOR
     */

    // Para el Spinner de Categorías
    private android.widget.Spinner Spinner;
    private SpinAdapterCat adapter;
    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.categorias.php";
    Categoria[] categorias;

    public static String SERVICIO_UPDATE;

    public static final String IDCATEGORIA = "idCategoria";
    public static final String NOMBRE = "Nombre";

    private final String TAG = "ActCategoria";
    private EditText editTextNombre;

    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_categoria);
        Spinner = findViewById(R.id.spinner_update);

        // Inicializar Spinner
        categorias();

        editTextNombre = (EditText) findViewById(R.id.act_categoria);

        Button btn_update = findViewById(R.id.button_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categoria categoria = (Categoria) Spinner.getSelectedItem();
                actualizarCategoria(categoria.getIdCategoria());
            }
        });


        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_home = new Intent(UpdateCategoria.this, Administrador.class);
                startActivity(actividad_home);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(UpdateCategoria.this, Categories.class);
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_videojuegos = (Button) findViewById(R.id.videojuegos);
        btn_videojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_games = new Intent(UpdateCategoria.this, Games.class);
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

                startActivity(new Intent(UpdateCategoria.this, Login.class));
                finish(); //Termina esta actividad
            }
        });
    }

    private boolean validarDatosIngresados(EditText txtField) {
        if (txtField.getText().toString().matches("")) {
            txtField.setError("Ingresa una categoría");;
            txtField.requestFocus();

            return false;
        }
        else {
            return true;
        }
    }

    // FUNCION Y SERVICIO PARA INICIALIZAR EL SPINNER DE CATEGORIAS
    private void categorias() {
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {

                        JSONObject obj=null;
                        Categoria[] categorias = parserJSONCategorias.arreglo(responseArray);

                        adapter = new SpinAdapterCat(UpdateCategoria.this,
                                android.R.layout.simple_spinner_item,
                                categorias);
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

    // SERVICIO PARA ACTUALIZAR LA CATEGORIA
    private void actualizarCategoria(final String idCategoria) {

        boolean valDatos = validarDatosIngresados(editTextNombre);

        if (!valDatos) {
            Toast.makeText(this, "Ingresa una categoria", Toast.LENGTH_LONG).show();
        }

        else {
            SERVICIO_UPDATE = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.u.categorias.php?";
            nombre = editTextNombre.getText().toString().trim();

            SERVICIO_UPDATE = SERVICIO_UPDATE + IDCATEGORIA + "=" + idCategoria + "&" + NOMBRE + "=" + nombre;
            Log.d(TAG, SERVICIO_UPDATE.toString());

            JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_UPDATE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject autenticacion = (JSONObject) response.get(0);
                        String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                        //Log.d(TAG,response.toString());

                        if (codigo_autenticacion.equals("01")) {

                            Toast.makeText(UpdateCategoria.this, "Categoría Actualizada", Toast.LENGTH_LONG).show();
                            Log.d(TAG, response.toString());
                            Intent intent = new Intent(UpdateCategoria.this, Administrador.class);
                            startActivity(intent);
                        } else if (codigo_autenticacion.equals("04")) {
                            Toast.makeText(UpdateCategoria.this, "Fallo al intentar actualizar el registro", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Fallo al intentar actualizar el registro");
                        } else if (codigo_autenticacion.equals("05")) {
                            Toast.makeText(UpdateCategoria.this, "Fallo en la actualizacion, datos no encontrados", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Datos no encontrados");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(UpdateCategoria.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UpdateCategoria.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(IDCATEGORIA, idCategoria);
                    map.put(NOMBRE, nombre);
                    return map;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(peticion);
        }
    }
}
