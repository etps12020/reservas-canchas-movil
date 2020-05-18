package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databases.MainActivity;
import com.example.databases.R;
import com.example.databases.adapters.CanchasAdapter;
import com.example.databases.adapters.SpinnerCanchaAdapter;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.tiposReservas.TipoReserva;
import com.example.databases.api.usuarios.Duis;
import com.example.databases.api.usuarios.ResponseCancha;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.edificios.Edificio;
import com.example.databases.api.canchas.Cancha;
import com.example.databases.api.utilidades.Session;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealizarReserva extends AppCompatActivity  implements View.OnClickListener {

    private ArrayList<ResponseLogin> usuariosList;  //Lista dinamica de usuarios
    private ArrayList<Cancha> canchas; //Lista dinamica de canchas
    private ArrayList<Edificio> edificios;//Lista dinamica de edificios
    private ArrayList<TipoReserva> tiposReservas; //Lista de tipos de reservas
    private ArrayList<Duis> duisArrayList;
    private ArrayList<String>  duisUsuarios  , nombresCanchas , nombresEdificios , nombresTipoReservas; //Lista de string para nombres de edificios, edificios, canchas y duis de usuarios
    private CalendarView calendarViewReserva; //Calendario para dia de reserva
    private SpinnerDialog spinnerDialogUsuarios  , spinnerDialogCanchas  , spinnerDialogEdificios ,spinnerDialogTipoReserva ;//Searchables Spinners controls
    private Spinner spnCancha;
    private ImageButton   imbTipoReservacion , imbUsuario , imbEdificio;

    private EditText edtUsuario , edtNombreEdificio  , edtCancha  , edtTipoReservacion ; //Campos de texto solo para presentacion
    private String fecha; //Fecha quemada //Fecha de reserva
    private String idCancha ="2"; //Cancha quemada //Id de cancha
    private String idCanchaSeleccionada , idEdificioSeleccionado , duiUsuario , idTipoReserva;

    private ReservasCanchasService reservasCanchasService; //Instancia de servicios
    private ReservasCanchasClient reservasCanchasClient;  //Clientee retrofit
    private ResponseLogin usuarioLogin;
    private ErrorObject errorObject=  null; //Objecto de error
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_reserva);

        calendarViewReserva  =  findViewById(R.id.calendarViewReserva);

        usuarioLogin= Session.obtenerSessionUsuario(getApplicationContext());

        edtUsuario =  findViewById(R.id.edtUsuario);
        edtNombreEdificio =  findViewById(R.id.edtNombreEdificio);
        spnCancha =  findViewById(R.id.spnCancha);
        edtTipoReservacion =  findViewById(R.id.edtTipoReservacion);
        linearLayout =  findViewById(R.id.linearLayout);

        imbUsuario  =  findViewById(R.id.imbUsuario);
        imbTipoReservacion =  findViewById(R.id.imbTipoReservacion);
        imbEdificio =  findViewById(R.id.imBEdificio);

        duisUsuarios  = new ArrayList<>();
        nombresCanchas = new ArrayList<>();
        nombresEdificios =  new ArrayList<>();
        nombresTipoReservas =  new ArrayList<>();
        canchas =  new ArrayList<>();
        edificios =  new ArrayList<>();
        tiposReservas =  new ArrayList<>();
        retrofitInit();


        imbUsuario.setOnClickListener(this); //Evento de busqueda de usuarios
        imbEdificio.setOnClickListener(this); //Evento de busqueda de edificios
        imbTipoReservacion.setOnClickListener(this);


        spinnerDialogUsuarios = new SpinnerDialog( RealizarReserva.this , duisUsuarios , "Seleccione un usuario"  );
        spinnerDialogUsuarios.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String dui, int i) {
                duiUsuario = dui;
                edtUsuario.setText(  dui  );
            }
        });


        spinnerDialogEdificios = new SpinnerDialog( RealizarReserva.this ,    nombresEdificios , "Seleccione el edificio");
        spinnerDialogEdificios.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String edificio, int i) {
                idEdificioSeleccionado =  String.valueOf( buscarIdEdificio(edificio)   );
//                Toast.makeText(getApplicationContext(), idEdificioSeleccionado, Toast.LENGTH_SHORT).show();
                edtNombreEdificio.setText(  edificio );
                listarCanchas( String.valueOf(   edificios.get(i).getId()    )   );
            }
        });


        spinnerDialogTipoReserva =  new SpinnerDialog(RealizarReserva.this  , nombresTipoReservas  , "Seleccione el tipo de reserva realizar");
        spinnerDialogTipoReserva.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String tipoReserva, int i) {
                idTipoReserva  =  String.valueOf( buscarIdTipoReservacion(tipoReserva));
//                Toast.makeText(getApplicationContext() , idTipoReserva , Toast.LENGTH_SHORT).show();
                edtTipoReservacion.setText(tipoReserva);
            }
        });


        if(usuarioLogin.getIdRol()==3){
            linearLayout.setVisibility(LinearLayout.GONE);
        }

        calendarViewReserva.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String docUsuario  = edtUsuario.getText().toString();
                String nomEdificio  =  edtNombreEdificio.getText().toString();
                String tReservacion  =  edtTipoReservacion.getText().toString();

                if(docUsuario.isEmpty()){
                    edtUsuario.setError("Campo requerido");
                }else if(nomEdificio.isEmpty()) {
                    edtNombreEdificio.setError("Campo requerido");
                }else if(tReservacion.isEmpty()){
                    edtTipoReservacion.setError("Campo requerido");
                }else{
                    Calendar calendar =  Calendar.getInstance();

                    calendar.set(Calendar.YEAR ,  year);
                    calendar.set(Calendar.MONTH , month);
                    calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    fecha  = simpleDateFormat.format(calendar.getTime());  //Utilizara como fecha

                    if(canchas.size() ==0 ){
                        Toast.makeText(getApplicationContext(), "Seleccione una cancha", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new  Intent(  getApplicationContext() , HorarioReserva.class  );
                        i.putExtra("idCancha" ,  String.valueOf(canchas.get( spnCancha.getSelectedItemPosition()).getCancha())   );
                        i.putExtra("fecha" , fecha );
                        i.putExtra( "idTipo"  , idTipoReserva   );
                        i.putExtra( "dui" , duiUsuario);
                        startActivity(i);
                    }

                }





            }
        });


    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private void cargarUsuarios(){

        String accion = "dui";
        String idUsuario= String.valueOf(  usuarioLogin.getId()    );

        final Call<JsonElement>  listarUsuarios   =       reservasCanchasService.listarUsuarios(idUsuario , accion);

        listarUsuarios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if( response.isSuccessful()  ){

                    String jsonString  = response.body().toString();

                    if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        duisArrayList = new Gson().fromJson(jsonString , new TypeToken<List<Duis>>(){}.getType() );
                        if(duisArrayList.size()>0){
                            for(int i=0;  i<duisArrayList.size() ; i++){
                                duisUsuarios.add(  duisArrayList.get(i).getDui()  );
                            }
                            spinnerDialogUsuarios.showSpinerDialog();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de comunicacion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void listarCanchas(String idEdificio){ //Filtra las canchas al hacer la seleccion del edificio

        String edificio =  idEdificio;
        final Call<JsonElement> listarCanchas =  reservasCanchasService.filtrarCanchas(null, null , edificio   );

        listarCanchas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if(response.isSuccessful()){
                        String jsonString  = response.body().toString();

                        if(!existsError(jsonString)){

                            ArrayList<Cancha> listaCanchas = new Gson().fromJson(jsonString , new TypeToken<List<Cancha>>(){}.getType() );
                            //Canchas activas
                            for(Cancha cancha: listaCanchas){
                                if(cancha.getIdEstado()==1){
                                    canchas.add(cancha);
                                }
                            }

                        }else{
                            canchas = new ArrayList<>();
                        }

                        SpinnerCanchaAdapter canchasAdapter =  new SpinnerCanchaAdapter( getApplicationContext(), R.layout.support_simple_spinner_dropdown_item ,  canchas   );
                        spnCancha.setAdapter(canchasAdapter);

                    }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listarEdificios(){
        Call<JsonElement> listar =  reservasCanchasService.listarEdificiosReserva();
        listar.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();
                    if(!existsError(jsonString)){

                        ArrayList<Edificio> edificiosTemp = new Gson().fromJson(jsonString , new TypeToken<ArrayList<Edificio>>(){}.getType() );

                        for(Edificio edificio: edificiosTemp){
                            if(edificio.getIdEstado()==1){
                                edificios.add(edificio);
                            }
                        }

                        if(edificios.size() >  0){
                            for(int i=0; i<edificios.size() ;  i++){
                                nombresEdificios.add(  edificios.get(i).getNombre() );
                            }
                            spinnerDialogEdificios.showSpinerDialog();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error en la comunicacion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listarTiposReservas(){
        Call<JsonElement> listarTiposReserva =  reservasCanchasService.listarTiposReservas();

        listarTiposReserva.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();

                    if(!existsError(jsonString)){
                        tiposReservas = new Gson().fromJson(jsonString , new TypeToken<ArrayList<TipoReserva>>(){}.getType() );
                        if(tiposReservas.size() >  0){
                            for(int i=0; i<tiposReservas.size() ;  i++){
                                nombresTipoReservas.add(  tiposReservas.get(i).getTipo() );
                            }
                            spinnerDialogTipoReserva.showSpinerDialog();
                        }

                    }

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private int buscarIdCancha(String nombre){
        for (Cancha cancha: canchas) {
            if(cancha.getNombre() == nombre){
                return  cancha.getCancha();
            }
        }
        return 0;
    }

    private int buscarIdEdificio(String nombre){
        for(Edificio edificio:  edificios ){
            if(edificio.getNombre() ==  nombre){
                return edificio.getId();
            }
        }
        return 0;
    }

    private int buscarIdTipoReservacion(String nombre){

        for(TipoReserva tipoReserva:tiposReservas){
            if(tipoReserva.getTipo()==nombre){
                return tipoReserva.getId();
            }
        }
        return 0;
    }

    private boolean existsError(String jsonString){
        if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
            errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
            Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }





    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.imbUsuario){
            if(duisUsuarios.size()==0){//Si esta vacio los duis que los llene
                cargarUsuarios();
            }else{
                spinnerDialogUsuarios.showSpinerDialog();
            }

        }else if(v.getId() == R.id.imBEdificio){

            if(nombresEdificios.size()==0){
                listarEdificios();
            }else{
                spinnerDialogEdificios.showSpinerDialog();
            }
        }
        else if(v.getId() == R.id.imbTipoReservacion){
            if(nombresTipoReservas.size() == 0){
                listarTiposReservas();
            }else{
                spinnerDialogTipoReserva.showSpinerDialog();;
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent  =  new Intent( getApplicationContext() , ListaReservas.class  );
        startActivity(intent);
        finish();
    }
}
