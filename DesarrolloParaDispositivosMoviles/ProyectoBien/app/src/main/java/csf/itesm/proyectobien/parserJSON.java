package csf.itesm.proyectobien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class parserJSON {

    /*
        ACTIVIDAD DE PARSEO DE VIDEOJUEGOS
     */

    public static ArrayList<Videojuego> Videojuegos = new ArrayList<>();
    public static ArrayList<DatosCarrito> Carrito = new ArrayList<>();
    public static ArrayList<DatosCompra> Compra = new ArrayList<>();

    // Regresa un ArrayList de Videojuegos, sin ninguna filtración
    public static ArrayList<Videojuego> parseaArreglo(JSONArray arr) {

        JSONObject obj=null;
        Videojuego videojuego = null;
        Videojuegos.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                videojuego = new Videojuego();

                videojuego.setIdVideojuego(obj.getString("idVideojuego"));
                videojuego.setNombre(obj.getString("Nombre"));
                videojuego.setDescripcion(obj.getString("Descripcion"));
                videojuego.setImagen(obj.getString("Imagen"));
                videojuego.setAPK(obj.getString("APK"));
                videojuego.setCosto(obj.getString("Costo"));
                videojuego.setidCategoria(obj.getString("idCategoria"));
                videojuego.setnomCategoria(obj.getString("CatNombre"));

                Videojuegos.add(videojuego);
            }
            return Videojuegos;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    // Regresa un ArrayList de Videojuego, filtrando por idCategoria
    public static ArrayList<Videojuego> parseaArregloCategorias(JSONArray arr, String categoria) {

        JSONObject obj=null;
        Videojuego videojuego = null;
        Videojuegos.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                videojuego = new Videojuego();

                videojuego.setIdVideojuego(obj.getString("idVideojuego"));
                videojuego.setNombre(obj.getString("Nombre"));
                videojuego.setDescripcion(obj.getString("Descripcion"));
                videojuego.setImagen(obj.getString("Imagen"));
                videojuego.setAPK(obj.getString("APK"));
                videojuego.setCosto(obj.getString("Costo"));
                videojuego.setidCategoria(obj.getString("idCategoria"));
                videojuego.setnomCategoria(obj.getString("CatNombre"));

                if (obj.getString("idCategoria").equals(categoria))
                {
                    Videojuegos.add(videojuego);
                }

            }
            return Videojuegos;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    // Metodo para el uso de spinner
    public static Videojuego[] busquedaArreglo(JSONArray arr) {

        JSONObject obj=null;
        int size = arr.length();
        size++;
        Videojuego[] videojuegos = new Videojuego[size];

        try {
            for(int i = 0;i<size;i++) {

                videojuegos[i] = new Videojuego();

                // Uso de hint
                if (i == 0) {
                    videojuegos[i].setNombre("Selecciona un videojuego");

                }

                else {
                    obj = arr.getJSONObject(i-1);
                    videojuegos[i].setIdVideojuego(obj.getString("idVideojuego"));
                    videojuegos[i].setNombre(obj.getString("Nombre"));
                    videojuegos[i].setDescripcion(obj.getString("Descripcion"));
                    videojuegos[i].setImagen(obj.getString("Imagen"));
                    videojuegos[i].setAPK(obj.getString("APK"));
                    videojuegos[i].setCosto(obj.getString("Costo"));
                    videojuegos[i].setidCategoria(obj.getString("idCategoria"));
                    videojuegos[i].setnomCategoria(obj.getString("CatNombre"));
                }
            }
            return videojuegos;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    // Regresa ArrayList de Carrito
    public static ArrayList<DatosCarrito> parseaCarrito(JSONArray arr, int Count) {

        JSONObject obj=null;
        DatosCarrito carrito = null;
        Carrito.clear();

        try {
            for(int i = 0;i<Count;i++) {

                obj = arr.getJSONObject(i+1);
                carrito = new DatosCarrito();

                carrito.setIdUsuario(obj.getString("idUsuario"));
                carrito.setIdVideojuego(obj.getString("idVideojuego"));
                carrito.setNombre(obj.getString("Nombre"));
                carrito.setCantidad(obj.getString("Cantidad"));
                carrito.setCosto(obj.getString("Costo"));

                Carrito.add(carrito);
            }
            return Carrito;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    // Regresa ArrayList de Compra
    public static ArrayList<DatosCompra> parseaCompra(JSONArray arr, int Count) {

        JSONObject obj=null;
        DatosCompra compra = null;
        Compra.clear();

        try {
            for(int i = 0;i<Count;i++) {

                obj = arr.getJSONObject(i+1);
                compra = new DatosCompra();

                compra.setIdUsuario(obj.getString("idUsuario"));
                compra.setIdVideojuego(obj.getString("idVideojuego"));
                compra.setNombre(obj.getString("Nombre"));
                compra.setFecha(obj.getString("fechaCompra"));
                compra.setAPK(obj.getString("APK"));

                Compra.add(compra);
            }
            return Compra;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}