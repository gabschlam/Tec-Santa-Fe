package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    /*
        ACTIVIDAD DE REGISTRO
     */

    private EditText txtField_Nombre, txtField_Apellido, txtField_Correo, txtField_Usuario, txtField_Password, txtField_RePassword;
    private DBHelper databaseHelper;

    private final String TAG = "Registro";
    private final String ROL = "Cliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DBHelper(this);

        txtField_Nombre = findViewById(R.id.editNombre);
        txtField_Apellido = findViewById(R.id.editApellido);
        txtField_Correo = findViewById(R.id.editCorreo);
        txtField_Usuario = findViewById(R.id.editUsuario);
        txtField_Password = findViewById(R.id.editPass);
        txtField_RePassword = findViewById(R.id.editRePass);

    }

    // METODO Y SERVICIO PARA CREAR CUENTA
    public void crearCuenta(View v) {
        boolean valNombre = validarDatosIngresados(txtField_Nombre);
        boolean valApellido = validarDatosIngresados(txtField_Apellido);
        boolean valCorreo = validarDatosIngresados(txtField_Correo);
        boolean valUuario = validarDatosIngresados(txtField_Usuario);
        boolean valPassword = validarDatosIngresados(txtField_Password);
        boolean valRePassword = validarDatosIngresados(txtField_RePassword);

        if (!valNombre || !valApellido || !valCorreo || !valUuario || !valPassword || !valRePassword) {
            Toast.makeText(this, "Llena todos los datos", Toast.LENGTH_LONG).show();
        }

        else if (txtField_Correo.getText().toString().trim().length() < 6) {
            txtField_Correo.setError("Escriba un correo válido");
            txtField_Correo.requestFocus();
        }

        else if (txtField_Password.getText().toString().trim().length() < 6) {
            txtField_Password.setError("El password debe tener al menos 6 caracteres");
            txtField_Password.requestFocus();
        }

        else
        {
            String nombre = txtField_Nombre.getText().toString();
            String apellido = txtField_Apellido.getText().toString();
            String correo = txtField_Correo.getText().toString();
            String usuario = txtField_Usuario.getText().toString();
            String password = txtField_Password.getText().toString();
            String rePassword = txtField_RePassword.getText().toString();

            if (!password.matches(rePassword))
            {
                txtField_RePassword.setError("La contraseña no coincide");;

                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setMessage("Las contraseñas no coinciden");
                dialog.setTitle("Contraseña");
                dialog.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }

            // Después de validaciones, se manda petición
            else
            {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Toast.makeText(Register.this, "Cuenta creada", Toast.LENGTH_LONG).show();
                                Log.d(TAG, response.toString());
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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

                RequestRegLogin registerRequest = new RequestRegLogin(usuario, password, ROL, correo, nombre, apellido, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);

            }

        }
    }

    private boolean validarDatosIngresados(EditText txtField) {
        if (txtField.getText().toString().trim().equals("")) {
            txtField.setError("Este campo no puede quedar vacio");
            return false;
        }
        else {
            return true;
        }
    }
}
