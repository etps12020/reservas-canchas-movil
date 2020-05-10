package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.databases.R;

public class RealizarReserva extends AppCompatActivity {

    CalendarView calendarViewReserva;
    Long date;
    String fecha= "2020-05-08";
    String idCancha ="2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_reserva);

        calendarViewReserva  =  findViewById(R.id.calendarViewReserva);

        calendarViewReserva.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Intent i = new  Intent(  getApplicationContext() , HorarioReserva.class  );
                i.putExtra("idCancha" , idCancha);
                i.putExtra("fecha" , fecha );
                startActivity(i);

            }
        });


    }
}
