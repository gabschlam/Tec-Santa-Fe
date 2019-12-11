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

public class UpdateVideojuego extends AppCompatActivity {

    /*
        ACTIVIDAD PARA ACTUALIZAR UNA CATEGORIA, EXCLUSIVO DE ADMINISTRADOR
     */

    // Para el Spinner de Videojuegos
    private android.widget.Spinner SpinnerVid;
    // Para el Spinner de Categorías
    private android.widget.Spinner SpinnerCat;
    private SpinAdapterVid adapter_vid;
    private SpinAdapterCat adapter_cat;
    String url_vid = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.videojuegosC.php";
    String url_cat = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.categorias.php";
    Videojuego[] videojuegos;

    private EditText txtField_Nombre, txtField_Descripcion, txtField_Imagen, txtField_APK, txtField_Costo;

    public static String SERVICIO_UPDATE;

    public static final String IDVIDEOJUEGO = "idVideojuego";
    public static final String NOMBRE = "Nombre";
    public static final String DESCRIPCION = "Descripcion";
    public static final String IMAGEN = "Imagen";
    public static final String APK = "APK";
    public static final String COSTO = "Costo";
    public static final String IDCATEGORIA = "idCategoria";

    private final String TAG = "ActVideojuego";

    private String nombre, descripcion, imagen, apk, costo, idVideojuego, idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_videojuego);

        SpinnerVid = findViewById(R.id.spinnerVid);
        SpinnerCat = findViewById(R.id.spinner_update);

        txtField_Nombre = findViewById(R.id.editUpdNombre);
        txtField_Descripcion = findViewById(R.id.editUpdDesct);
        txtField_Imagen = findViewById(R.id.editUpdImagen);
        txtField_APK = findViewById(R.id.editUpdAPK);
        txtField_Costo = findViewById(R.id.editUpdCosto);

        // Inicializar Spinner de Categorias
        categorias();

        // Inicializar Spinner de Videojuegos
        videojuegos();

        Button btn_update = findViewById(R.id.update_vid);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Videojuego videojuego = (Videojuego) SpinnerVid.getSelectedItem();
                Categoria categoria = (Categoria) SpinnerCat.getSelectedItem();

                idVideojuego = videojuego.getIdVideojuego();
                idCategoria = categoria.getIdCategoria();

                actualizarVideojuego(idVideojuego, idCategoria);
            }
        });

        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_home = new Intent(UpdateVideojuego.this, Administrador.class);
                startActivity(actividad_home);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(UpdateVideojuego.this, Categories.class);
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_videojuegos = (Button) findViewById(R.id.videojuegos);
        btn_videojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_games = new Intent(UpdateVideojuego.this, Games.class);
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

                startActivity(new Intent(UpdateVideojuego.this, Login.class));
                finish(); //Termina esta actividad
            }
        });
    }

    private boolean validarDatosIngresados(EditText txtField) {
        if (txtField.getText().toString().matches("")) {
            txtField.setError("Este campo no puede quedar vacio");;
            return false;
        }
        else {
            return true;
        }
    }

    // FUNCION Y SERVICIO PARA INICIALIZAR EL SPINNER DE VIDEOJUEGOS
    private void videojuegos() {
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url_vid, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {

                        JSONObject obj=null;
                        Videojuego[] videojuegos = parserJSON.busquedaArreglo(responseArray);

                        adapter_vid = new SpinAdapterVid(UpdateVideojuego.this,
                                android.R.layout.simple_spinner_item,
                                videojuegos);
                        SpinnerVid.setAdapter(adapter_vid); // Set the custom adapter to the spinner

                        SpinnerVid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {
                                if (position > 0)
                                {
                                    // Se reemplaza el texto de los textfields con el actual, para facilitar actualización
                                    Videojuego videojuego = (Videojuego) SpinnerVid.getSelectedItem();
                                    txtField_Nombre.setText(videojuego.getNombre());
                                    txtField_Imagen.setText(videojuego.getImagen());
                                    txtField_Descripcion.setText(videojuego.getDescripcion());
                                    txtField_APK.setText(videojuego.getAPK());
                                    txtField_Costo.setText(videojuego.getCosto());

                                    int spinnerPosition = adapter_cat.getIndex(videojuego.getnomCategoria());

                                    //set the default according to value
                                    SpinnerCat.setSelection(spinnerPosition);

                                }
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

    // FUNCION Y SERVICIO PARA INICIALIZAR EL SPINNER DE CATEGORIAS
    private void categorias() {
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url_cat, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {

                        JSONObject obj=null;
                        Categoria[] categorias = parserJSONCategorias.arreglo(responseArray);

                        adapter_cat = new SpinAdapterCat(UpdateVideojuego.this,
                                android.R.layout.simple_spinner_item,
                                categorias);
                        SpinnerCat.setAdapter(adapter_cat); // Set the custom adapter to the spinner

                        SpinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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


    // SERVICIO PARA ACTUALIZAR EL VIDEOJUEGO
    private void actualizarVideojuego(final String idVideojuego, final String idCategoria) {

        boolean valNombre = validarDatosIngresados(txtField_Nombre);
        boolean valDescripcion = validarDatosIngresados(txtField_Descripcion);
        boolean valImagen = validarDatosIngresados(txtField_Imagen);
        boolean valCosto = validarDatosIngresados(txtField_Costo);

        if (!valNombre || !valDescripcion || !valImagen || !valCosto) {
            Toast.makeText(this, "Llena todos los datos", Toast.LENGTH_LONG).show();
        }

        else {
            SERVICIO_UPDATE = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.u.videojuegos.php?";
            nombre = txtField_Nombre.getText().toString();
            descripcion = txtField_Descripcion.getText().toString();
            imagen = txtField_Imagen.getText().toString();
            apk = txtField_APK.getText().toString();
            costo = txtField_Costo.getText().toString();

            SERVICIO_UPDATE = SERVICIO_UPDATE + IDVIDEOJUEGO + "=" + idVideojuego + "&" + NOMBRE + "=" + nombre + "&" + DESCRIPCION + "=" + descripcion + "&" + IMAGEN + "=" + imagen + "&" + APK + "=" + apk + "&" + COSTO + "=" + costo + "&" + IDCATEGORIA + "=" + idCategoria;
            Log.d(TAG, SERVICIO_UPDATE.toString());

            JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_UPDATE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject autenticacion = (JSONObject) response.get(0);
                        String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                        //Log.d(TAG,response.toString());

                        if (codigo_autenticacion.equals("01")) {

                            Toast.makeText(UpdateVideojuego.this, "Videojuego Actualizado", Toast.LENGTH_LONG).show();
                            Log.d(TAG, response.toString());
                            Intent intent = new Intent(UpdateVideojuego.this, Administrador.class);
                            startActivity(intent);
                        } else if (codigo_autenticacion.equals("04")) {
                            Toast.makeText(UpdateVideojuego.this, "Fallo al intentar actualizar el registro", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Fallo al intentar actualizar el registro");
                        } else if (codigo_autenticacion.equals("05")) {
                            Toast.makeText(UpdateVideojuego.this, "Fallo en la actualizacion, datos no encontrados", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Datos no encontrados");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(UpdateVideojuego.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UpdateVideojuego.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(NOMBRE, nombre);
                    map.put(DESCRIPCION, descripcion);
                    map.put(IMAGEN, imagen);
                    map.put(APK, apk);
                    map.put(COSTO, costo);
                    map.put(IDCATEGORIA, idCategoria);

                    return map;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(peticion);
        }
    }

}
