package csf.itesm.mx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Actividad3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutactivity_actividad3);

        Button btn_main2 = (Button) findViewById(R.id.button4);

        btn_main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad = new Intent(Actividad3.this, MainActivity.class);
                startActivity(actividad);
            }
        }
        );
    }
}
