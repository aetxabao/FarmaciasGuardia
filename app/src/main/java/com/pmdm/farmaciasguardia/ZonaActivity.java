package com.pmdm.farmaciasguardia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ZonaActivity extends Activity {

    Params pars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona);

        ListView lv = (ListView)findViewById(R.id.ListZonas);

        pars = Params.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pars.getGrupos());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                String zona = (String) parent.getItemAtPosition(position);
                pars.setGrupoSeleccionado(zona);
                Toast.makeText(getApplicationContext(), "Zona: " + zona, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ZonaActivity.this, FarmasActivity.class);
                startActivity(intent);
            }
        });
        Log.d("Farma-ZonaActivity", "onCreate");
    }

}
