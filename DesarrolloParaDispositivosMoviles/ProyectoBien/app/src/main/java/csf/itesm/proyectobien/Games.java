package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;


public class Games extends AppCompatActivity{

    /*
        ACTIVIDAD DE VIDEOJUEGOS - HOME DEL USUARIO
     */

    public static Context contextOfApplication;

    SharedPreferences sharedPreferences;
    private TextView txtField_Usuario;
    private TextView txtView;
    String usuario;
    public static int showName;

    Videojuego[] videojuegos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        // Para que la actividad de Carrito pueda acceder a los datos de Shared Preferences
        contextOfApplication = getApplicationContext();

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        txtField_Usuario = findViewById(R.id.txtViewUsuario);
        txtView = findViewById(R.id.textView3);

        usuario = sharedPreferences.getString("username", "");

        txtField_Usuario.setText("Bienvenido: " + usuario);

        // Validaciones para no desplegar nombre de usuario después de regresar al home
        if (showName == 0) {
            txtField_Usuario.setVisibility(View.VISIBLE);
        }
        else {
            txtField_Usuario.setVisibility(View.GONE);
        }

        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad;

                // Validaciones para que el flujo sea al home indicado, dependiendo el rol del usuario / cliente
                if(Administrador.rol == null){
                    actividad = new Intent(Games.this, Games.class);
                    //Para dejar de filtrar videojuegos por categoría
                    ListaCategorias.idCategoria = null;
                    // No desplegar nombre de usuario
                    Games.showName = 1;
                }
                else if(Administrador.rol.equals("Admin")) {
                    actividad = new Intent(Games.this, Administrador.class);
                    //Para dejar de filtrar videojuegos por categoría
                    ListaCategorias.idCategoria = null;

                }
                else {
                    actividad = new Intent(Games.this, Games.class);
                    //Para dejar de filtrar videojuegos por categoría
                    ListaCategorias.idCategoria = null;
                    // No desplegar nombre de usuario
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

                Intent actividad = new Intent(Games.this, Categories.class);
                startActivity(actividad);
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
                    actividad = new Intent(Games.this, Carrito.class);
                    startActivity(actividad);
                }
                else if(Administrador.rol.equals("Admin")) {


                }
                else {
                    actividad = new Intent(Games.this, Carrito.class);
                    startActivity(actividad);
                }
            }
        }
        );

        // Si cierra sesion, eliminamos datos de SharedPreferences
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para que se muestren todos los videojuegos al inciar sesión
                ListaCategorias.idCategoria = null;

                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.clear();
                e.commit();

                Administrador.rol = null;

                startActivity(new Intent(Games.this, Login.class));
                finish(); //Termina esta actividad
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentoContenedor);

        if (fragment == null) {
            fragment = new ListaVideojuegos();

            fm.beginTransaction()
                    .add(R.id.fragmentoContenedor, fragment)

                    .commit();
        }
    }

    // Para que la actividad de Carrito pueda acceder a los datos de Shared Preferences
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

}
