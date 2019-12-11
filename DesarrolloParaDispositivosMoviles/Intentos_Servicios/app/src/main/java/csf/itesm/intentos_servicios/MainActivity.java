package csf.itesm.intentos_servicios;

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

        Button btn_actividad1 = (Button) findViewById(R.id.button);
        Button btn_actividad2 = (Button) findViewById(R.id.button2);
        Button btn_actividad3 = (Button) findViewById(R.id.button8);

        btn_actividad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad1 = new Intent(MainActivity.this, Intentos.class);
                startActivity(actividad1);
            }
        }
        );

        btn_actividad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad2 = new Intent(MainActivity.this, Parametros.class);
                // MANDAR PARAMETROS
                actividad2.putExtra("string1", "Esto es un string");
                actividad2.putExtra("int1",20);
                startActivity(actividad2);
            }
        }
        );

        btn_actividad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad2 = new Intent(MainActivity.this, Parametros.class);
                // MANDAR PARAMETROS CON BUNDLE (EN UN SOLO ARREGLO)
                Bundle extras = new Bundle();

                extras.putString("string2", "Esto es un string con bundle");
                extras.putInt("int2", 30);

                actividad2.putExtras(extras);
                startActivity(actividad2);
            }
        }
        );
    }
}
