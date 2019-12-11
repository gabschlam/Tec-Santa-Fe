package csf.itesm.proyectobien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListaVideojuegos extends ListFragment {

    /*
        CLASE PARA SERVICIO Y OBTENER VIDEOJUEGOS
     */

    private final String TAG = "ListaVideojuegos";
    private ArrayList<Videojuego> miListaDeVideojuegos;

    private String categoria;

    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.videojuegosC.php";

    private final String EXTRA_JSON_OBJECT = "objetoVideojuego";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProgressDialog barraDeProgreso = new ProgressDialog(getActivity());
        barraDeProgreso.setMessage("Cargando datos...");
        barraDeProgreso.show();

        categoria = ListaCategorias.idCategoria;

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG,response.toString());
                        Log.d(TAG,"Tamaño "+response.length());

                        // Si se seleccionó una categoría en la actividad de categorías, filtrar videojuegos
                        if (categoria == null)
                        {
                            miListaDeVideojuegos = parserJSON.parseaArreglo(response);
                        }
                        else {
                            miListaDeVideojuegos = parserJSON.parseaArregloCategorias(response, categoria);
                        }

                        barraDeProgreso.hide();

                        // Creamos un adaptador personalizado para la lista de videojuegos
                        AdaptadorVideojuego adapter = new AdaptadorVideojuego(miListaDeVideojuegos);
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

    private class AdaptadorVideojuego extends ArrayAdapter<Videojuego> {
        public AdaptadorVideojuego(ArrayList<Videojuego> videojuegos) {
            super(getActivity(), 0, videojuegos);
        }

        /* Sobre cargamos el metodo getView() en el que inflamos el archivo layout_lista_videojuegos.xml
           en el que mostraremos los videojuegos. Obtenemos el elemento Videojuego en la posición correspondiente de
           la lista miListaDeVideojuego y devolvemos la vista.*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(TAG,"posición "+position);
            if (convertView == null) {
                // Inflaremos el siguiente layout para mostrar los videojuegos
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.layout_lista_videojuegos, null);
            }

            Videojuego c = miListaDeVideojuegos.get(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.textview_nombre);
            nameTextView.setText(c.getNombre());

            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), IndividualGame.class);
                    i.putExtra(EXTRA_JSON_OBJECT, miListaDeVideojuegos.get(position));

                    // Validaciones de administrador
                    if(Administrador.rol == null){
                        Games.showName = 1;

                    }
                    else if(Administrador.rol.equals("Admin")) {
                        Games.showName = 1;

                    }
                    else {
                        Administrador.rol = null;
                        Games.showName = 1;

                    }

                    startActivity(i);
                }
            });
            return convertView;
        }

    }

}
