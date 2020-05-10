package com.example.databases.api.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.databases.api.usuarios.ResponseLogin;
import com.google.gson.Gson;

public class Session {

    private static final String ARCHIVO_PREFERENCIAS ="reservasUtec";
    private static final String SESSION_USUARIO = "usuarioLogin";

    public static void crearSessionUsuario(String userJSON , Context context){
        SharedPreferences sharedPreferences  =  context.getSharedPreferences( ARCHIVO_PREFERENCIAS , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(SESSION_USUARIO ,  userJSON   );
        editor.commit();
    }

    public static ResponseLogin obtenerSessionUsuario(Context context){
        Gson gson =  new Gson();
        SharedPreferences  sharedPreferences  = context.getSharedPreferences( ARCHIVO_PREFERENCIAS , Context.MODE_PRIVATE);
        String strJson  = sharedPreferences.getString(SESSION_USUARIO , null);
        if(strJson!=null){
            ResponseLogin userLogin =  gson.fromJson(  strJson ,  ResponseLogin.class  );
            return userLogin;
        }else{
            return null;
        }

    }

}
