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
import com.example.databases.model.EstadoUsuario;
import com.example.databases.model.Usuario;

import java.util.List;

//Adaptador utilizado para poder utilizar objetos especificos de EstadoUsuario
public class EstadoUsuarioAdapter extends ArrayAdapter<EstadoUsuario> {

    private List<EstadoUsuario> estadoUsuariosList;
    private Context context;

    public EstadoUsuarioAdapter(@NonNull Context context, int resource, @NonNull List<EstadoUsuario> objects) {
        super(context, resource, objects);
        this.context  = context;
        estadoUsuariosList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Este metodo toma la vista de simple spinner y remplaza su contenido para cada uno de los items del arraylist pasado como parametro

        View view = convertView;


        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.custom_simple_spinner_item , null);
        }


        EstadoUsuario estadoUsuario =  estadoUsuariosList.get(position);

        TextView texto = view.findViewById(R.id.customSpinnerItem);

        texto.setText(  estadoUsuario.getEstado()  );

        return  view;
    }
}
