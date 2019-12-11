package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CrearCategoria extends AppCompatActivity {

    /*
        ACTIVIDAD PARA CREAR UNA CATEGORIA, EXCLUSIVO DE ADMINISTRADOR
     */

    private final String TAG = "CrearCategoria";
    private EditText txtField_Nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);

        txtField_Nombre = findViewById(R.id.editNombreCat);

        Button btn_crear = (Button) findViewById(R.id.button_crear);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCategoria();
            }
        }
        );


        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_home = new Intent(CrearCategoria.this, Administrador.class);
                startActivity(actividad_home);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(CrearCategoria.this, Categories.class);
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_videojuegos = (Button) findViewById(R.id.videojuegos);
        btn_videojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_games = new Intent(CrearCategoria.this, Games.class);
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

                startActivity(new Intent(CrearCategoria.this, Login.class));
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

    // SERVICIO PARA CREAR UNA NUEVA CATEGORIA
    private void crearCategoria() {
        boolean valDatos = validarDatosIngresados(txtField_Nombre);

        if (!valDatos) {
            Toast.makeText(this, "Ingresa una categoria", Toast.LENGTH_LONG).show();
        }

        else {
            String nombre = txtField_Nombre.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Toast.makeText(CrearCategoria.this, "Categoría creada", Toast.LENGTH_LONG).show();
                            Log.d(TAG, response.toString());
                            Intent intent = new Intent(CrearCategoria.this, Administrador.class);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CrearCategoria.this);
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

            RequestCreateCat registerRequest = new RequestCreateCat(nombre, responseListener);
            RequestQueue queue = Volley.newRequestQueue(CrearCategoria.this);
            queue.add(registerRequest);
        }

    }
}
