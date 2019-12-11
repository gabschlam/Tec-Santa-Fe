package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Carrito extends AppCompatActivity {

    /*
        ACTIVIDAD PARA VISUALIZAR EL CARRITO SIN COMPRAR DEL USUARIO
     */

    private static final String TAG = "Carrito";
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // Recibiendo SharedPreferences sobre el actual inicio de sesión
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        // idUsuario para servicios JSON
        idUsuario = sharedPreferences.getString("idUsuario", "");

        Button btn_comprar= (Button) findViewById(R.id.comprar);
        btn_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprar();
            }
        }
        );

        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_home = new Intent(Carrito.this, Games.class);
                startActivity(actividad_home);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(Carrito.this, Categories.class);
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_carrito= (Button) findViewById(R.id.carrito);
        btn_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_carrito = new Intent(Carrito.this, Carrito.class);
                startActivity(actividad_carrito);
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

                startActivity(new Intent(Carrito.this, Login.class));
                finish(); //Termina esta actividad
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentoContenedor);

        if (fragment == null) {
            fragment = new ListaCarrito();

            fm.beginTransaction()
                    .add(R.id.fragmentoContenedor, fragment)

                    .commit();
        }
    }

    // SERVICIO PARA COMPRAR ARTICULOS DENTRO DEL CARRITO
    private void comprar() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d(TAG, response.toString());

                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(Carrito.this, "Compra realizada", Toast.LENGTH_LONG).show();
                        Log.d(TAG, response.toString());
                        Intent intent = new Intent(Carrito.this, Compra_Descarga.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Carrito.this, "Fallo en la compra", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Fallo en la compra");
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestComprar registerRequest = new RequestComprar(idUsuario, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Carrito.this);
        queue.add(registerRequest);
    }
}
