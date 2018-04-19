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

class RequestTaskEdificioAdd extends AsyncTask<TextView, String, String> {
	TextView t;
	String result = "fail";
	
	String name=null;
	String estado=null;
	String pais=null;
	
    @Override
    protected String doInBackground(TextView... params) {
    	System.out.println("doInBackground - 1");
    	this.t = params[0];
    	System.out.println("doInBackground - 2");
		return AddEdificio(this.name,this.estado, this.pais);
		

    }

	final String AddEdificio(
			String name,
			String estado,
			String pais
			) 
	{
		System.out.println("AddEdificio - 1");
		
		Constants constantValues=new Constants();
		try{
			name= URLEncoder.encode(name, "UTF-8");
			estado= URLEncoder.encode(estado, "UTF-8");
			pais= URLEncoder.encode(pais, "UTF-8");
		}catch(UnsupportedEncodingException e){
			System.out.println("AddEdificio - 12 error");
			e.printStackTrace();
		}
		String urlStr = constantValues.URL_ADD_EDIFICIO+"name="+name+"&estado="+estado+"&pais="+pais;
				
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
				System.out.println("AddEdificio - 7");
				String NL = System.getProperty("line.separator");
				System.out.println("AddEdificio - 8");
				while ((line = inStream.readLine()) != null) {
					buffer.append(line + NL);
				}
				System.out.println("AddEdificio - 9");
				inStream.close();
				System.out.println("AddEdificio - 10");
				result = buffer.toString();
				System.out.println("GetSomething - 11");

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



		System.out.println("GetSomething - 14");
		return result;
	}
	
    @Override
    protected void onPostExecute(String result) {
    	System.out.println("onPostExecute - 1");
    	t.setText(result);
    	System.out.println("onPostExecute - 2");
    }
    

}
