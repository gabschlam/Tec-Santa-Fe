package csf.itesm.examenfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListaUsuario extends ListFragment {

    private final String TAG = "ListaUsuarios";
    private ArrayList<Usuario> miListaDeUsuarios;
    private TextView nameTextView, apTextView, idLogTextView, claveAutoTextView;
    private ImageView ImageViewUrl;

    String url = "http://ubiquitous.csf.itesm.mx/~raulms/content/TC2024/REST/.Examen/servicio.select.usuario.php";

    private final String EXTRA_JSON_OBJECT = "objetoUsuario";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProgressDialog barraDeProgreso = new ProgressDialog(getActivity());
        barraDeProgreso.setMessage("Cargando datos...");
        barraDeProgreso.show();

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG,response.toString());
                        Log.d(TAG,"Tamaño "+response.length());

                        miListaDeUsuarios = parserJSON.parseaArreglo(response);

                        barraDeProgreso.hide();

                        // Creamos un adaptador personalizado para la lista de usuarios
                        AdaptadorUsuario adapter = new AdaptadorUsuario(miListaDeUsuarios);
                        setListAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error en: " + error.getMessage());
                // oculta la barra de progreso
                barraDeProgreso.hide();
            }
        });
        // Anexamos el request a la cola
        Volley.newRequestQueue(getActivity()).add(jsonArrayReq);

        barraDeProgreso.dismiss();

    }

    private class AdaptadorUsuario extends ArrayAdapter<Usuario> {
        public AdaptadorUsuario(ArrayList<Usuario> usuarios) {
            super(getActivity(), 0, usuarios);
        }

        /* Sobre cargamos el metodo getView() en el que inflamos el archivo layout_lista_videojuegos.xml
           en el que mostraremos los videojuegos. Obtenemos el elemento Videojuego en la posición correspondiente de
           la lista miListaDeVideojuego y devolvemos la vista.*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(TAG,"posición "+position);
            if (convertView == null) {
                // Inflaremos el siguiente layout para mostrar los usuarios
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.layout_lista_usuarios, null);
            }

            Usuario u = miListaDeUsuarios.get(position);

            nameTextView = (TextView) convertView.findViewById(R.id.nombre);
            nameTextView.setText(u.getNombre());

            apTextView = (TextView) convertView.findViewById(R.id.apellido);
            apTextView.setText(u.getAppaterno());

            ImageViewUrl = (ImageView) convertView.findViewById(R.id.imageUsuario);
            String imagen = u.getImagen();

            com.android.volley.toolbox.ImageLoader cargaImagen = new com.android.volley.toolbox.ImageLoader(Volley.newRequestQueue(getContext()),
                    new csf.itesm.examenfinal.ImageLoader());

            cargaImagen.get(imagen, new com.android.volley.toolbox.ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error al cargar imagen: " + error.getMessage());
                }
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // carga la imagen en el  imageview
                        ImageViewUrl.setImageBitmap(response.getBitmap());
                    }
                }
            });

            idLogTextView = (TextView) convertView.findViewById(R.id.idLogin);
            idLogTextView.setText(u.getIdLogin());

            claveAutoTextView = (TextView) convertView.findViewById(R.id.claveAuto);
            claveAutoTextView.setText(u.getClave_auto());

            return convertView;
        }

    }

}
