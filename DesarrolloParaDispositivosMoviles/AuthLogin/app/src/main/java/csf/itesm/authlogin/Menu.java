package csf.itesm.authlogin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    private Usuario datosUsuario;
    private Button botonMarcas;
    private Button botonAutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        TextView nombre = (TextView) findViewById(R.id.textNombre);
        nombre.setText(datosUsuario.getInstance().getNombre());
        TextView appaterno = (TextView) findViewById(R.id.textAppaterno);
        appaterno.setText(datosUsuario.getInstance().getAppaterno());
    }
}
