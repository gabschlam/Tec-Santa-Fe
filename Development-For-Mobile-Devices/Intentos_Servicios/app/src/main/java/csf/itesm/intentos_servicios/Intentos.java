package csf.itesm.intentos_servicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Intentos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intentos);

        // ABRIR NAVEGADOR
        Button btn_actividad1 = (Button) findViewById(R.id.button3);
        btn_actividad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad = new Intent(Intent.ACTION_VIEW);
                actividad.setData(Uri.parse("http://www.google.com"));
                startActivity(actividad);
            }
        }
        );

        // BUSQUEDA EN NAVEGADOR
        Button btn_actividad2 = (Button) findViewById(R.id.button4);
        btn_actividad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad = new Intent(Intent.ACTION_WEB_SEARCH);
                actividad.putExtra(SearchManager.QUERY,"http://www.youtube.com");
                startActivity(actividad);
            }
        }
        );

        // ACCESO A TECLADO PARA LLAMAR
        Button btn_actividad3 = (Button) findViewById(R.id.button5);
        btn_actividad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad3 = new Intent(Intent.ACTION_DIAL);
                startActivity(actividad3);
            }
        }
        );

        // ACCESO A LLAMADA
        Button btn_actividad4 = (Button) findViewById(R.id.button6);
        btn_actividad4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telefono = "55-5252-9898";
                Intent actividad4 = new Intent(Intent.ACTION_CALL);
                actividad4.setData(Uri.parse("tel:" + telefono));
                actividad4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(ActivityCompat.checkSelfPermission(Intentos.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(actividad4);
            }
        }
        );

        // ACCESO A STREET VIEW
        Button btn_actividad5 = (Button) findViewById(R.id.button7);
        btn_actividad5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad5 = new Intent(Intent.ACTION_VIEW);
                actividad5.setData(Uri.parse("google.streetview:cbll=19.3614317,-99.2673668"));
                startActivity(actividad5);
            }
        }
        );
    }
}
