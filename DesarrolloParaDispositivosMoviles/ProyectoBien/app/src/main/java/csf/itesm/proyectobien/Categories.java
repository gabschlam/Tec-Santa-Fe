package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {

    /*
        ACTIVIDAD DE CATEGORIAS
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Button btn_home = (Button) findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad;

                // Validaciones para que el flujo sea al home indicado, dependiendo el rol del usuario / cliente
                if(Administrador.rol == null){
                    actividad = new Intent(Categories.this, Games.class);
                    ListaCategorias.idCategoria = null;
                    Games.showName = 1;
                }
                else if(Administrador.rol.equals("Admin")) {
                    actividad = new Intent(Categories.this, Administrador.class);
                    ListaCategorias.idCategoria = null;

                }
                else {
                    actividad = new Intent(Categories.this, Games.class);
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
                Intent actividad_categ = new Intent(Categories.this, Categories.class);
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
                    actividad = new Intent(Categories.this, Carrito.class);
                    startActivity(actividad);
                }
                else if(Administrador.rol.equals("Admin")) {


                }
                else {
                    actividad = new Intent(Categories.this, Carrito.class);
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

                startActivity(new Intent(Categories.this, Login.class));
                finish(); //Termina esta actividad
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentoContenedor);

        if (fragment == null) {
            fragment = new ListaCategorias();

            fm.beginTransaction()
                    .add(R.id.fragmentoContenedor, fragment)
                    .commit();
        }
    }
}
