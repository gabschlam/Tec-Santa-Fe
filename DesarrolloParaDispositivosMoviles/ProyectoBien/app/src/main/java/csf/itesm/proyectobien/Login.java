package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {

    /*
        ACTIVIDAD DE LOGIN
     */

    public static String SERVICIO_LOGIN;

    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";
    public static final String ROL_ADMIN = "Administrador";

    private final String TAG = "Datos";

    Usuario datosUsuario;

    public static String idUsuario;

    private String usuario;
    private String password;

    private EditText txtField_Usuario, txtField_Password;
    private DBHelper databaseHelper;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DBHelper(this);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        txtField_Usuario = findViewById(R.id.usuario);
        txtField_Password = findViewById(R.id.password);

        Button btn_register = (Button) findViewById(R.id.button_reg);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad_register = new Intent(Login.this, Register.class);
                startActivity(actividad_register);
            }
        }
        );
    }

    // METODO PARA INICIAR SESION
    public void iniciaSesion(View view) {
        String usuario = txtField_Usuario.getText().toString();
        String password = txtField_Password.getText().toString();

        boolean valUuario = validarDatosIngresados(txtField_Usuario);
        boolean valPassword = validarDatosIngresados(txtField_Password);

        if (!valUuario || !valPassword) {
            Toast.makeText(this, "Llena todos los datos", Toast.LENGTH_LONG).show();
        }

        else {
            // Si ya se habia hecho petición anteriormente, ya está en la base de datos interna.
            // Checamos si el usuario y password ya están en la base de datos interna.
            if (databaseHelper.checarPassword(usuario, password)) {

                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString("username", usuario);
                e.putString("password", password);
                e.putString("idUsuario", databaseHelper.checarId(usuario));
                e.commit();

                // Si el rol del usuario ingresando es administrador
                if (databaseHelper.checarRol(usuario).equals(ROL_ADMIN)) {
                    startActivity(new Intent(Login.this,Administrador.class));
                }

                else {
                    startActivity(new Intent(Login.this, Games.class));
                    Games.showName = 0;
                    finish();
                }

            }
            /*
                Si no hay coincidencia en la base de datos interna hay dos opciones:
                Si existe el usuario, pero el password no es el correcto, no se hace
                petición a la base de datos.
                Si no existe el usuario, se hace petición a base de datos.
             */
            else {
                // Si está el usuario...
                if (databaseHelper.checarUsuario(usuario)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage("Usuario o contraseña incorrecta");
                    dialog.setTitle("Error");
                    dialog.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }

                // Si no está el usuario, petición a base de datos.
                else {
                    usuarioLogin(usuario, password);
                }
            }
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

    // SERVICIO PARA VALIDACIÓN DE LOGIN
    private void usuarioLogin(String user, final String pass) {

        SERVICIO_LOGIN = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.login.php?";
        usuario = user;
        password = pass;

        SERVICIO_LOGIN = SERVICIO_LOGIN + USUARIO + "=" + usuario + "&" + PASSWORD + "=" + password;
        Log.d(TAG,SERVICIO_LOGIN.toString());

        JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_LOGIN, new Response.Listener<JSONArray>() {
            @Override public void onResponse(JSONArray response) {
                try {
                    JSONObject autenticacion = (JSONObject) response.get(0);
                    String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                    Log.d(TAG,response.toString());

                    if (codigo_autenticacion.equals("01")) {
                        JSONObject userInfo = (JSONObject) response.get(1);
                        JSONObject username = (JSONObject) response.get(2);

                        datosUsuario.getInstance().setIdUsuario(username.getString("idUsuario").toString());
                        datosUsuario.getInstance().setNombre(username.getString("Nombre").toString());
                        datosUsuario.getInstance().setApellido(username.getString("Apellido").toString());
                        datosUsuario.getInstance().setRol(userInfo.getString("rol").toString());
                        datosUsuario.getInstance().setusuario(usuario);
                        datosUsuario.getInstance().setPassword(password);

                        // Se agrega a base de datos interna
                        databaseHelper.agregarUsuario(usuario, password, userInfo.getString("rol").toString(), username.getString("idUsuario").toString());

                        SharedPreferences.Editor e = sharedPreferences.edit();
                        e.putString("username", usuario);
                        e.putString("password", password);
                        e.putString("idUsuario", username.getString("idUsuario"));

                        e.commit();

                        // Si el rol del usuario ingresando es administrador
                        if (databaseHelper.checarRol(usuario).equals(ROL_ADMIN)) {
                            startActivity(new Intent(Login.this,Administrador.class));
                            Administrador.rol = "Admin";
                        }

                        else {
                            startActivity(new Intent(Login.this, Games.class));
                            Administrador.rol = null;
                            Games.showName = 0;
                            finish();
                        }

                    } else if (codigo_autenticacion.equals("04")) {
                        Log.d(TAG,"Usuario o password incorrecto");

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                        dialog.setMessage("Usuario o contraseña incorrecta");
                        dialog.setTitle("Error");
                        dialog.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(Login.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(USUARIO, usuario);
                map.put(PASSWORD, password);
                return map;
            }

            // AUTENTIFICACIÓN QUE NO SIRVIÓ COMO ESPERADO
            /*@Override public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentiales = usuario + ":" + password;
                //String autenticacion = "Basic " + Base64.encodeToString(credentiales.getBytes(), Base64.NO_WRAP);
                String autenticacion = "Basic dGllbmRhOjNkb3Rz";
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", autenticacion);
                return headers;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticion);
    }
}
