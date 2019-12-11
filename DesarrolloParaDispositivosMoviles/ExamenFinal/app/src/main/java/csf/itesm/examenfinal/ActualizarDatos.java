package csf.itesm.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarDatos extends AppCompatActivity {

    private EditText txtField_idUsuario, txtField_Nombre, txtField_Apellido, txtField_Edad, txtField_Imagen, txtField_ClaveAuto;

    public static String SERVICIO_UPDATE;

    public static final String IDUSUARIO = "id_usuario";
    public static final String NOMBRE = "Nombre";
    public static final String APPPATERNO = "appaterno";
    public static final String EDAD = "edad";
    public static final String IMAGEN = "imagen";
    public static final String CLAVEAUTO = "clave_auto";

    private final String TAG = "ActualizarDatos";

    private String idUsuario, nombre, apellido, edad, imagen, claveAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);

        txtField_idUsuario = findViewById(R.id.editID);
        txtField_Nombre = findViewById(R.id.editNombre);
        txtField_Apellido = findViewById(R.id.editApellido);
        txtField_Edad = findViewById(R.id.editEdad);
        txtField_Imagen = findViewById(R.id.editImagen);
        txtField_ClaveAuto = findViewById(R.id.editClaveAuto);

        Button btn_update = findViewById(R.id.button_act);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actualizarDatos();
            }
        });
    }

    private void actualizarDatos() {

        boolean valUsuario = validarDatosIngresados(txtField_idUsuario);
        boolean valNombre = validarDatosIngresados(txtField_Nombre);
        boolean valApellido = validarDatosIngresados(txtField_Apellido);
        boolean valEdad = validarDatosIngresados(txtField_Edad);
        boolean valImagen = validarDatosIngresados(txtField_Imagen);
        boolean valClaveAuto = validarDatosIngresados(txtField_ClaveAuto);

        if (!valUsuario || !valNombre || !valApellido || !valEdad || !valImagen || !valClaveAuto) {
            Toast.makeText(this, "Llena todos los datos", Toast.LENGTH_LONG).show();
        }

        else {
            SERVICIO_UPDATE = "http://ubiquitous.csf.itesm.mx/~raulms/content/TC2024/REST/.Examen/servicio.update.usuario.php?";

            idUsuario = txtField_idUsuario.getText().toString();
            nombre = txtField_Nombre.getText().toString();
            apellido = txtField_Apellido.getText().toString();
            edad = txtField_Edad.getText().toString();
            imagen = txtField_Imagen.getText().toString();
            claveAuto = txtField_ClaveAuto.getText().toString();

            SERVICIO_UPDATE = SERVICIO_UPDATE + IDUSUARIO + "=" + idUsuario + "&" + NOMBRE + "=" + nombre + "&"
                    + APPPATERNO + "=" + apellido + "&" + EDAD + "=" + edad + "&" + IMAGEN + "=" + imagen + "&"
                    + CLAVEAUTO + "=" + claveAuto;

            Log.d(TAG, SERVICIO_UPDATE.toString());

            JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_UPDATE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject autenticacion = (JSONObject) response.get(0);
                        String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                        Log.d(TAG,response.toString());

                        if (codigo_autenticacion.equals("1")) {
                            Toast.makeText(ActualizarDatos.this, "Registro Actualizado", Toast.LENGTH_LONG).show();

                        } else if (codigo_autenticacion.equals("4")) {
                            Toast.makeText(ActualizarDatos.this, "Fallo al intentar actualizar el registro", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Fallo al intentar actualizar el registro");
                        } else if (codigo_autenticacion.equals("5")) {
                            Toast.makeText(ActualizarDatos.this, "Fallo en la actualizacion, datos no encontrados", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Datos no encontrados");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(ActualizarDatos.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActualizarDatos.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(IDUSUARIO, idUsuario);
                    map.put(NOMBRE, nombre);
                    map.put(APPPATERNO, apellido);
                    map.put(EDAD, edad);
                    map.put(IMAGEN, imagen);
                    map.put(CLAVEAUTO, claveAuto);

                    return map;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(peticion);
        }
    }

    private boolean validarDatosIngresados(EditText txtField) {
        if (txtField.getText().toString().matches("")) {
            txtField.setError("Este campo no puede quedar vacio");;
            return false;
        }
        else {
            return true;
        }
    }
}
