package csf.itesm.proyectobien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.fragment.app.ListFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListaCategorias extends ListFragment {

    /*
        CLASE PARA SERVICIO Y OBTENER CATEGORIAS
     */
    private final String TAG = "ListaCategorias";
    private ArrayList<Categoria> miListaDeCategorias;

    public static String idCategoria;

    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1024122/Content/Parcial%203/ProyectoFinal/REST/servicio.categorias.php";
    private final String EXTRA_JSON_OBJECT = "objetoCategoria";

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
                        miListaDeCategorias = parserJSONCategorias.parseaArreglo(response);
                        barraDeProgreso.hide();

                        // Creamos un adaptador personalizado para la lista de categorías
                        AdaptadorCategoria adapter = new AdaptadorCategoria(miListaDeCategorias);
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

    private class AdaptadorCategoria extends ArrayAdapter<Categoria> {
        public AdaptadorCategoria(ArrayList<Categoria> categorias) {
            super(getActivity(), 0, categorias);
        }

        /* Sobre cargamos el metodo getView() en el que inflamos el archivo layout_lista_categorias.xml
           en el que mostraremos las categorías. Obtenemos el elemento Categoría en la posición correspondiente de
           la lista miListaDeCategoria y devolvemos la vista.*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(TAG,"posición "+position);
            if (convertView == null) {
                // Inflaremos el siguiente layout para mostrar las categorías.
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.layout_lista_categorias, null);
            }
            Categoria c = miListaDeCategorias.get(position);

            Button button_nombre = (Button) convertView.findViewById(R.id.button_nombre);
            button_nombre.setText(c.getNombre());

            button_nombre.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), Games.class);
                    i.putExtra(EXTRA_JSON_OBJECT, miListaDeCategorias.get(position));

                    // IdCategoria para filtrar videojuegos
                    idCategoria = miListaDeCategorias.get(position).getIdCategoria();

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
