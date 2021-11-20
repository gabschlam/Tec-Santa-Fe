package csf.itesm.examenfinal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class parserJSON {

    public static ArrayList<Usuario> Usuarios = new ArrayList<>();

    // Regresa un ArrayList de Categorias
    public static ArrayList<Usuario> parseaArreglo(JSONArray arr) {

        JSONObject obj=null;
        Usuario usuario = null;
        Usuarios.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                usuario = new Usuario();

                usuario.setNombre(obj.getString("Nombre"));
                usuario.setAppaterno(obj.getString("Appaterno"));
                usuario.setImagen(obj.getString("imagen"));
                usuario.setIdLogin(obj.getString("id_login"));
                usuario.setClave_auto(obj.getString("Clave_auto"));

                Usuarios.add(usuario);
            }
            return Usuarios;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

}