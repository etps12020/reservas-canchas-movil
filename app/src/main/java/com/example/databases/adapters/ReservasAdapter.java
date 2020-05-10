package com.example.databases.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.databases.R;
import com.example.databases.api.reservas.Reserva;

import java.util.List;

public class ReservasAdapter  extends ArrayAdapter<Reserva> {
    private List<Reserva> reservaList;
    private Context context;

    public ReservasAdapter(@NonNull Context context, int resource, @NonNull List<Reserva> objects) {
        super(context, resource, objects);
        this.context =  context;
        this.reservaList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =  convertView;

        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.item_list_reserva , null);
        }

        Reserva reserva =  reservaList.get(position);





        return view;
    }
}

