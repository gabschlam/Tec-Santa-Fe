package csf.itesm.mx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Actividad2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutactivity_actividad2);

        Button btn_main = (Button) findViewById(R.id.button3);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad = new Intent(Actividad2.this, MainActivity.class);
                startActivity(actividad);
            }
        }
        );

    }
}
