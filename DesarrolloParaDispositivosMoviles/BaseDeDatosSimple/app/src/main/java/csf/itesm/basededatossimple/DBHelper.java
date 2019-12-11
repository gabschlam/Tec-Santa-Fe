package csf.itesm.basededatossimple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static String NOMBRE_DB = "agencia_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLA_AUTOS = "autos";
    private static final String LLAVE_ID = "id";
    private static final String LLAVE_NOMBRE = "nombre";

    // Definimos la estructura de la base de datos
    /* CREATE TABLE autos ( id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nombre TEXT, telefono TEXT......);*/
    private static final String CREAR_TABLA_AUTOS = "CREATE TABLE "
            + TABLA_AUTOS + "(" + LLAVE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + LLAVE_NOMBRE + " TEXT );";

    // Definimos parametros de la BD
    public DBHelper(Context context) {
        super(context, NOMBRE_DB, null, DATABASE_VERSION);
        Log.d("table", CREAR_TABLA_AUTOS);
    }

    // Creamos la tabla autos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_AUTOS);
    }

    // Ejecutamos en onUpgrade si existe la tabla autos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLA_AUTOS + "'");
        onCreate(db);
    }

    public long agregarDetallesAuto(String auto) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creamos valores
        ContentValues valores = new ContentValues();
        valores.put(LLAVE_NOMBRE, auto);
        // Insertamos una fila en la tabla autos
        return db.insert(TABLA_AUTOS, null, valores);
    }

    public ArrayList<String> obtieneTodaLaListaDeAutos() {
        ArrayList<String> arrayListDeAutos = new ArrayList<String>();
        String nombre = "";
        String querySelect = "SELECT  * FROM " + TABLA_AUTOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySelect, null);

        // Iteramos sobre todas las filas y vamos agregando
        if (cursor.moveToFirst()) {
            do {
                nombre = cursor.getString(cursor.getColumnIndex(LLAVE_NOMBRE));
                // Agregamos a la lista de autos
                arrayListDeAutos.add(nombre);
                //cursor.close();
            } while (cursor.moveToNext());
            Log.d("arreglo", arrayListDeAutos.toString());
            cursor.close();
        }
        return arrayListDeAutos;
    }
}
