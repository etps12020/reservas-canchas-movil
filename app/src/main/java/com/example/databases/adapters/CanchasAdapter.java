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
import com.example.databases.model.Cancha;

import java.util.List;

public class CanchasAdapter extends ArrayAdapter<Cancha> {
    private List<Cancha> canchas;
    private Context context;
    public CanchasAdapter(@NonNull Context context, int resource, @NonNull List<Cancha> objects) {
        super(context, resource, objects);
        this.context =  context;
        this.canchas =  objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Genera el contenido visual en base al contenido del array list pasado como parametro
        View view =  convertView;

        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.item_usuario , null);
        }

        Cancha  cancha = canchas.get(position);

        TextView user =   view.findViewById(R.id.nombreUsuario);

        user.setText(cancha.getNombre());


        return view;
    }

}
