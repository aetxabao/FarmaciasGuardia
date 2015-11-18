package com.pmdm.farmaciasguardia;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class Params {

    protected String fechaSeleccionada = null;
    protected String grupoSeleccionado = null;
    protected ArrayList<Farmacia> farmacias = null;
    protected ArrayList<String> grupos = null;
    protected ArrayList<String> fechas = null;

    private static final Params INSTANCE = new Params();

    private Params() {}

    public static Params getInstance() {
        return INSTANCE;
    }

    /**
     * Comparar la diferencia de tiempo con respecto al procesamiento JSON.
     * @param xml Cadena con el texto en formato XML.
     */
    protected void setDataXML(String xml)  {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document doc;
        XPathFactory xpf;
        XPath xp;
        XPathExpression xpe;
        NodeList farmaciasdeguardia;
        int n;
        grupos = new ArrayList<>();
        fechas = new ArrayList<>();
        farmacias = new ArrayList<>();

        Set<String> setGrupo = new HashSet<>();
        Set<String> setFecha = new HashSet<>();

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

            doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            doc.getDocumentElement().normalize();

            xpf = XPathFactory.newInstance();
            xp = xpf.newXPath();

            xpe = xp.compile("/FARMACIASDEGUARDIA/FARMACIAGUARDIA");
            farmaciasdeguardia = (NodeList) xpe.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);
            n = farmaciasdeguardia.getLength();
            Log.d("Farma-Params", "n="+n);
            for (int i = 0; i < n; i++) {
                String nombre=null, localidad=null, direccion=null, telefono=null, zona=null, fecha=null;
                try {
                    Element farmaciaguardia = (Element) farmaciasdeguardia.item(i);
                     nombre = xp.evaluate("FARMACIA", farmaciaguardia);
                     localidad = xp.evaluate("LOCALIDAD", farmaciaguardia);
                     direccion = xp.evaluate("DIRECCION", farmaciaguardia);
                     telefono = xp.evaluate("TELEFONO", farmaciaguardia);
                     if ((telefono!=null) && (telefono.length()>0)){
                         telefono.replaceAll(" ", "");
                         if (telefono.length()==9)
                         telefono = telefono.substring(0, 3) + " " + telefono.substring(3, telefono.length());
                     }
                     zona = xp.evaluate("GRUPO", farmaciaguardia);
                     fecha = xp.evaluate("FECHA", farmaciaguardia);
                    Farmacia far = new Farmacia(nombre, localidad, direccion, telefono, zona, fecha);
                    farmacias.add(far);
                    setGrupo.add(zona);
                    setFecha.add(fecha);
                }catch (Exception e){
                    Log.d("Farma-Params", "nombre="+nombre+" localidad="+localidad+" direccion="+direccion+
                                         " telefono="+telefono+" zona="+zona+" fecha="+fecha);
                }
            }
            for (String str : setGrupo)
                grupos.add(str);
            for (String str : setFecha)
                fechas.add(str);
        }catch(Exception e){
            Log.d("Farma-Params", "Error: "+ e.getMessage() + "\n" + xml);
        }
    }

    public ArrayList<Farmacia> getFarmaciasSeleccionadas(){
        ArrayList<Farmacia> farms = new ArrayList<>();
        for (Farmacia farm : farmacias) {
            if (farm.zona.equalsIgnoreCase(this.grupoSeleccionado) &&
                    farm.fecha.equals(this.fechaSeleccionada)){
                farms.add(farm);
            }
        }
        return farms;
    }

    /**
     * Comparar la diferencia de tiempo con respecto al procesamiento XML.
     * @param json Cadena con el texto en formato JSON.
     */
    public void setDataJSON(String json) {
        try {
            grupos = new ArrayList<String>();
            fechas = new ArrayList<String>();
            farmacias = new ArrayList<>();

            Set<String> setGrupo = new HashSet<String>();
            Set<String> setFecha = new HashSet<String>();

            JSONArray jaFarms = new JSONArray(json);
            for (int i = 0; i < jaFarms.length(); i++) {

                String nombre=null, localidad=null, direccion=null, telefono=null, zona=null, fecha=null;
                try {
                    JSONObject joFarm = jaFarms.getJSONObject(i);
                    nombre = joFarm.getString("FARMACIA");
                    localidad = joFarm.getString("LOCALIDAD");
                    direccion = joFarm.getString("DIRECCIÓN");
                    telefono = joFarm.getString("TELÉFONO");
                    if ((telefono!=null) && (telefono.length()>0)){
                        telefono.replaceAll(" ", "");
                        if (telefono.length()>0)
                            telefono = telefono.substring(0, 3) + " " + telefono.substring(3, telefono.length());
                    }
                    zona = joFarm.getString("GRUPO");
                    fecha = joFarm.getString("FECHA");
                    Farmacia far = new Farmacia(nombre, localidad, direccion, telefono, zona, fecha);
                    farmacias.add(far);
                    setGrupo.add(zona);
                    setFecha.add(fecha);
                }catch (Exception e){
                    Log.d("Farma-Params", "nombre="+nombre+" localidad="+localidad+" direccion="+direccion+
                            " telefono="+telefono+" zona="+zona+" fecha="+fecha);
                }
            }

            for (String str : setGrupo)
                grupos.add(str);
            for (String str : setFecha)
                fechas.add(str);

        } catch (Exception e) {
        Log.d("Farma-Params", "Error: " + e.getMessage() + "\n" + json);
        }
    }

    public String getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(String fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

    public String getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(String grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public ArrayList<String> getGrupos() {
        return grupos;
    }

    public ArrayList<String> getFechas() {
        return fechas;
    }

}
