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
import com.example.databases.api.canchas.Cancha;

import java.util.List;

public class SpinnerCanchaAdapter extends ArrayAdapter {

    private Context context;
    private List<Cancha> canchaList;

    public SpinnerCanchaAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context =  context;
        this.canchaList =  objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =  convertView;

        if(view==null){
            view = LayoutInflater.from(this.context).inflate(R.layout.custom_simple_spinner_item , null);
        }

        TextView texto =  view.findViewById(R.id.customSpinnerItem);
        texto.setText(  canchaList.get(position).getNombre()  );
        return view;
    }
}
