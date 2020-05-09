package com.example.databases.api.utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Net {
    Context mContext;
    public Net(Context mContext)
    {
        this.mContext=mContext;
    }
    public boolean comprobarRed(){
        ConnectivityManager connectivityManager=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=connectivityManager.getActiveNetworkInfo();

        return (net!=null && net.isConnected());
    }
}

