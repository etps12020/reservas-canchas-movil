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
import com.example.databases.model.Edificio;

import java.util.List;

public class EdificiosAdapter extends ArrayAdapter<Edificio> {

    private List<Edificio> listEdificios;
    private Context context;

    public EdificiosAdapter(@NonNull Context context, int resource, @NonNull List<Edificio> objects) {
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

        Edificio edificio =  listEdificios.get(position);

        TextView tvEdificio = view.findViewById(R.id.nombreEdificio);

        tvEdificio.setText( edificio.getNombre() );

        return view;
    }
}
