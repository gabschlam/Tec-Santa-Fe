package csf.itesm.tarea1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CrearCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        Button btn_login_cuenta = (Button) findViewById(R.id.button5);

        btn_login_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_login = new Intent(CrearCuenta.this, Login.class);

                Toast toast = Toast.makeText(getApplicationContext(), "Cuenta Crada", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.RED);
                toast.show();
                startActivity(actividad_login);
            }
        }
        );

        Button btn_login = (Button) findViewById(R.id.button6);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_login = new Intent(CrearCuenta.this, Login.class);
                startActivity(actividad_login);
            }
        }
        );
    }
}
