package csf.itesm.materialdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // En vez de tipo Button, se pone tipo ButtonRectangle
        ButtonRectangle btn_1 = findViewById(R.id.button);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_login = new Intent(MainActivity.this, Actividad2.class);
                startActivity(actividad_login);

                Toast toast = Toast.makeText(getApplicationContext(), "Botón pulsado", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
    }
}
