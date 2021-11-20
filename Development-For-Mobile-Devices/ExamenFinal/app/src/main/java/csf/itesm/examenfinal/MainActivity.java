package csf.itesm.examenfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_actualizar = (Button) findViewById(R.id.button_update);
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_actualizar = new Intent(MainActivity.this, ActualizarDatos.class);
                startActivity(actividad_actualizar);
            }
        }
        );

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentoContenedor);

        if (fragment == null) {
            fragment = new ListaUsuario();

            fm.beginTransaction()
                    .add(R.id.fragmentoContenedor, fragment)

                    .commit();
        }
    }
}
