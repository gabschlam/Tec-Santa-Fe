package csf.itesm.proyectobien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class parserJSONCategorias {

    /*
        ACTIVIDAD DE PARSEO DE CATEGORIAS
     */

    public static ArrayList<Categoria> Categorias = new ArrayList<>();

    // Regresa un ArrayList de Categorias
    public static ArrayList<Categoria> parseaArreglo(JSONArray arr) {

        JSONObject obj=null;
        Categoria categoria = null;
        Categorias.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                categoria = new Categoria();

                categoria.setIdCategoria(obj.getString("idCategoria"));
                categoria.setNombre(obj.getString("Nombre"));

                Categorias.add(categoria);
            }
            return Categorias;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    // Metodo para el uso de spinner
    public static Categoria[] arreglo(JSONArray arr) {

        Categoria[] categorias = new Categoria[arr.length()];

        JSONObject obj=null;

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                categorias[i] = new Categoria();
                categorias[i].setIdCategoria(obj.getString("idCategoria"));
                categorias[i].setNombre(obj.getString("Nombre"));

            }
            return categorias;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }


}