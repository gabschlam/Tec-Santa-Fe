package csf.itesm.tarea1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = (Button) findViewById(R.id.button2);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_login = new Intent(MainActivity.this, Login.class);
                startActivity(actividad_login);
            }
        }
        );

        Button btn_crearCuenta = (Button) findViewById(R.id.button);

        btn_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_crearCuenta = new Intent(MainActivity.this, CrearCuenta.class);
                startActivity(actividad_crearCuenta);
            }
        }
        );
    }
}
