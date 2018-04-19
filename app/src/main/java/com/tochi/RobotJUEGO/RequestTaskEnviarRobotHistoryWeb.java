package com.tochi.RobotJUEGO;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 813743 on 01/03/2018.
 */

public class RequestTaskEnviarRobotHistoryWeb extends AsyncTask<TextView, String, String> {


    TextView t;
    String result = "fail";
    String id=null;
    String name=null;
    String tipo=null;
    String comandoprevio=null;
    String lat=null;
    String lon=null;
    String ano=null;
    String mes=null;
    String dia=null;
    String hora=null;
    String minuto=null;
    String segundo=null;

    @Override
    protected String doInBackground(TextView... params) {
        System.out.println("doInBackground - 1");
        this.t = params[0];
        System.out.println("doInBackground - 2");
//        return UpdateRobotHistoryGET(this.id,this.name,this.tipo,this.comandoprevio, this.lat, this.lon, this.ano, this.mes, this.dia,this.hora, this.minuto, this.segundo);

        // este con POST y mandnadolos en un JSON, return UpdateRobotHistoryGET(this.id,this.nombreRutina,this.secuencia,this.comando, this.tiempo, this.status);
        return InsertRobotHistoryPOST("no hay id, es neuvo",this.name,this.tipo,this.comandoprevio, this.lat, this.lon, this.ano, this.mes, this.dia,this.hora, this.minuto, this.segundo);
    }
/*
Not used, as this is to add new robot history

    final String UpdateRobotHistoryGET(
            String id,
            String name,
            String tipo,
            String comandoprevio,
            String lat,
            String lon,
            String ano,
            String mes,
            String dia,
            String hora,
            String minuto,
            String segundo


    )
    {
        System.out.println("UpdateRobotHistoryGET - 1");

        Constants constantValues=new Constants();

        //esto se usaba para cuando se mandan parametros
        try{
            id= URLEncoder.encode(id, "UTF-8");
            name= URLEncoder.encode(name, "UTF-8");
            tipo= URLEncoder.encode(tipo, "UTF-8");
            comandoprevio= URLEncoder.encode(comandoprevio, "UTF-8");
            lat="0";
            lon="0";
            lat =lat==null? "0.0": URLEncoder.encode(lat, "UTF-8");
            lon= lon==null? "0.0": URLEncoder.encode(lon, "UTF-8");
            ano= URLEncoder.encode(ano, "UTF-8");
            mes= URLEncoder.encode(mes, "UTF-8");
            dia= URLEncoder.encode(dia, "UTF-8");
            hora= URLEncoder.encode(hora, "UTF-8");
            minuto= URLEncoder.encode(minuto, "UTF-8");
            segundo= URLEncoder.encode(segundo, "UTF-8");

        }catch(UnsupportedEncodingException e){
            System.out.println("Update RobotHistoryGET  status - 12 error");
            e.printStackTrace();
        }


        String urlStr = constantValues.URL_UPDATE_ROBOTHISTORY_POSICION_GET+"/"+id+"/"+name+"/"+tipo+"/"+comandoprevio+"/"+lat+"/"+lon+"/"+ano+"/"+mes+"/"+dia+"/"+hora+"/"+minuto+"/"+segundo;
        System.out.println("Update RobotHistoryGET status - url es");
        BufferedReader inStream = null;



        try{

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //readStream(in);

                inStream = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuffer buffer = new StringBuffer("");
                String line = "";
                System.out.println("Update RobotHistoryGET status - 7");
                String NL = System.getProperty("line.separator");
                System.out.println("Update RobotHistoryGET status - 8");
                while ((line = inStream.readLine()) != null) {
                    buffer.append(line + NL);
                }
                System.out.println("Update RobotHistoryGET status - 9");
                inStream.close();
                System.out.println("Update RobotHistoryGET status - 10");
                result = buffer.toString();
                System.out.println("Update RobotHistoryGET status GetSomething - 11");

            } finally {
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Update RobotHistoryGET status - 12 error");
            e.printStackTrace();
        } finally {
            System.out.println("Update RobotHistoryGET status - 13");
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        System.out.println("Update RobotHistoryGET status GetSomething - 14");
        return result;
    }
*/

    final String InsertRobotHistoryPOST(
            String id,
            String name,
            String tipo,
            String comandoprevio,
            String lat,
            String lon,
            String ano,
            String mes,
            String dia,
            String hora,
            String minuto,
            String segundo
    )
    {
        System.out.println("Update RobotHistoryPOST - 1");

        Constants constantValues=new Constants();
        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        try{
//            jsonParam.put("id", id);  not needed as this is a new robotHistory
            jsonParam.put("nombre", name);
            jsonParam.put("tipo", tipo);
            jsonParam.put("comandoprevio", comandoprevio);
            jsonParam.put("lat", lat);
            jsonParam.put("lon", lon);
            jsonParam.put("ano", ano);
            jsonParam.put("mes", mes);
            jsonParam.put("dia", dia);
            jsonParam.put("hora", hora);
            jsonParam.put("minuto", minuto);
            jsonParam.put("segundo", segundo);

        }catch(Exception e){
            System.out.println("Update RobotHistoryPOST - 12. error de json");
            e.printStackTrace();
        }



        String urlStr = constantValues.URL_ADD_ROBOTHISTORY_POSICION_POST;

        BufferedReader inStream = null;



        try{

            System.out.println("Add new RobotHistoryPOST - sql:"+urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(jsonParam.toString());
            wr.flush();

            try {

                //display what returns the POST request

                StringBuilder sb = new StringBuilder();
                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //readStream(in);

                    inStream = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    StringBuffer buffer = new StringBuffer("");
                    String line = "";
                    System.out.println("Add new RobotHistoryPOST - 7");
                    String NL = System.getProperty("line.separator");
                    System.out.println("Add new RobotHistoryPOST - 8");
                    while ((line = inStream.readLine()) != null) {
                        buffer.append(line + NL);
                    }
                    System.out.println("Add new RobotHistoryPOST - 9");
                    inStream.close();
                    System.out.println("Add new   RobotHistoryPOST - 10");
                    result = buffer.toString();
                    System.out.println("Add new RobotHistoryPOST - 11");
                } else {
                    System.out.println(urlConnection.getResponseMessage());
                }



            } finally {
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Add new RobotHistoryPOST  - 12 error");
            e.printStackTrace();
        } finally {
            System.out.println("Add new RobotHistoryPOST  - 13");
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        System.out.println("GetSomething RobotHistoryPOST - 14");
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("Add new RobotHistoryPOST onPostExecute - 10");
//        t.setText(result);
        System.out.println("Add new RobotHistoryPOST onPostExecute - 20");
    }


}
