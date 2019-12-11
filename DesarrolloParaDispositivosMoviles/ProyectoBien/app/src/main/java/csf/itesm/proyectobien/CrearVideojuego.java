package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CrearVideojuego extends AppCompatActivity{

    /*
        ACTIVIDAD PARA CREAR UNA CATEGORIA, EXCLUSIVO DE ADMINISTRADOR
     */

    // Para el Spinner de las categorias
    private Spinner Spinner;
    private SpinAdapterCat adapter;
    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.categorias.php";
    Categoria[] categorias;

    private final static String TAG = "CrearVideojuego";

    private EditText txtField_Nombre, txtField_Descripcion, txtField_Imagen, txtField_APK, txtField_Costo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_videojuego);
        Spinner = findViewById(R.id.spinner);

        // Inicializacion del spinner de categorías
        categorias();

        txtField_Nombre = findViewById(R.id.editNombre);
        txtField_Descripcion = findViewById(R.id.editDesct);
        txtField_Imagen = findViewById(R.id.editImagen);
        txtField_APK = findViewById(R.id.editAPK);
        txtField_Costo = findViewById(R.id.editCosto);

        Button btn_crear = findViewById(R.id.button_crear);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categoria categoria = (Categoria) Spinner.getSelectedItem();
                crearVideojuego(categoria.getIdCategoria());
            }
        });

        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_home = new Intent(CrearVideojuego.this, Administrador.class);
                startActivity(actividad_home);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(CrearVideojuego.this, Categories.class);
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_videojuegos = (Button) findViewById(R.id.videojuegos);
        btn_videojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_games = new Intent(CrearVideojuego.this, Games.class);
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

                startActivity(new Intent(CrearVideojuego.this, Login.class));
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

    // FUNCION Y SERVICIO PARA INICIALIZAR EL SPINNER DE CATEGORIAS
    private void categorias() {
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                            Categoria[] categorias = parserJSONCategorias.arreglo(responseArray);

                            adapter = new SpinAdapterCat(CrearVideojuego.this,
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

        Singleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    // SERVICIO PARA CREAR VIDEOJUEGO
    private void crearVideojuego(String idCategoria) {
        boolean valNombre = validarDatosIngresados(txtField_Nombre);
        boolean valDescripcion = validarDatosIngresados(txtField_Descripcion);
        boolean valImagen = validarDatosIngresados(txtField_Imagen);
        boolean valAPK = validarDatosIngresados(txtField_APK);
        boolean valCosto = validarDatosIngresados(txtField_Costo);

        if (!valNombre || !valDescripcion || !valImagen || !valCosto || !valAPK) {
            Toast.makeText(this, "Llena todos los datos", Toast.LENGTH_LONG).show();
        }

        else {
            String nombre = txtField_Nombre.getText().toString();
            String descripcion = txtField_Descripcion.getText().toString();
            String imagen = txtField_Imagen.getText().toString();
            String apk = txtField_APK.getText().toString();
            String costo = txtField_Costo.getText().toString();

            // ELIMINAR CUALQUIER NUEVA LINEA
            if (descripcion.contains("\n")) {
                descripcion = descripcion.replaceAll("\n", " ");
            }
            if (imagen.contains("\n")) {
                imagen = imagen.replaceAll("\n", " ");
            }
            if (apk.contains("\n")) {
                apk = apk.replaceAll("\n", " ");
            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Toast.makeText(CrearVideojuego.this, "Videojuego creado", Toast.LENGTH_LONG).show();
                            Log.d(TAG, response.toString());
                            Intent intent = new Intent(CrearVideojuego.this, Administrador.class);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CrearVideojuego.this);
                            builder.setMessage("Registro fallido")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RequestCreateVid registerRequest = new RequestCreateVid(nombre, descripcion, imagen, apk, costo, idCategoria, responseListener);
            RequestQueue queue = Volley.newRequestQueue(CrearVideojuego.this);
            queue.add(registerRequest);
        }

    }
}
