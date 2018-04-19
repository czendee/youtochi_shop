package com.tochi.RobotJUEGO;


import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 813743 on 15/10/2016.
 */

class RequestTaskAddNuevaRutinaWeb extends AsyncTask<TextView, String, String> {
    TextView t;
    String result = "fail";
    String nombreRutina=null;
    String secuencia=null;
    String comando=null;
    String tiempo=null;

    @Override
    protected String doInBackground(TextView... params) {
        System.out.println("doInBackground - 1");
        this.t = params[0];
        System.out.println("doInBackground - 2");
        return AddNuevaRutina(this.nombreRutina,this.secuencia,this.comando, this.tiempo);


    }

    final String AddNuevaRutina(
            String nombreRutina,
            String secuencia,
            String comando,
            String tiempo
    )
    {
        System.out.println("AddNuevaRutinaWeb - 1");

        Constants constantValues=new Constants();
        try{
            nombreRutina= URLEncoder.encode(nombreRutina, "UTF-8");
            secuencia= URLEncoder.encode(secuencia, "UTF-8");
            comando= URLEncoder.encode(comando, "UTF-8");
            tiempo= URLEncoder.encode(tiempo, "UTF-8");
        }catch(UnsupportedEncodingException e){
            System.out.println("AddNuevaRutinaWeb - 12 error");
            e.printStackTrace();
        }
        String urlStr = constantValues.URL_ADD_RUTINA_ROBOT+"nombre="+nombreRutina+"&secuencia="+secuencia+"&comando="+comando+"&tiempo="+tiempo;

        BufferedReader inStream = null;



        try{
            System.out.println("AddNuevaRutinaWeb - sql:"+urlStr);
            System.out.println("AddNuevaRutinaWeb - sql:"+urlStr);
            System.out.println("AddNuevaRutinaWeb - sql:"+urlStr);
            System.out.println("AddNuevaRutinaWeb - sql:"+urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //readStream(in);

                inStream = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuffer buffer = new StringBuffer("");
                String line = "";
                System.out.println("AddNuevaRutinaWeb - 7");
                String NL = System.getProperty("line.separator");
                System.out.println("AddNuevaRutinaWeb - 8");
                while ((line = inStream.readLine()) != null) {
                    buffer.append(line + NL);
                }
                System.out.println("AddNuevaRutinaWeb - 9");
                inStream.close();
                System.out.println("AddNuevaRutinaWeb - 10");
                result = buffer.toString();
                System.out.println("GetSomething - 11");

            } finally {
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("AddNuevaRutinaWeb - 12 error");
            e.printStackTrace();
        } finally {
            System.out.println("AddNuevaRutinaWeb - 13");
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
        System.out.println("AddNuevaRutinaWeb onPostExecute - 1");
        t.setText(result);
        System.out.println("AddNuevaRutinaWeb onPostExecute - 2");
    }


}
