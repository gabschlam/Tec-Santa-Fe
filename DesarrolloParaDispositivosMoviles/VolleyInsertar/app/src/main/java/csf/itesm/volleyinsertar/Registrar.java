package csf.itesm.volleyinsertar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Registrar extends AppCompatActivity {

    private final String TAG = "Registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        final EditText etNombre = (EditText) findViewById(R.id.editTxtUsuario);
        final EditText etPassword = (EditText) findViewById(R.id.editTxtPassword);
        final EditText etCorreo = (EditText) findViewById(R.id.editTxtEmail);

        final Button bRegister = (Button) findViewById(R.id.buttonReg);

        bRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String nombre = etNombre.getText().toString();
                final String password = etPassword.getText().toString();
                final String correo = etCorreo.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Toast.makeText(Registrar.this, "Datos ingresados", Toast.LENGTH_LONG).show();
                                Log.d(TAG, response.toString());
                                Intent intent = new Intent(Registrar.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder= new AlertDialog.Builder(Registrar.this);
                                builder.setMessage("Registro fallido")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RequestRegistrar registerRequest = new RequestRegistrar(nombre, password, correo, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Registrar.this);
                queue.add(registerRequest);
            }
        });

    }
}