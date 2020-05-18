package com.example.databases.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.databases.R;
import com.example.databases.api.reservas.RequestUpdateReserva;
import com.example.databases.api.reservas.Reserva;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.utilidades.Session;
import com.example.databases.views.DetalleReserva;
import com.example.databases.views.ListaReservas;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasAdapter  extends ArrayAdapter<Reserva> {
    private List<Reserva> reservaList;
    private Context context;
    private ResponseLogin userLogin;
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private ErrorObject errorObject;

    public ReservasAdapter(@NonNull Context context, int resource, @NonNull List<Reserva> objects) {
        super(context, resource, objects);
        this.context =  context;
        this.reservaList = objects;
        this.userLogin = Session.obtenerSessionUsuario(this.context);
        retrofitInit();
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
        TextView nombreCompleto = view.findViewById(R.id.tvNombreCompleto);
        TextView tvFechaHora = view.findViewById(R.id.tvFechaHora);
        TextView tvNumeroReserva= view.findViewById(R.id.tvNumeroReserva);
        TextView estadoReserva =  view.findViewById(R.id.estadoReserva);
        //Click en elemento de lista


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson =  new Gson();
                String reservaJSON =  gson.toJson(   reserva   );
                Intent i = new Intent(context , DetalleReserva.class);
                i.putExtra("infoReserva"  , reservaJSON    );
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Actualizar a estado 3

                RequestUpdateReserva requestUpdateReserva =  new RequestUpdateReserva(reserva.getNumReservacion() , userLogin.getId() ,  3 , ""  );
                Call<JsonElement> aprobarReserva = reservasCanchasService.actualizarReservacion(requestUpdateReserva);

                aprobarReserva.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        String jsonString  = response.body().toString();
                        if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                            errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                            Toast.makeText(context, errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });

            }
        });

        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Actualizar a estado 2

                RequestUpdateReserva requestUpdateReserva =  new RequestUpdateReserva(reserva.getNumReservacion() , userLogin.getId() ,  2 , ""  );
                Call<JsonElement> rechazarReserva = reservasCanchasService.actualizarReservacion(requestUpdateReserva);
                rechazarReserva.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        String jsonString  = response.body().toString();
                        if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                            errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                            Toast.makeText(context, errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });



            }
        });

        tvFechaHora.setText( "Fecha: "+reserva.getFechaReservacion()+"\nHora: "+reserva.getHoraInicio() );
        estadoReserva.setText(reserva.getEstado());

        if(reserva.getIdEstado()==1){
            estadoReserva.setTextColor(Color.parseColor("#00AAE4") );
        }else if(reserva.getIdEstado()==2){
            estadoReserva.setTextColor(Color.parseColor("#EFA94A") );
        }else if(reserva.getIdEstado()==3){
            estadoReserva.setTextColor(Color.parseColor("#96C93D") );
        }else if(reserva.getIdEstado()==5 || reserva.getIdEstado()==5){
            estadoReserva.setTextColor(Color.parseColor("#FF6961") );
        }else if(reserva.getIdEstado()==6){
            estadoReserva.setTextColor(Color.parseColor("#5D9B9B") );
        }else{
            estadoReserva.setTextColor(Color.parseColor("#00574B") );
        }


        //Si el usuario es administrador mostrara el nombre
        if(userLogin.getIdRol()==1 || userLogin.getIdRol()==2 ){
            nombreCompleto.setText( reserva.getNombreCompleto()    );
        }else{
            nombreCompleto.setText( ""   );
        }

        tvNumeroReserva.setText(  String.valueOf(  reserva.getNumReservacion() + ") "   )   );

        if(userLogin.getIdRol()==3){
            btnAceptar.setVisibility(View.GONE);
            btnRechazar.setVisibility(View.GONE);
        }



        return view;
    }

    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }
}

