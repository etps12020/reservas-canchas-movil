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
//import com.example.databases.model.EstadoCancha;
import com.example.databases.model.EstadoUsuario;
import com.example.databases.api.usuarios.EstadoCancha;
import java.util.List;

public class EstadoCanchaAdapter extends ArrayAdapter<EstadoCancha> {
    private List<EstadoCancha> estadoCanchasList;
    private Context context;

    public EstadoCanchaAdapter(@NonNull Context context, int resource, @NonNull List<EstadoCancha> objects) {
        super(context, resource, objects);
        this.context  = context;
        estadoCanchasList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Este metodo toma la vista de simple spinner y remplaza su contenido para cada uno de los items del arraylist pasado como parametro

        View view = convertView;


        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.custom_simple_spinner_item , null);
        }


        EstadoCancha estadoCancha =  estadoCanchasList.get(position);

        TextView texto = view.findViewById(R.id.customSpinnerItem);

        texto.setText(  estadoCancha.getEstado()  );

        return  view;
    }
}
