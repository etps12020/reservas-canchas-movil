package com.example.databases.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.databases.R;
import com.example.databases.api.reservas.Reserva;
import com.example.databases.views.DetalleReserva;
import com.google.gson.Gson;

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

        final Reserva reserva =  reservaList.get(position);


        ImageButton btnAceptar = view.findViewById(R.id.btnAceptar);
        ImageButton  btnRechazar =  view.findViewById(R.id.btnRechazar);

        //Click en elemento de lista
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson =  new Gson();
                String reservaJSON =  gson.toJson(   reserva   );
                Intent i = new Intent(context , DetalleReserva.class);
                i.putExtra("infoReserva"  , reservaJSON    );
                context.startActivity(i);

            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,  "Aceptar Reserva" , Toast.LENGTH_SHORT).show();
            }
        });

        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Rechazar reserva", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}

