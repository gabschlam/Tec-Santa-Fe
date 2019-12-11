package csf.itesm.basededatossimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_guardar, btn_recuperar;
    private EditText edt_nombre;
    private DBHelper databaseHelper;
    private TextView nombres;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DBHelper(this);
        nombres = findViewById(R.id.nombres);
        btn_guardar = findViewById(R.id.btn_guardar);
        btn_recuperar = findViewById(R.id.btn_recuperar);
        edt_nombre = findViewById(R.id.edit_nombres);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.agregarDetallesAuto(edt_nombre.getText().toString());
                edt_nombre.setText("");
                Toast.makeText(MainActivity.this, "Nombre almacenado!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList = databaseHelper.obtieneTodaLaListaDeAutos();
                nombres.setText("");
                nombres.setText(arrayList.get(0)); // Para quitar coma del principio
                for (int i = 1; i < arrayList.size(); i++){
                    nombres.setText(nombres.getText().toString()+", "+arrayList.get(i));
                }
            }
        });
    }
}
