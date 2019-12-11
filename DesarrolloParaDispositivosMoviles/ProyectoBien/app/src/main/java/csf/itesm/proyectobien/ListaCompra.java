package csf.itesm.proyectobien;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaCompra extends ListFragment {

    /*
        CLASE PARA SERVICIO Y OBTENER COMPRA
     */

    // Para obtener de la actividad "Games" el dato de idUsuario
    Context applicationContext = Games.getContextOfApplication();

    private final String TAG = "ListaCarrito";
    private ArrayList<DatosCompra> miListaDeCompra;

    private String categoria;

    private String idUsuario;

    public static final String IDUSUARIO = "idUsuario";

    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.compraUsuario.php?";

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
                        // Recibimos la cantidad de elementos en su compra para poder desplegarlos
                        String count = (((JSONObject) response.get(0)).getString("Count"));

                        miListaDeCompra = parserJSON.parseaCompra(response, Integer.parseInt(count));

                        // Creamos un adaptador personalizado para la lista de compra
                        AdaptadorCompra adapter = new AdaptadorCompra(miListaDeCompra);
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

    private class AdaptadorCompra extends ArrayAdapter<DatosCompra> {
        public AdaptadorCompra(ArrayList<DatosCompra> compra) {
            super(getActivity(), 0, compra);
        }

        /* Sobre cargamos el metodo getView() en el que inflamos el archivo layout_lista_compra.xml
           en el que mostraremos los videojuegos comprados. Obtenemos el elemento Compra en
           la posición correspondiente de la lista miListaDeCompra y devolvemos la vista.*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(TAG,"posición "+position);
            if (convertView == null) {
                // Inflaremos el siguiente layout para mostrar la compra.
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.layout_lista_compra, null);
            }
            final DatosCompra c = miListaDeCompra.get(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.nombre);
            nameTextView.setText(c.getNombre());

            final Button descargar = (Button) convertView.findViewById(R.id.descargar);

            descargar.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    descargarVideojuego(c);

                }
            });
            return convertView;
        }

    }

    // SERVICIO PARA DESCARGAR APK
    private void descargarVideojuego(DatosCompra c) {

        DownloadManager downloadmanager = (DownloadManager) applicationContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(c.getAPK());

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(c.getNombre());
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        downloadmanager.enqueue(request);

    }

}
