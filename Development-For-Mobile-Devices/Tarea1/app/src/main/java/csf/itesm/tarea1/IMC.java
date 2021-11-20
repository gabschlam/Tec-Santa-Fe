package csf.itesm.tarea1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IMC extends AppCompatActivity {

    private EditText alt;
    private EditText peso;
    private String str_res = "Tu IMC es: ";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        alt = (EditText)findViewById(R.id.alt);
        peso = (EditText)findViewById(R.id.peso);

        textView = findViewById(R.id.imc);

        Button btn_imc = (Button) findViewById(R.id.button17);

        btn_imc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double altura = Double.parseDouble(alt.getText().toString());

                double masa = Double.parseDouble(peso.getText().toString());

                double imc = masa / (altura*altura);

                textView.setText(str_res + String.format("%.2f", imc));
            }
        }
        );

        Button btn_regresar = (Button) findViewById(R.id.button18);

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_regresar = new Intent(IMC.this, Menu.class);
                startActivity(actividad_regresar);
            }
        }
        );
    }
}
