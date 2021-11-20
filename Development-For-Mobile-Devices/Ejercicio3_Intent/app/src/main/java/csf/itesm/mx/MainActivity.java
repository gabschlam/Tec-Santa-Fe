package csf.itesm.mx;

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

        btn_actividad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad1 = new Intent(MainActivity.this, Actividad2.class);
                startActivity(actividad1);
            }
        }
        );

        btn_actividad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad2 = new Intent(MainActivity.this, Actividad3.class);
                startActivity(actividad2);
            }
        }
        );
    }
}
