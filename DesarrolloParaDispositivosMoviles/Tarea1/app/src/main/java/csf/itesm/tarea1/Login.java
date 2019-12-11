package csf.itesm.tarea1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText textoUsuario;
    private EditText textoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_ingresar = (Button) findViewById(R.id.button3);
        textoUsuario = findViewById(R.id.editText);
        textoPassword = findViewById(R.id.editText2);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_ingresar = new Intent(Login.this, Menu.class);
                actividad_ingresar.putExtra("usuario", textoUsuario.getText().toString());
                actividad_ingresar.putExtra("password", textoPassword.getText().toString());

                startActivity(actividad_ingresar);
            }
        }
        );

        Button btn_crearCuenta = (Button) findViewById(R.id.button4);

        btn_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_crearCuenta = new Intent(Login.this, CrearCuenta.class);
                startActivity(actividad_crearCuenta);
            }
        }
        );
    }
}
