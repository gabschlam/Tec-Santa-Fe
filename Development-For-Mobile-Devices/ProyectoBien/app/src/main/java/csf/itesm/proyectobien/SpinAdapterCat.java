package csf.itesm.proyectobien;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinAdapterCat extends ArrayAdapter<Categoria> {

     /*
        CLASE PARA ADAPTADOR DE SPINNER DE CATEGORÍAS
     */

    private Context context;

    private Categoria[] values;

    public SpinAdapterCat(Context context, int textViewResourceId,
                          Categoria[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public Categoria getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public int getIndex(String myString){
        for (int i=0;i<getCount();i++){

            if (getItem(i).getNombre().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);

        label.setText(values[position].getNombre());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(values[position].getNombre());

        return label;
    }
}