package csf.itesm.tarea1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        TextView textView = findViewById(R.id.nombre);
        textView.setText("Bienvenido " + usuario);

        Button btn_calc = (Button) findViewById(R.id.button7);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_calc = new Intent(Menu.this, Calculadora.class);
                startActivity(actividad_calc);
            }
        }
        );

        Button btn_imc = (Button) findViewById(R.id.button8);

        btn_imc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_calc = new Intent(Menu.this, IMC.class);
                startActivity(actividad_calc);
            }
        }
        );

        Button btn_salir = (Button) findViewById(R.id.button11);

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        }
        );
    }
}
