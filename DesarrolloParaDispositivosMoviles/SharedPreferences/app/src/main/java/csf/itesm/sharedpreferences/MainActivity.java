package csf.itesm.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtField_Nombre, txtField_Apellido, txtField_Email, txtField_Num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtField_Nombre = findViewById(R.id.nombre);
        txtField_Apellido = findViewById(R.id.apellido);
        txtField_Email = findViewById(R.id.email);
        txtField_Num = findViewById(R.id.telefono);
    }

    public void guardar(View v) {
        String nombre = txtField_Nombre.getText().toString();
        String apellido = txtField_Apellido.getText().toString();
        String email = txtField_Email.getText().toString();
        String tel = txtField_Num.getText().toString();

        SharedPreferences preferencias = getSharedPreferences("crm", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(nombre, nombre+" "+apellido+" "+email+" "+tel);

        editor.apply();
        editor.commit();
        Toast.makeText(this, "Datos de contacto guardados", Toast.LENGTH_LONG).show();
    }

    public void recuperar(View v) {
        String nombre = txtField_Nombre.getText().toString();

        SharedPreferences preferencias = getSharedPreferences("crm", Context.MODE_PRIVATE);
        String datos = preferencias.getString(nombre, "");
        String[] arr_datos = datos.split(" ");

        if (datos.length() == 0) {
            Toast.makeText(this, "No existe este contacto en el CRM", Toast.LENGTH_LONG).show();
        }
        else {
            txtField_Apellido.setText(arr_datos[1]);
            txtField_Email.setText(arr_datos[2]);
            txtField_Num.setText(arr_datos[3]);
        }

    }

}
