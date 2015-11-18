package com.pmdm.farmaciasguardia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FarmasActivity extends Activity {

    Params pars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmas);

        ListView lv = (ListView) findViewById(R.id.ListFarmas);

        pars = Params.getInstance();

        FarmaciaAdapter adapter = new FarmaciaAdapter(this, pars.getFarmaciasSeleccionadas());

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String opt = ((Farmacia) parent.getAdapter().getItem(position)).telefono.replaceAll(" ", "");

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", opt, null));
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Farmacia: " + opt, Toast.LENGTH_SHORT).show();
            }

        });
        Log.d("Farma-FarmasActivity", "onCreate");
    }
}
