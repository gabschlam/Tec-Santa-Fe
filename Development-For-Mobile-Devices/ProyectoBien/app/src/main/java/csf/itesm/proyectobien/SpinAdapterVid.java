package csf.itesm.proyectobien;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinAdapterVid extends ArrayAdapter<Videojuego> {

     /*
        CLASE PARA ADAPTADOR DE SPINNER DE VIDEOJUEGOS
     */

    private Context context;

    private Videojuego[] values;

    public SpinAdapterVid(Context context, int textViewResourceId,
                          Videojuego[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public Videojuego getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = (TextView) super.getView(position, convertView, parent);

        label.setText(values[position].getNombre());

        // Para el hint de videojuegos
        if(position == 0){
            label.setTextColor(Color.GRAY);
        }
        else {
            label.setTextColor(Color.BLACK);
        }

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(values[position].getNombre());

        return label;
    }
}