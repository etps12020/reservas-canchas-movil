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
import com.example.databases.model.EstadoEdificio;

import java.util.List;

public class EstadoEdificioAdapter extends ArrayAdapter<EstadoEdificio> {
    private Context context;
    private List<EstadoEdificio> estadoEdificioList;

    public EstadoEdificioAdapter(@NonNull Context context, int resource, @NonNull List<EstadoEdificio> objects) {
        super(context , resource ,  objects);
        this.context = context;
        this.estadoEdificioList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view  =  convertView ;

        if(view==null){
            view = LayoutInflater.from(this.context).inflate(R.layout.custom_simple_spinner_item , null);
        }

        EstadoEdificio  estadoEdificio  = estadoEdificioList.get(position);

        TextView texto =  view.findViewById(R.id.customSpinnerItem);
        texto.setText( estadoEdificio.getEstado() );

        return view;
    }
}
