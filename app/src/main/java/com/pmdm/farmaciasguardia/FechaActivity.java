package com.pmdm.farmaciasguardia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaActivity extends Activity {

    Params pars;
    Long date;
    CalendarView cal;
    Button btn;
    String fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha);

        Log.d("Farma-FechaActivity", "onCreate");
        pars = Params.getInstance();
        cal = (CalendarView)findViewById(R.id.CalendarView1);
        btn = (Button)findViewById(R.id.BtnDate);
        date = (new Date()).getTime();
        fecha = new SimpleDateFormat("dd/MM/yyyy",java.util.Locale.getDefault()).format(date);
        pars.setFechaSeleccionada(fecha);
        btn.setText(getString(R.string.query) + " " + pars.getFechaSeleccionada());
        cal.setDate(date);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                if (cal.getDate() != date){
                    Log.d("Farma-FechaActivity", "onSelectedDayChange");
                    date = cal.getDate();
                    int mes = month + 1;
                    String dd = day<10?"0"+day:""+day;
                    String mm = mes<10?"0"+mes:""+mes;
                    String fecha = dd + "/" + mm + "/" + year;
                    pars.setFechaSeleccionada(fecha);
                    btn.setText(getString(R.string.query) + " " + pars.getFechaSeleccionada());
                }
            }
        });
    }

    public void onClickDate(View v){
        Log.d("Farma-FechaActivity", "Fecha:" + fecha);
        Intent intent = new Intent(FechaActivity.this, ZonaActivity.class);
        startActivity(intent);
    }
}
