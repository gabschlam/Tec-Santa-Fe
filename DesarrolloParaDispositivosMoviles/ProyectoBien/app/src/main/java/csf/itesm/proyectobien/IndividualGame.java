package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class IndividualGame extends AppCompatActivity {

    /*
        ACTIVIDAD DE JUEGO INDIVIDUAL
     */

    private static final String TAG = "IndividualGame";

    private Videojuego miVideojuego;
    private TextView TextViewCategoria;
    private TextView TextViewVideojuego;
    private TextView TextViewDescripcion;
    private TextView TextViewCosto;
    private ImageView ImageViewUrl;

    private String imagen;
    private String idUsuario;

    private final String OBJETO_JSON_EXTRA = "objetoVideojuego";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_game);

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        idUsuario = sharedPreferences.getString("idUsuario", "");

        miVideojuego = (Videojuego) getIntent().getSerializableExtra(OBJETO_JSON_EXTRA);

        // Mapeamos componentes de la interfaz
        TextViewCategoria = (TextView) findViewById(R.id.text_categoria);
        TextViewVideojuego = (TextView) findViewById(R.id.text_videojuego);
        TextViewDescripcion = (TextView) findViewById(R.id.text_descripcion);
        TextViewCosto = (TextView) findViewById(R.id.text_costo);
        ImageViewUrl = (ImageView) findViewById(R.id.image_view_videojuego);

        final ProgressDialog barraDeProgreso = new ProgressDialog(IndividualGame.this);
        barraDeProgreso.setMessage("Cargando datos...");
        barraDeProgreso.show();

        // Definimos los valores que desplegaremos al usuario
        TextViewCategoria.setText("Categoria: " + miVideojuego.getnomCategoria());
        TextViewVideojuego.setText("Videojuego: " + miVideojuego.getNombre());
        TextViewDescripcion.setText(miVideojuego.getDescripcion() + ".");
        TextViewCosto.setText("Costo: " + miVideojuego.getCosto());
        imagen = (miVideojuego.getImagen());

        ImageLoader cargaImagen = new ImageLoader(Volley.newRequestQueue(getApplicationContext()),
                new csf.itesm.proyectobien.ImageLoader());

        cargaImagen.get(imagen, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error al cargar imagen: " + error.getMessage());
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // carga la imagen en el  imageview
                    ImageViewUrl.setImageBitmap(response.getBitmap());
                    barraDeProgreso.hide();
                }
            }
        });


        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad;

                // Validaciones para que el flujo sea al home indicado, dependiendo el rol del usuario / cliente
                if (Administrador.rol == null) {
                    actividad = new Intent(IndividualGame.this, Games.class);
                    ListaCategorias.idCategoria = null;
                    Games.showName = 1;
                } else if (Administrador.rol.equals("Admin")) {
                    actividad = new Intent(IndividualGame.this, Administrador.class);
                    ListaCategorias.idCategoria = null;

                } else {
                    actividad = new Intent(IndividualGame.this, Games.class);
                    ListaCategorias.idCategoria = null;
                    Games.showName = 1;
                }
                startActivity(actividad);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(IndividualGame.this, Categories.class);
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_carrito= (Button) findViewById(R.id.carrito);
        btn_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad;
                // Validaciones para que el administrador no tenga acceso a la ventana de carrito
                if(Administrador.rol == null){
                    actividad = new Intent(IndividualGame.this, Carrito.class);
                    startActivity(actividad);
                }
                else if(Administrador.rol.equals("Admin")) {


                }
                else {
                    actividad = new Intent(IndividualGame.this, Carrito.class);
                    startActivity(actividad);
                }
            }
        }
        );

        // Si cierra sesion, eliminamos datos de SharedPreferences
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para que se muestren todos los videojuegos al inciar sesión
                ListaCategorias.idCategoria = null;

                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.clear();
                e.commit();

                Administrador.rol = null;

                startActivity(new Intent(IndividualGame.this, Login.class));
                finish(); //Termina esta actividad
            }
        });

        Button btn_agregar = (Button) findViewById(R.id.agregar_carrito);
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validaciones para que el administrador no pueda agregar videojuegos al carrito
                if(Administrador.rol == null){
                    agregarCarrito();
                }
                else if(Administrador.rol.equals("Admin")) {


                }
                else {
                    agregarCarrito();
                }

            }
        }
        );

        barraDeProgreso.dismiss();

    }

    // SERVICIO PARA AGREGAR VIDEOJUEGO AL CARRITO
    private void agregarCarrito() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d(TAG, response.toString());

                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(IndividualGame.this, "Videojuego agreado al carrito", Toast.LENGTH_LONG).show();
                        Log.d(TAG, response.toString());
                        Intent intent = new Intent(IndividualGame.this, Games.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(IndividualGame.this);
                        dialog.setMessage("Posible error. \n Favor de checar si el videojuego ya está en el carrito.");
                        dialog.setTitle("Error");
                        dialog.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestCreateCarrito registerRequest = new RequestCreateCarrito(idUsuario, miVideojuego.getIdVideojuego(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(IndividualGame.this);
        queue.add(registerRequest);
    }


}
