package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.databases.adapters.EdificioAdapter;
import com.example.databases.adapters.SpinnerCanchaAdapter;
import com.example.databases.adapters.TipoCanchaAdapter;
import com.example.databases.adapters.TipoReservaAdapter;
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

import javax.microedition.khronos.egl.EGLDisplay;

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
    private Spinner spnCancha, spnTipoReserva ,spnUbicacion;
    private ImageButton   imbTipoReservacion , imbUsuario , imbEdificio;

    private EditText edtUsuario  ; //Campos de texto solo para presentacion
    private String fecha; //Fecha quemada //Fecha de reserva
    private String idCancha ="2"; //Cancha quemada //Id de cancha
    private String idCanchaSeleccionada , idEdificioSeleccionado , duiUsuario , idTipoReserva;

    private ReservasCanchasService reservasCanchasService; //Instancia de servicios
    private ReservasCanchasClient reservasCanchasClient;  //Clientee retrofit
    private ResponseLogin usuarioLogin;
    private ErrorObject errorObject=  null; //Objecto de error
    private LinearLayout linearLayout ,linearLayoutTipoReserva;

    String title="Realizar reserva";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_reserva);

        calendarViewReserva  =  findViewById(R.id.calendarViewReserva);

        linearLayoutTipoReserva =  findViewById(R.id.linearLayoutTipoReserva);

        usuarioLogin= Session.obtenerSessionUsuario(getApplicationContext());

        edtUsuario =  findViewById(R.id.edtUsuario);
        spnCancha =  findViewById(R.id.spnCancha);
        spnTipoReserva =  findViewById(R.id.spnTipoReserva);
        spnUbicacion =  findViewById(R.id.spnUbicacion);


        linearLayout =  findViewById(R.id.linearLayout);

        imbUsuario  =  findViewById(R.id.imbUsuario);

        getSupportActionBar().setTitle(title);

//        imbEdificio =  findViewById(R.id.imBEdificio);

        duisUsuarios  = new ArrayList<>();
        nombresCanchas = new ArrayList<>();
        nombresEdificios =  new ArrayList<>();
        nombresTipoReservas =  new ArrayList<>();
        canchas =  new ArrayList<>();

        tiposReservas =  new ArrayList<>();
        retrofitInit();


        imbUsuario.setOnClickListener(this); //Evento de busqueda de usuarios

        listarEdificios();
        listarTiposReservas();


        spinnerDialogUsuarios = new SpinnerDialog( RealizarReserva.this , duisUsuarios , "Seleccione un usuario"  );
        spinnerDialogUsuarios.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String dui, int i) {
                duiUsuario = dui;
                edtUsuario.setText(  dui  );
            }
        });


        if(usuarioLogin.getIdRol()==3){
            linearLayout.setVisibility(LinearLayout.GONE);
            linearLayoutTipoReserva.setVisibility(LinearLayout.GONE);
        }

        calendarViewReserva.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String docUsuario  = edtUsuario.getText().toString();

                if(docUsuario.isEmpty()  &&  (usuarioLogin.getIdRol()==1 || usuarioLogin.getIdRol()==2)  ){
                    edtUsuario.setError("Campo requerido");
                }else{
                    Calendar calendar =  Calendar.getInstance();

                    calendar.set(Calendar.YEAR ,  year);
                    calendar.set(Calendar.MONTH , month);
                    calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    fecha  = simpleDateFormat.format(calendar.getTime());  //Utilizara como fecha

                    if(canchas.size() ==0  ){
                        Toast.makeText(getApplicationContext(), "Seleccione una cancha", Toast.LENGTH_SHORT).show();
                    }else{

                        String idTipoReserva = (usuarioLogin.getIdRol()==3) ? "2" :String.valueOf( tiposReservas.get(  spnTipoReserva.getSelectedItemPosition() ).getId()   );

                        Intent i = new  Intent(  getApplicationContext() , HorarioReserva.class  );
                        i.putExtra("idCancha" ,  String.valueOf(canchas.get( spnCancha.getSelectedItemPosition()).getCancha())   );
                        i.putExtra("fecha" , fecha );
                        i.putExtra( "idTipo"  , idTipoReserva );
                        i.putExtra( "dui" , duiUsuario);
                        startActivity(i);
                    }

                }
            }
        });

        spnUbicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listarCanchas(String.valueOf(edificios.get(position).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        Call<JsonElement> listar =  reservasCanchasService.listarEdificiosActivosReserva("activos");
        listar.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();

                    if(!existsError(jsonString)){
                        edificios = new Gson().fromJson(jsonString , new TypeToken<ArrayList<Edificio>>(){}.getType() );
                        EdificioAdapter edificioAdapter =  new EdificioAdapter(getApplicationContext(), R.layout.custom_simple_spinner_item  , edificios);
                        spnUbicacion.setAdapter(edificioAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
                        if(tiposReservas.size()>0){
                            TipoReservaAdapter tipoReservaAdapter =  new TipoReservaAdapter(getApplicationContext() , R.layout.support_simple_spinner_dropdown_item ,  tiposReservas  );
                            spnTipoReserva.setAdapter(tipoReservaAdapter);
                        }


                        //spnTipoReserva
//                        if(tiposReservas.size() >  0){
//                            for(int i=0; i<tiposReservas.size() ;  i++){
//                                nombresTipoReservas.add(  tiposReservas.get(i).getTipo() );
//                            }
//                            spinnerDialogTipoReserva.showSpinerDialog();
//                        }

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

        }
//        else if(v.getId() == R.id.imBEdificio){
//
//            if(nombresEdificios.size()==0){
//                listarEdificios();
//            }else{
//                spinnerDialogEdificios.showSpinerDialog();
//            }
//        }
//        else if(v.getId() == R.id.imbTipoReservacion){
//            if(nombresTipoReservas.size() == 0){
//                listarTiposReservas();
//            }else{
//                spinnerDialogTipoReserva.showSpinerDialog();;
//            }
//        }

    }

    @Override
    public void onBackPressed() {
        Intent intent  =  new Intent( getApplicationContext() , ListaReservas.class  );
        startActivity(intent);
        finish();
    }
}
