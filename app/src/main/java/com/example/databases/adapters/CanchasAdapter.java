package com.example.databases.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.databases.R;
//import com.example.databases.model.Cancha;
import com.example.databases.api.usuarios.ResponseCancha;
import java.util.List;

public class CanchasAdapter extends ArrayAdapter<ResponseCancha> {
    private List<ResponseCancha> canchas;
    private Context context;
    public CanchasAdapter(@NonNull Context context, int resource, @NonNull List<ResponseCancha> objects) {
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
            view = LayoutInflater.from(this.context).inflate(R.layout.cardview_canchas , null);
        }

        ResponseCancha  cancha = canchas.get(position);
        TextView user =   view.findViewById(R.id.nombreCancha);
        TextView direccion  =  view.findViewById(R.id.direccion);

        ImageView imagenCard =  view.findViewById(R.id.imagenCard);
        user.setText(cancha.getNombre());
        direccion.setText(  "Ubicacion: "+cancha.getEdificio()+"\nTipo: "+cancha.getTipo()+"\nHorario: "+cancha.getHoraInicio()+"AM  = "+cancha.getHoraFin()+" PM"   );


        byte[] decodedString = Base64.decode(  cancha.getImagen() , Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imagenCard.setImageBitmap(decodedByte);


        return view;
    }

}
