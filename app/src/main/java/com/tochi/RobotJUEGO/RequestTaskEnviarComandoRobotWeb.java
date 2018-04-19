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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created by 813743 on 25/11/2017.
 */
public class RequestTaskEnviarComandoRobotWeb extends AsyncTask<TextView, String, String> {


    TextView t;
    String result = "fail";
    String nombreRutina=null;
    String secuencia=null;
    String comando=null;
    String tiempo=null;
    String status=null;
    String id=null;

    @Override
    protected String doInBackground(TextView... params) {
        System.out.println("doInBackground - 1");
        this.t = params[0];
        System.out.println("doInBackground - 2");
        return UpdateComandoRobotGET(this.id,this.nombreRutina,this.secuencia,this.comando, this.tiempo, this.status);

        // este con POST y mandnadolos en un JSON, return UpdateComandoRobotGET(this.id,this.nombreRutina,this.secuencia,this.comando, this.tiempo, this.status);

    }

    final String UpdateComandoRobotGET(
            String id,
            String nombreRutina,
            String secuencia,
            String comando,
            String tiempo,
            String status
    )
    {
        System.out.println("UpdateComandoRobotWeb - 1");

        Constants constantValues=new Constants();

        //esto se usaba para cuando se mandan parametros
        try{
            id= URLEncoder.encode(id, "UTF-8");
            nombreRutina= URLEncoder.encode(nombreRutina, "UTF-8");
            secuencia= URLEncoder.encode(secuencia, "UTF-8");
            comando= URLEncoder.encode(comando, "UTF-8");
            tiempo= URLEncoder.encode(tiempo, "UTF-8");
            status= URLEncoder.encode(status, "UTF-8");


        }catch(UnsupportedEncodingException e){
            System.out.println("Update comando status - 12 error");
            e.printStackTrace();
        }


        String urlStr = constantValues.URL_UPDATE_COMANDO_ROBOT_GET+"/"+id+"/"+nombreRutina+"/"+secuencia+"/"+comando+"/"+tiempo+"/"+status;
        System.out.println("Update comando status - url es");
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
                System.out.println("Update comando status - 7");
                String NL = System.getProperty("line.separator");
                System.out.println("Update comando status - 8");
                while ((line = inStream.readLine()) != null) {
                    buffer.append(line + NL);
                }
                System.out.println("Update comando status - 9");
                inStream.close();
                System.out.println("Update comando status - 10");
                result = buffer.toString();
                System.out.println("Update comando status GetSomething - 11");

            } finally {
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Update comando status - 12 error");
            e.printStackTrace();
        } finally {
            System.out.println("Update comando status - 13");
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        System.out.println("Update comando status GetSomething - 14");
        return result;
    }


    final String UpdateComandoRobotPOST(
            String id,
            String nombreRutina,
            String secuencia,
            String comando,
            String tiempo,
            String status
    )
    {
        System.out.println("UpdateComandoRobotWeb - 1");

        Constants constantValues=new Constants();
        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        try{
            jsonParam.put("id", id);
            jsonParam.put("nombrerobot", nombreRutina);
            jsonParam.put("secuencia", secuencia);
            jsonParam.put("comando", comando);
            jsonParam.put("tiempo", tiempo);
            jsonParam.put("status", tiempo);

        }catch(Exception e){
            System.out.println("Update ComandoRobotWeb - 12. error de json");
            e.printStackTrace();
        }
        //esto se usaba para cuando se mandan parametros
        try{
            id= URLEncoder.encode(id, "UTF-8");
            nombreRutina= URLEncoder.encode(nombreRutina, "UTF-8");
            secuencia= URLEncoder.encode(secuencia, "UTF-8");
            comando= URLEncoder.encode(comando, "UTF-8");
            tiempo= URLEncoder.encode(tiempo, "UTF-8");
            status= URLEncoder.encode(status, "UTF-8");


        }catch(UnsupportedEncodingException e){
            System.out.println("AddNuevaComandoRobotWeb - 12 error");
            e.printStackTrace();
        }


        String urlStr = constantValues.URL_UPDATE_COMANDO_ROBOT_POST;

        BufferedReader inStream = null;



        try{

            System.out.println("Update ComandoRobotWeb - sql:"+urlStr);
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
                    System.out.println("Update ComandoRobotWeb - 7");
                    String NL = System.getProperty("line.separator");
                    System.out.println("Update ComandoRobotWeb - 8");
                    while ((line = inStream.readLine()) != null) {
                        buffer.append(line + NL);
                    }
                    System.out.println("Update ComandoRobotWeb - 9");
                    inStream.close();
                    System.out.println("Update   ComandoRobotWeb - 10");
                    result = buffer.toString();
                    System.out.println("GetSomething - 11");
                } else {
                    System.out.println(urlConnection.getResponseMessage());
                }



            } finally {
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("AddNuevaComandoRobotWeb - 12 error");
            e.printStackTrace();
        } finally {
            System.out.println("AddNuevaComandoRobotWeb - 13");
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        System.out.println("GetSomething - 14");
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("AddNuevaComandoRobotWeb onPostExecute - 10");
//        t.setText(result);
        System.out.println("AddNuevaComandoRobotWeb onPostExecute - 20");
    }


}
