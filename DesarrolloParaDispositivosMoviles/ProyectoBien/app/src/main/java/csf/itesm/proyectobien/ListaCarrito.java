package csf.itesm.proyectobien;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaCarrito extends ListFragment {

    /*
        CLASE PARA SERVICIO Y OBTENER CARRITO
     */

    // Para obtener de la actividad "Games" el dato de idUsuario
    Context applicationContext = Games.getContextOfApplication();

    public static String SERVICIO_DELETE;

    private final String TAG = "ListaCarrito";
    private ArrayList<DatosCarrito> miListaDeCarrito;

    private String categoria;

    private String idUsuario;

    public static final String IDUSUARIO = "idUsuario";
    public static final String IDVIDEOJUEGO = "idVideojuego";

    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.carrito.php?";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProgressDialog barraDeProgreso = new ProgressDialog(getActivity());
        barraDeProgreso.setMessage("Cargando datos...");
        barraDeProgreso.show();

        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getString("idUsuario", "");

        categoria = ListaCategorias.idCategoria;

        url = url + IDUSUARIO + "=" + idUsuario;

        Log.d("url",url.toString());

        JsonArrayRequest peticion = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override public void onResponse(JSONArray response) {
                try {
                    JSONObject autenticacion = (JSONObject) response.get(0);
                    String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                    Log.d(TAG,response.toString());

                    if (codigo_autenticacion.equals("01")) {
                        // Recibimos la cantidad de elementos en el carrito para poder desplegarlos
                        String count = (((JSONObject) response.get(0)).getString("Count"));

                        miListaDeCarrito = parserJSON.parseaCarrito(response, Integer.parseInt(count));

                        // Creamos un adaptador personalizado para la lista del carrito
                        AdaptadorCarrito adapter = new AdaptadorCarrito(miListaDeCarrito);
                        setListAdapter(adapter);

                    }

                    else{
                        Toast.makeText(getActivity(), "Carrito Vacio ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), Games.class);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        // Anexamos el request a la cola
        Volley.newRequestQueue(getActivity()).add(peticion);

        barraDeProgreso.dismiss();

    }

    private class AdaptadorCarrito extends ArrayAdapter<DatosCarrito> {
        public AdaptadorCarrito(ArrayList<DatosCarrito> carrito) {
            super(getActivity(), 0, carrito);
        }

        /* Sobre cargamos el metodo getView() en el que inflamos el archivo layout_lista_carrito.xml
           en el que mostraremos los videojuegos en el carrito. Obtenemos el elemento Carrito en
           la posición correspondiente de la lista miListaDeCarrito y devolvemos la vista.*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Log.d(TAG,"posición "+position);
            if (convertView == null) {
                // Inflaremos el siguiente layout para mostrar el carrito.
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.layout_lista_carrito, null);
            }
            final DatosCarrito c = miListaDeCarrito.get(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.nombre);
            nameTextView.setText(c.getNombre());

            TextView costo = (TextView) convertView.findViewById(R.id.costo);
            costo.setText(c.getCosto());

            TextView cantidad = (TextView) convertView.findViewById(R.id.cantidad);
            cantidad.setText(c.getCantidad());

            final Button eliminar = (Button) convertView.findViewById(R.id.eliminar);

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    eliminarVideojuego(c.getIdUsuario(), c.getIdVideojuego());

                }
            });
            return convertView;
        }

    }

    // SERVICIO PARA ELIMINAR UN ELEMENTO DEL CARRITO
    private void eliminarVideojuego(final String idUsuario, final String idVideojuego) {

        SERVICIO_DELETE = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.d.carrito.php?";

        SERVICIO_DELETE = SERVICIO_DELETE + IDUSUARIO + "=" + idUsuario + "&" + IDVIDEOJUEGO + "=" + idVideojuego;
        Log.d(TAG, SERVICIO_DELETE.toString());

        JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_DELETE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject autenticacion = (JSONObject) response.get(0);
                    String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                    //Log.d(TAG,response.toString());

                    if (codigo_autenticacion.equals("01")) {

                        Toast.makeText(getActivity(), "Articulo Eliminado", Toast.LENGTH_LONG).show();
                        Log.d(TAG, response.toString());
                        Intent intent = new Intent(getActivity(), Carrito.class);
                        startActivity(intent);
                    } else if (codigo_autenticacion.equals("04")) {
                        Toast.makeText(getActivity(), "Fallo en el borrado", Toast.LENGTH_LONG).show();

                        Log.d(TAG, "Fallo en el borrado");
                    } else if (codigo_autenticacion.equals("05")) {
                        Toast.makeText(getActivity(), "Fallo en el borrado, datos no encontrados", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Datos no encontrados");
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(IDUSUARIO, idUsuario);
                map.put(IDVIDEOJUEGO, idVideojuego);
                return map;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(peticion);

    }

}
