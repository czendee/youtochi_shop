package com.tochi.RobotJUEGO.ChamanRV;

import android.app.IntentService;
import android.content.Intent;

import com.tochi.RobotJUEGO.Edificio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ControlesIntentService extends IntentService {
    public static final String ACTION_PROGRESO =
            "net.sgoliver.intent.action.PROGRESO";
    public static final String ACTION_FIN =
            "net.sgoliver.intent.action.FIN";

    public ControlesIntentService() {
        super("ControlesIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        int iter = intent.getIntExtra("iteraciones", 0);

        String elurl = intent.getStringExtra("url");

        String resultadoWeb="nada";
        System.out.println("***************onHandleIntent paso1");
        for(int i=1; i<=iter; i++) {
            //tareaLarga();
            resultadoWeb=tareaWeb(elurl);
            System.out.println("***************onHandleIntent paso555:"+resultadoWeb);
            //Comunicamos el progreso
            Intent bcIntent = new Intent();
            bcIntent.setAction(ACTION_PROGRESO);
            bcIntent.putExtra("progreso",i*10);
            int cuantos=i*10;
            bcIntent.putExtra("progresoWeb", resultadoWeb+cuantos);
            sendBroadcast(bcIntent);
        }

        Intent bcIntent = new Intent();
        bcIntent.setAction(ACTION_FIN);
        sendBroadcast(bcIntent);
    }

    private void tareaLarga()
    {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }

    private String tareaWeb(String parametroURL){
        System.out.println("PrimerFragment doInBackground paso 1");

        List<Edificio> result = new ArrayList<Edificio>();
        System.out.println("PrimerFragment doInBackground paso 2"+parametroURL);

        String resulto="inicio";
        try {

            BufferedReader inStream = null;
            System.out.println("ValidaDueno - 2"+parametroURL);
            String JSONResp =null;

            URL url = new URL(parametroURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            try {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //readStream(in);

                inStream = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuffer buffer = new StringBuffer("");

                System.out.println("Obten Lista - 5");
                String line = "";
                System.out.println("Obten Lista - 7");
                String NL = System.getProperty("line.separator");
                System.out.println("Obten Lista - 8");
                while ((line = inStream.readLine()) != null) {
                    buffer.append(line + NL);
                }
                System.out.println("Obten Lista - 9");
                inStream.close();
                System.out.println("Obten Lista - 10");
                JSONResp = buffer.toString();
                System.out.println("GetSomething - 11");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Obten Lista - 12 error");
                e.printStackTrace();
                resulto="error 1";
            } finally {
                System.out.println("Obten Lista - 13");
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                resulto="finaly 1";
            }

            System.out.println("PrimerFragmentdoInBackground paso 5");
            if(JSONResp!=null){
                JSONObject jsonResponse = new JSONObject(new String(JSONResp));
                JSONArray arr = jsonResponse.getJSONArray("edificio");

                System.out.println("PrimerFragment doInBackground paso 6");

                for (int i=0; i < arr.length(); i++) {
//                    result.add(convertEdificio(arr.getJSONObject(i)));
                }
                resulto="fui a la webz";

            }
            System.out.println("PrimerFragment doInBackground paso 7");
//            return result;
            return resulto;
        } catch(Throwable t) {
            t.printStackTrace();
            resulto="error 2"+t.toString();
        }
        System.out.println("PrimerFragment doInBackground paso 8");
        return resulto;
    }

}
