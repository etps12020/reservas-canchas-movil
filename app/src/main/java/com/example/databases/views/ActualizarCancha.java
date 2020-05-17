package com.example.databases.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.RSIllegalArgumentException;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.EdificioAdapter;
import com.example.databases.adapters.EstadoCanchaAdapter;
import com.example.databases.adapters.TipoCanchaAdapter;
import com.example.databases.api.canchas.Cancha;
import com.example.databases.api.canchas.RequestUpdateCancha;
import com.example.databases.api.canchas.TipoCancha;
import com.example.databases.api.edificios.Edificio;
import com.example.databases.api.horarios.Horario;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.EstadoCancha;
import com.example.databases.api.utilidades.ErrorObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarCancha extends AppCompatActivity {

    private EditText edtNombre , edtDescripcion  , edtTelefono  , edtHoraInicio  , edtHoraFin  ;
    private Spinner spnEdificio , spnTipoCancha , spnEstadoCancha;
    private ImageView imvCancha;
    private ErrorObject errorObject;
    private SpinnerDialog spinnerDialogHoraInicio ,  spinnerDialogHoraFin;
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private Button btnActualizar;

    private ArrayList<Edificio> edificioArrayList;
    private ArrayList<TipoCancha> tipoCanchaArrayList;
    private ArrayList<EstadoCancha> estadoCanchaArrayList;
    private ArrayList<Horario> horarioArrayList;
    private ArrayList<String> horas;
    private Cancha canchaSeleccionada;
    private int indexHoraInicio , indexHoraFin;
    private String imagen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cancha);

        edtNombre  =  findViewById(R.id.edtNombre);
        edtDescripcion =  findViewById(R.id.edtDescripcion);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtHoraInicio  =  findViewById(R.id.edtHoraInicio);
        edtHoraFin =  findViewById(R.id.edtHoraFin);
        imvCancha =  findViewById(R.id.imvCancha);
        btnActualizar =   findViewById(R.id.btnActualizar);

        spnEdificio =  findViewById(R.id.spnEdificio);
        spnTipoCancha = findViewById(R.id.spnTipoCancha);
        spnEstadoCancha =  findViewById(R.id.spnEstadoCancha);

        horas =  new ArrayList<>();

        retrofitInit();

        final Intent i  =  getIntent();
        obtenerDatosCancha(i.getStringExtra("id"));
        imvCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        spinnerDialogHoraInicio = new SpinnerDialog( ActualizarCancha.this , horas , "Seleccione una hora de inicio");
        spinnerDialogHoraInicio.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String hora, int i) {
                indexHoraInicio  = i;
                edtHoraInicio.setText(hora);
            }
        });

        spinnerDialogHoraFin =  new SpinnerDialog( ActualizarCancha.this , horas ,  "Seleccione una hora fin");
        spinnerDialogHoraFin.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String hora, int i) {
                indexHoraFin = i;
                edtHoraFin.setText(hora);

            }
        });

        edtHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialogHoraInicio.showSpinerDialog();
            }
        });

        edtHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialogHoraFin.showSpinerDialog();
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre =  edtNombre.getText().toString();
                String descripcion =  edtDescripcion.getText().toString();
                String telefono =  edtTelefono.getText().toString();
                String horaInicio =  edtHoraInicio.getText().toString();
                String horaFin  =  edtHoraFin.getText().toString();

                if(nombre.isEmpty()){
                    edtNombre.setError("Campo nombre es requerido");
                }else if(descripcion.isEmpty()){
                    edtDescripcion.setError("Campo descripcion es requerido");
                }else if(telefono.isEmpty()){
                    edtTelefono.setError("Campo teleno es requerido");
                }else if(horaInicio.isEmpty()){
                    edtHoraInicio.setError("Seleccione una hora");
                }else if(horaFin.isEmpty()){
                    edtHoraFin.setError("Seleccione una hora");
                }else{

                }



                RequestUpdateCancha requestUpdateCancha =  new RequestUpdateCancha(
                        Integer.parseInt(i.getStringExtra("id")) ,
                        nombre ,
                        descripcion ,
                        telefono ,
                        horarioArrayList.get(indexHoraInicio).getHoraInicio() ,
                        horarioArrayList.get(indexHoraFin).getHoraFin() ,
                        edificioArrayList.get(spnEdificio.getSelectedItemPosition()).getId() ,
                        tipoCanchaArrayList.get( spnTipoCancha.getSelectedItemPosition()  ).getId() ,
                        estadoCanchaArrayList.get( spnEstadoCancha.getSelectedItemPosition()  ).getId(),
                        imagen
                );

                Call<JsonElement> actualizarCancha =  reservasCanchasService.actualizarCanchas(requestUpdateCancha);

                actualizarCancha.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        String jsonString  = response.body().toString();

                        Toast.makeText(getApplicationContext(), jsonString, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });


            }
        });

    }


    private void retrofitInit() {
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService = reservasCanchasClient.getReservasCanchasService();
    }

    private void obtenerEstadosCanchas(){

        Call<JsonElement> listarEstadosCanchas =  reservasCanchasService.listarEstadosCanchas();

        listarEstadosCanchas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    String jsonString  = response.body().toString();
                    if(!existsError(jsonString)) {
                        estadoCanchaArrayList  = new Gson().fromJson(jsonString , new TypeToken<List<EstadoCancha>>(){}.getType() );
                        EstadoCanchaAdapter  estadoCanchaAdapter =  new EstadoCanchaAdapter(getApplicationContext() ,  R.layout.support_simple_spinner_dropdown_item ,  estadoCanchaArrayList  );
                        spnEstadoCancha.setAdapter(estadoCanchaAdapter);
                        int indexEstadoCancha = findIndexEstadoCancha(  canchaSeleccionada.getIdEstado() );
                        spnEstadoCancha.setSelection(indexEstadoCancha);
                    }
                }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            }
        });
    }

    private void obtenerTiposCachas(){

        Call<JsonElement> listarTiposCanchas  = reservasCanchasService.listarTiposCanchas();

        listarTiposCanchas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();
                if(!existsError(jsonString)) {
                    tipoCanchaArrayList  = new Gson().fromJson(jsonString , new TypeToken<List<TipoCancha>>(){}.getType() );
                    TipoCanchaAdapter tipoCanchaAdapter  =  new TipoCanchaAdapter(getApplicationContext() ,  R.layout.support_simple_spinner_dropdown_item , tipoCanchaArrayList  );
                    spnTipoCancha.setAdapter(tipoCanchaAdapter);
                    //Buscar valor de tipo de cancha para la cancha seleccionada
                    int indexTipoCancha = findIndexTipoCancha(canchaSeleccionada.getIdTipoCancha());
                    spnTipoCancha.setSelection(indexTipoCancha); //Selecciona el valor de la cancha especificada
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void obtenerEdificios(){

        Call<JsonElement> listarEdificios  = reservasCanchasService.listarEdificios();

        listarEdificios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();
                if(!existsError(jsonString)) {
                    edificioArrayList  = new Gson().fromJson(jsonString , new TypeToken<List<Edificio>>(){}.getType() );
                    EdificioAdapter  edificioAdapter  =  new EdificioAdapter( getApplicationContext() ,  R.layout.support_simple_spinner_dropdown_item ,  edificioArrayList );
                    spnEdificio.setAdapter(edificioAdapter); //Adaptador y llenado de datos
                    int indexEdificio  =  findIndexEdificio( canchaSeleccionada.getIdEdificio() );
                    spnEdificio.setSelection(indexEdificio);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            }
        });
    }

    private void obtenerHorariosCanchas(){

        Call<JsonElement> listarHorariosCanhas = reservasCanchasService.listarHorariosCanchas();
        listarHorariosCanhas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    String jsonString  = response.body().toString();
                    if(!existsError(jsonString)) {
                        horarioArrayList = new Gson().fromJson(jsonString , new TypeToken<List<Horario>>(){}.getType() );
                        //Sacar los valores textuales y tambien los ids actuales de cada uno de las horas
                        for(Horario horario: horarioArrayList){
                            horas.add(horario.getHoraInicio()+" "+horario.getHoraFin());
                        }

                        //Indice de la hora Inicial
                        indexHoraInicio =  findIndexTextHorariosInicio(  canchaSeleccionada.getHoraInicio()  );
                        //Indice de la hora final
                        indexHoraFin = findIndexHorariosFin( canchaSeleccionada.getHoraFin()  );

                    }
                }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }

    private void obtenerDatosCancha(String id){

        final Call<JsonElement> obtenerCancha  =  reservasCanchasService.obtenerCancha(id);

        obtenerCancha.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                String jsonString  = response.body().toString();

                if(!existsError(jsonString)){

                    ArrayList<Cancha> canchaArrayList  = new Gson().fromJson(jsonString , new TypeToken<List<Cancha>>(){}.getType() );

                    if(canchaArrayList.size() > 0 ){

                        canchaSeleccionada=  canchaArrayList.get(0);

                        edtNombre.setText(canchaSeleccionada.getNombre());
                        edtTelefono.setText(canchaSeleccionada.getTelefonoContacto());
                        edtDescripcion.setText(canchaSeleccionada.getDescripcion());
                        edtHoraInicio.setText(canchaSeleccionada.getHoraInicio());
                        edtHoraFin.setText(canchaSeleccionada.getHoraFin());

                        byte[] decodedString = Base64.decode(  canchaSeleccionada.getImagen() , Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imvCancha.setImageBitmap(decodedByte);

                        //Obtener horarios de canchas
                        obtenerHorariosCanchas();
                        //Obtener Edificios Canchas
                        obtenerEdificios();
                        //Obtener Tipos canchas
                        obtenerTiposCachas();;
                        //Obtener estados de canchas 7
                        obtenerEstadosCanchas();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }


    private int findIndexEstadoCancha(int idEstadoCancha){
        int index = 0;
        for(EstadoCancha estadoCancha: estadoCanchaArrayList){
            if(estadoCancha.getId()== idEstadoCancha){
                return index;
            }else{
                index++;
            }
        }
        return -1;
    }

    private int findIndexEdificio(int idEdificio){
        int index = 0 ;
        for(Edificio edificio: edificioArrayList){
            if(edificio.getId() ==  idEdificio){
                return index;
            }else{
                index++;
            }
        }
        return -1;
    }

    private int findIndexTipoCancha(int idTipoCancha){
        int index = 0;
        for( TipoCancha tipoCancha :  tipoCanchaArrayList  ){
            if(tipoCancha.getId() ==  idTipoCancha){
                return index;
            }else{
                index++;
            }
        }
        return -1;
    }

    private  void cargarImagen(){
        Intent intent  =  new Intent( Intent.ACTION_PICK ,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI   );
        intent.setType("image/");
        startActivityForResult( intent.createChooser(intent , "Seleccionee la aplicacion ")  ,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Uri path =  data.getData();
//            imvCancha.setImageURI(path);
            try {
                Bitmap bitmap  = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() , path);
                imagen = convertirImagenString(bitmap);
                imvCancha.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean existsError(String jsonResponse){
        if(jsonResponse.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
            errorObject =  new Gson().fromJson(jsonResponse , ErrorObject.class);
            Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private int findIndexTextHorariosInicio(String horaInicio){
        int index = 0;
        for(Horario horario: horarioArrayList){
            if(horario.getHoraInicio().equals(horaInicio)){
                return index;
            }else{
                index++;
            }
        }
        return index;
    }
    private int findIndexHorariosFin(String horaFin){
        int index= 0;
        for(Horario horario: horarioArrayList){
            if(horario.getHoraInicio().equals(horaFin)){
                return index;
            }else{
                index++;
            }
        }

        return index;
    }

    private String convertirImagenString(Bitmap bitmap){
        ByteArrayOutputStream array =  new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG ,  100 , array   );
        byte[] imagenByte =  array.toByteArray();
        String imagenString = Base64.encodeToString(  imagenByte , Base64.DEFAULT );
        return imagenString;
    }
}
