package com.pmdm.farmaciasguardia;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    public final String strUrl = "http://www.navarra.es/appsext/DescargarFichero/default.aspx?" +
            "codigoAcceso=OpenData&fichero=GuardiasFarmacias/Guardias.json";//.xml

    protected URL url;

    public TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView)findViewById(R.id.AppTitle);
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {}
        Log.d("Farma-MainActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        txt.setText(getString(R.string.app_title) + "\n\n" + getString(R.string.connecting) + "...");
        new HttpGetTask().execute();
        Log.d("Farma-MainActivity", "onStart");
    }



    //http://developer.android.com/reference/android/os/AsyncTask.html
    private class HttpGetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try{
                //http://developer.android.com/intl/es/reference/java/net/HttpURLConnection.html
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //conn.setRequestProperty("User-Agent", "");
                conn.setRequestMethod("GET");
                //conn.setDoInput(true);
                conn.connect();
                //https://es.wikipedia.org/wiki/Anexo:C%C3%B3digos_de_estado_HTTP
                Log.d("Farma-MainActivity", "respuesta " + conn.getResponseCode());
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder("");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                Log.d("Farma-MainActivity", "doInBackground");
                return sb.toString();
            }catch(Exception e){
                Log.d("Farma-MainActivity", "doInBackground Error");
                return "Error: "+e;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Farma-MainActivity", "onPostExecute");
            if (result.startsWith("Error")){
                txt.setText(txt.getText().toString() + "\n" + result + "...");
                Log.d("Farma-MainActivity", result);
            }else{
                Params pars = Params.getInstance();
                pars.setDataJSON(result);//pars.setDataXML(result);//
                Log.d("Farma-MainActivity", "Result:" + result.length() + " Zonas:" + pars.getGrupos().size() + " Fechas:" + pars.getFechas().size());
                Intent intent = new Intent(MainActivity.this,FechaActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }
    }

}
