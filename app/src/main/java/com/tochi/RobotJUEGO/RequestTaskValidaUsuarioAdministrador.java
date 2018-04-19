package com.tochi.RobotJUEGO;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

/**
 * Created by 813743 on 20/09/2016.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
class RequestTaskValidaUsuarioAdministrador extends AsyncTask<TextView, String, String> {
    TextView t;
    String result = "fail";

    String DuenoId=null;

    Activity nextActivity=null;

    Context nextC=null;


    String extraMessageUser=null;

    private ProgressDialog pd;

    public RequestTaskValidaUsuarioAdministrador(Context va) {
        System.out.println("set the activty in asyncTask - 1");
//	    this.nextActivity = activity;
        this.nextC = va;
        System.out.println("set the activty in asyncTask - 2");
    }


    @Override
    protected void onPreExecute() {
        pd = ProgressDialog.show(nextActivity, "Signing in",
                "Please wait while we are signing you in..");
    }

    @Override
    protected String doInBackground(TextView... params) {
        System.out.println("doInBackground - 1");
//    	this.t = params[0];
        System.out.println("doInBackground - 2");


        return ValidaDueno(this.DuenoId);


    }

    final String ValidaDueno(
            String DuenoId
    )
    {
        System.out.println("ValidaDueno - 1");

        Constants constantValues=new Constants();
        try{
            DuenoId=java.net.URLEncoder.encode(DuenoId, "UTF-8");
        }catch(UnsupportedEncodingException e){
            System.out.println("ValidaDueno - 12 error");
            e.printStackTrace();
        }
        String urlStr = constantValues.URL_GET_USER_ALL_INFO+DuenoId;


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
                            System.out.println("ValidaDueno - 7");
                            String NL = System.getProperty("line.separator");
                            System.out.println("ValidaDueno - 8");
                            while ((line = inStream.readLine()) != null) {
                                buffer.append(line + NL);
                            }
                            System.out.println("ValidaDueno - 9");
                            inStream.close();
                            System.out.println("ValidaDueno - 10");
                            result = buffer.toString();
                        } finally {
                            urlConnection.disconnect();
                        }

                 } catch (Exception e) {
                     // TODO Auto-generated catch block
                     System.out.println("ValidaDueno - 12 error");
                     e.printStackTrace();
                 } finally {
                     System.out.println("ValidaDueno - 13");
                     if (inStream != null) {
                         try {
                             inStream.close();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                 }


/*

        BufferedReader inStream = null;
        System.out.println("ValidaDueno - 2"+url);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            System.out.println("ValidaDueno - 3");
            HttpGet httpRequest = new HttpGet(url);
            System.out.println("ValidaDueno - 4");
            HttpResponse response = httpClient.execute(httpRequest);
            System.out.println("ValidaDueno - 5");
            inStream = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent()));
            System.out.println("ValidaDueno - 6");
            StringBuffer buffer = new StringBuffer("");
            String line = "";
            System.out.println("ValidaDueno - 7");
            String NL = System.getProperty("line.separator");
            System.out.println("ValidaDueno - 8");
            while ((line = inStream.readLine()) != null) {
                buffer.append(line + NL);
            }
            System.out.println("ValidaDueno - 9");
            inStream.close();
            System.out.println("ValidaDueno - 10");
            result = buffer.toString();
            System.out.println("GetSomething - 11");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("ValidaDueno - 12 error");
            e.printStackTrace();
        } finally {
            System.out.println("ValidaDueno - 13");
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
*/
        System.out.println("ValidaDueno - 14");
        pd.dismiss();
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("onPostExecute - 1");
//        Toast.makeText(nextC, result, Toast.LENGTH_LONG).show();
        System.out.println("onPostExecute - 2");
//        nextActivity.startActivity(new Intent(nextActivity, ListBuildingsActivity.class));

        Intent intent2 =new Intent(nextC, InicioActivity.class);
        intent2.putExtra("cual_usuario",this.extraMessageUser+"");
        System.out.println("Login activity passing,value:"+this.extraMessageUser);

        nextC.startActivity(intent2);

        System.out.println("onPostExecute - 3");
    }


}
