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
import com.example.databases.api.tiposReservas.TipoReserva;

import java.util.List;

public class TipoReservaAdapter extends ArrayAdapter<TipoReserva> {

    private List<TipoReserva> tipoReservaList;
    private Context context;

    public TipoReservaAdapter(@NonNull Context context, int resource, @NonNull List<TipoReserva> objects) {
        super(context, resource, objects);
        this.context = context;
        tipoReservaList =  objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =  convertView;


        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.custom_simple_spinner_item , null);
        }

        TextView texto = view.findViewById(R.id.customSpinnerItem);
        TipoReserva tipoReserva = tipoReservaList.get(position);
        texto.setText(  tipoReserva.getTipo() );

        return view;
    }
}
