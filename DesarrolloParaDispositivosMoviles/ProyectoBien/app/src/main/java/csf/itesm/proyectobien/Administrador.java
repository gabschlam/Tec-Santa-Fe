package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Administrador extends AppCompatActivity {

    /*
        ACTIVIDAD DE INICIO DEL ADMINISTRADOR
     */

    SharedPreferences sharedPreferences;
    String usuario;
    public static String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        TextView txtField_Usuario;

        // Recibiendo SharedPreferences sobre el actual inicio de sesión
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        txtField_Usuario = findViewById(R.id.txtViewUsuario);

        usuario = sharedPreferences.getString("username", "");

        txtField_Usuario.setText("Bienvenido: " + usuario);

        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_home = new Intent(Administrador.this, Administrador.class);
                startActivity(actividad_home);
            }
        }
        );

        Button btn_categorias = (Button) findViewById(R.id.categorias);
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_categ = new Intent(Administrador.this, Categories.class);
                // Para efectos de saber el rol en otras actividades
                ListaCategorias.idCategoria = null;
                rol = "Admin";
                startActivity(actividad_categ);
            }
        }
        );

        Button btn_videojuegos = (Button) findViewById(R.id.videojuegos);
        btn_videojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_games = new Intent(Administrador.this, Games.class);
                Games.showName = 1;
                ListaCategorias.idCategoria = null;
                // Para efectos de saber el rol en otras actividades
                rol = "Admin";
                startActivity(actividad_games);
            }
        }
        );

        Button btn_crearCategoria = (Button) findViewById(R.id.crearCategoria);
        btn_crearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_crearCategoria = new Intent(Administrador.this, CrearCategoria.class);
                // Para efectos de saber el rol en otras actividades
                rol = "Admin";
                startActivity(actividad_crearCategoria);
            }
        }
        );

        Button btn_crearVideojuego = (Button) findViewById(R.id.crearVideojuego);
        btn_crearVideojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_crearVideojuego = new Intent(Administrador.this, CrearVideojuego.class);
                // Para efectos de saber el rol en otras actividades
                rol = "Admin";
                startActivity(actividad_crearVideojuego);
            }
        }
        );

        Button btn_updateCategoria = (Button) findViewById(R.id.updateCategoria);
        btn_updateCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_updateCategoria = new Intent(Administrador.this, UpdateCategoria.class);
                // Para efectos de saber el rol en otras actividades
                rol = "Admin";
                startActivity(actividad_updateCategoria);
            }
        }
        );

        Button btn_updateVideojuego = (Button) findViewById(R.id.updateVideojuego);
        btn_updateVideojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_updateVideojuego = new Intent(Administrador.this, UpdateVideojuego.class);
                // Para efectos de saber el rol en otras actividades
                rol = "Admin";
                startActivity(actividad_updateVideojuego);
            }
        }
        );

        Button btn_eliminarCategoria = (Button) findViewById(R.id.eliminarCategoria);
        btn_eliminarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_eliminarCategoria = new Intent(Administrador.this, EliminarCategoria.class);
                // Para efectos de saber el rol en otras actividades
                rol = "Admin";
                startActivity(actividad_eliminarCategoria);
            }
        }
        );

        Button btn_eliminarVideojuego = (Button) findViewById(R.id.eliminarVideojuego);
        btn_eliminarVideojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_eliminarVideojuego = new Intent(Administrador.this, EliminarVideojuego.class);
                // Para efectos de saber el rol en otras actividades
                rol = "Admin";
                startActivity(actividad_eliminarVideojuego);
            }
        }
        );

        // Si cierra sesion, eliminamos datos de SharedPreferences
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.clear();
                e.commit();

                // Para efectos de saber el rol en otras actividades
                rol = null;

                startActivity(new Intent(Administrador.this, Login.class));
                finish(); //Termina esta actividad
            }
        });

    }
}
