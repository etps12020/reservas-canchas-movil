package com.example.databases.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.example.databases.R;
import com.example.databases.api.reservas.Horario;


public class HorariosAdapter extends ArrayAdapter<Horario> {

    private List<Horario> horarioList;
    private Context context;

    public HorariosAdapter(@NonNull Context context, int resource, @NonNull List<Horario> objects) {
        super(context, resource, objects);
        this.horarioList  =  objects;
        this.context =  context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view==null){
            view = LayoutInflater.from(this.context).inflate(R.layout.item_horario , null);
        }


        return view;
    }
}
