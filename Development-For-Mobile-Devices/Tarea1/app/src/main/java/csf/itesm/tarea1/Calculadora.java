package csf.itesm.tarea1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Calculadora extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private String str_res = "Resultado: ";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        num1 = (EditText)findViewById(R.id.num1);
        num2 = (EditText)findViewById(R.id.num2);

        textView = findViewById(R.id.resultado);

        Button btn_sum = (Button) findViewById(R.id.button12);

        btn_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double n1 = Double.parseDouble(num1.getText().toString());

                double n2 = Double.parseDouble(num2.getText().toString());

                double sum = n1 + n2;

                textView.setText(str_res + String.format("%.2f", sum));
            }
        }
        );

        Button btn_rest = (Button) findViewById(R.id.button13);

        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double n1 = Double.parseDouble(num1.getText().toString());

                double n2 = Double.parseDouble(num2.getText().toString());

                double rest = n1 - n2;

                textView.setText(str_res + String.format("%.2f", rest));
            }
        }
        );

        Button btn_div = (Button) findViewById(R.id.button14);

        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double n1 = Double.parseDouble(num1.getText().toString());

                double n2 = Double.parseDouble(num2.getText().toString());

                double div = n1 / n2;

                textView.setText(str_res + String.format("%.2f", div));
            }
        }
        );

        Button btn_mult = (Button) findViewById(R.id.button15);

        btn_mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double n1 = Double.parseDouble(num1.getText().toString());

                double n2 = Double.parseDouble(num2.getText().toString());

                double mult = n1 * n2;

                textView.setText(str_res + String.format("%.2f", mult));
            }
        }
        );

        Button btn_regresar = (Button) findViewById(R.id.button16);

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_regresar = new Intent(Calculadora.this, Menu.class);
                startActivity(actividad_regresar);
            }
        }
        );
    }
}
