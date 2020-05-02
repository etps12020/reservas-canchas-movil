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
import com.example.databases.model.TipoCancha;

import java.util.List;

public class TipoCanchaAdapter extends ArrayAdapter<TipoCancha> {

    private List<TipoCancha> tipoCanchasList;
    private Context context;

    public TipoCanchaAdapter(@NonNull Context context, int resource, @NonNull List<TipoCancha> objects) {
        super(context, resource, objects);
        this.context  = context;
        tipoCanchasList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Este metodo toma la vista de simple spinner y remplaza su contenido para cada uno de los items del arraylist pasado como parametro

        View view = convertView;


        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.custom_simple_spinner_item , null);
        }


        TipoCancha tipoCancha =  tipoCanchasList.get(position);

        TextView texto = view.findViewById(R.id.customSpinnerItem);

        texto.setText(  tipoCancha.getTipo()  );

        return  view;
    }
}

