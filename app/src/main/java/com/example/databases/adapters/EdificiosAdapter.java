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
//import com.example.databases.model.Edificio;

import java.util.List;
import com.example.databases.api.usuarios.ResponseEdificio;

public class EdificiosAdapter extends ArrayAdapter<ResponseEdificio> {

    private List<ResponseEdificio> listEdificios;
    private Context context;

    public EdificiosAdapter(@NonNull Context context, int resource, @NonNull List<ResponseEdificio> objects) {
        super(context, resource, objects);
        this.context  = context;
        this.listEdificios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =  convertView;

        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.cardview_edificios , null);
        }

        ResponseEdificio edificio =  listEdificios.get(position);

        TextView tvEdificio = view.findViewById(R.id.nombreEdificio);

        tvEdificio.setText( edificio.getNombre() );

        return view;
    }
}
