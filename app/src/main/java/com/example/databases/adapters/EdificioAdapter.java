package com.example.databases.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.databases.R;
import com.example.databases.api.edificios.Edificio;


import java.util.List;

public class EdificioAdapter  extends ArrayAdapter<Edificio> {

    private Context context;
    private List<Edificio> edificios;

    public EdificioAdapter(@NonNull Context context, int resource, @NonNull List<Edificio> objects) {
        super(context, resource, objects);
        this.context = context;
        edificios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;


        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.custom_simple_spinner_item , null);
        }

        TextView texto = view.findViewById(R.id.customSpinnerItem);
        Edificio edificio =  edificios.get(position);
        texto.setText(  edificio.getNombre()     );

        return view;
    }


}
