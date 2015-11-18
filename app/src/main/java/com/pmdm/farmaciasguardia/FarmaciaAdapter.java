package com.pmdm.farmaciasguardia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FarmaciaAdapter extends ArrayAdapter<Farmacia> {

    Activity context;
    ArrayList<Farmacia> datos;

    public FarmaciaAdapter(Activity context, ArrayList<Farmacia> datos) {
        super(context, R.layout.listitem_farmacia, datos);
        this.context = context;
        this.datos = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Farmacia farmacia = getItem(position);
        if (convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_farmacia,  parent, false);
        TextView tvNombre = (TextView)convertView.findViewById(R.id.LblNombre);
        TextView tvLocalidad = (TextView)convertView.findViewById(R.id.LblLocalidad);
        TextView tvDireccion = (TextView)convertView.findViewById(R.id.LblDireccion);
        TextView tvTelefono = (TextView)convertView.findViewById(R.id.LblTelefono);
        tvNombre.setText(farmacia.nombre);
        tvLocalidad.setText(farmacia.localidad);
        tvDireccion.setText(farmacia.direccion);
        tvTelefono.setText(farmacia.telefono);
        return convertView;
    }
}
