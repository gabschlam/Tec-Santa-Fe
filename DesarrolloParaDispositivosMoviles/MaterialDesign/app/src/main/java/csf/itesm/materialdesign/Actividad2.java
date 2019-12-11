package csf.itesm.materialdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Actividad2 extends AppCompatActivity {

    String[] marcas = {"Honda", "Toyota", "Ford", "Nissan", "Mini", "Mazda"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, marcas);

        AutoCompleteTextView textView = findViewById(R.id.txtMarcas);

        textView.setThreshold(2);
        textView.setAdapter(adaptador);
    }
}
