package csf.itesm.proyectobien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.MessageDigest;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    /*
        CLASE PARA BASE DE DATOS INTERNA
        ESTA CLASE SE USA PARA CUANDO SE MANDA PETICIÓN PARA VALIDAR USUARIO,
        GUARDAR EN BASE DE DATOS INTERNA Y NO MANDAR OTRA VEZ PETICIÓN
     */

    public static String NOMBRE_DB = "tienda_db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLA_USUARIOS = "usuarios";
    private static final String LLAVE_USUARIO = "usuario";
    private static final String LLAVE_PASSWORD = "password";
    private static final String LLAVE_ROL = "rol";
    private static final String LLAVE_ID = "idUsuario";

    // Definimos la estructura de la base de datos
    private static final String CREAR_TABLA_USUARIOS = "CREATE TABLE "
            + TABLA_USUARIOS + "(" + LLAVE_USUARIO + " TEXT PRIMARY KEY,"
            + LLAVE_PASSWORD + " TEXT," + LLAVE_ROL + " TEXT," + LLAVE_ID + " TEXT);";

    // Definimos parametros de la BD
    public DBHelper(Context context) {
        super(context, NOMBRE_DB, null, DATABASE_VERSION);
        Log.d("table", CREAR_TABLA_USUARIOS);
    }

    // Creamos la tabla de usuarios
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_USUARIOS);
    }

    // Ejecutamos en onUpgrade si existe la tabla de usuarios
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLA_USUARIOS + "'");
        onCreate(db);
    }

    // Encriptamos password con método MD5,
    // sacado de https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    private static final String encryptPass(final String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(password.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }

    public long agregarUsuario(String usuario, String password, String rol, String idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creamos valores
        ContentValues valores = new ContentValues();
        valores.put(LLAVE_USUARIO, usuario);
        valores.put(LLAVE_PASSWORD, encryptPass(password));
        valores.put(LLAVE_ROL, rol);
        valores.put(LLAVE_ID, idUsuario);

        // Insertamos una fila en la tabla de usuarios
        return db.insert(TABLA_USUARIOS, null, valores);
    }

    public boolean checarUsuario(String usuario) {
        String querySelect = "SELECT  * FROM " + TABLA_USUARIOS + " WHERE " + LLAVE_USUARIO + "= '" + usuario + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySelect, null);

        if(cursor.getCount()>0)
        {
            return true;
        }

        else {
           return false;
        }
    }

    public boolean checarPassword(String usuario, String password) {
        String querySelect = "SELECT * FROM " + TABLA_USUARIOS + " WHERE " + LLAVE_USUARIO
                + "= '" + usuario + "' AND " + LLAVE_PASSWORD + "= '" + encryptPass(password) + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySelect, null);

        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                return true;
            }

            else {
                return false;
            }
        }

        else {
            return false;
        }
    }

    public String checarRol(String usuario) {
        String rol = "";

        String querySelect = "SELECT " + LLAVE_ROL + " FROM " + TABLA_USUARIOS + " WHERE " + LLAVE_USUARIO + "= '" + usuario + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySelect, null);

        if (cursor.moveToFirst()) {
            rol = cursor.getString(0);
            Log.d("rol", rol.toString());

        }

        return rol;
    }

    public String checarId(String usuario) {
        String idUsuario = "";

        String querySelect = "SELECT " + LLAVE_ID + " FROM " + TABLA_USUARIOS + " WHERE " + LLAVE_USUARIO + "= '" + usuario + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySelect, null);

        if (cursor.moveToFirst()) {
            idUsuario = cursor.getString(0);
            Log.d("idUsuario", idUsuario.toString());

        }

        return idUsuario;
    }

    public ArrayList<String> obtieneTodaLaLista() {
        ArrayList<String> arrayListDeAutos = new ArrayList<String>();
        String nombre = "";
        String querySelect = "SELECT  * FROM " + TABLA_USUARIOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySelect, null);

        // Iteramos sobre todas las filas y vamos agregando
        if (cursor.moveToFirst()) {
            do {
                nombre = cursor.getString(cursor.getColumnIndex(LLAVE_ROL));
                // Agregamos a la lista de autos
                arrayListDeAutos.add(nombre);
                //cursor.close();
            } while (cursor.moveToNext());
            Log.d("arreglo", arrayListDeAutos.toString());
            cursor.close();
        }
        else {
            Log.d("arreglo", "No hay datos SQLite");
        }
        return arrayListDeAutos;
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLA_USUARIOS;
        db.execSQL(clearDBQuery);
    }

}