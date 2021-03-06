package com.example.databases.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.databases.R;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.db.Conexion;
import com.example.databases.model.Usuario;

import java.util.ArrayList;
import java.util.List;

//Adaptador para hacer enlace entre los objetos de tipo usuario y los elementos graficos (listas , spinners , etc)
public class UsuariosAdapter extends ArrayAdapter<ResponseLogin> {


    private List<ResponseLogin> usuarios;
    private Context context;

    public UsuariosAdapter(@NonNull Context context, int resource, @NonNull List<ResponseLogin> objects) {
        super(context, resource, objects);
        this.context =  context;
        this.usuarios =  objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Genera el contenido visual en base al contenido del array list pasado como parametro
        View view =  convertView;

        if(view == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.item_usuario , null);
        }

        ResponseLogin usuario =  usuarios.get(position);

        TextView user =   view.findViewById(R.id.nombreUsuario);

        user.setText( usuario.getUsuario()+"   -   "+ usuario.getNombre());


        return view;
    }
}
