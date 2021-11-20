package csf.itesm.objetosvolley;

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

        Button btnActividad_1 = (Button) findViewById(R.id.button);
        Button btnActividad_2 = (Button) findViewById(R.id.button2);
        Button btnActividad_3 = (Button) findViewById(R.id.button3);

        btnActividad_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Una forma diferente de hacer lo mismo
                //startActivity(new Intent("mx.itesm.csf.navegacion.Actividad2"));
                // o
                //startActivity(new Intent(this, Actividad2.class));
                // o
                // startActivityForResult(new Intent(
                //    "mx.itesm.csf.navegacion.Actividad2"),
                //    codigo_Requerido);

                Intent actividad = new Intent(MainActivity.this, Actividad2.class);
                startActivity(actividad);
            }
        });

        btnActividad_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(MainActivity.this, Actividad3.class);
                startActivity(actividad);
            }
        });

        btnActividad_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(MainActivity.this, MainActivity.class);
                startActivity(actividad);
            }
        });

    }
}
