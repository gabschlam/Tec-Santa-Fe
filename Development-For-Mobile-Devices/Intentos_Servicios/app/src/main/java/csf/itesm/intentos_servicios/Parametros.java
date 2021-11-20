package csf.itesm.intentos_servicios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Parametros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);

        /*// RECIBIR PARAMETROS
        Toast.makeText(this,getIntent().getStringExtra("string1"),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,Integer.toString(getIntent().getIntExtra("int1", 0)),Toast.LENGTH_SHORT).show();
        */

        // RECIBIR PARAMETROS CON BUNDLE

        Bundle bundle = getIntent().getExtras();

        Toast.makeText(this, bundle.getString("string2"), Toast.LENGTH_LONG).show();
        Toast.makeText(this, Integer.toString(bundle.getInt("int2")), Toast.LENGTH_SHORT).show();

    }
}
