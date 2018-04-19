package com.tochi.RobotJUEGO;

    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Build;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ListView;

    import org.json.JSONArray;
    import org.json.JSONException;
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


    public class RobotRemotosActivity extends Activity {


        final ArrayList<String> arrayTasks = new ArrayList<>();
        ArrayAdapter<String> adapter;

        final ArrayList<DataDescriptorRobot> dRobot = new ArrayList<>();//store the list of map markers robots,with info from the web API

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_playlist);
            setContentView(R.layout.activity_robot_remotos);



            Button btnRegresar=(Button)  findViewById( R.id.botonRegresarLista);

            btnRegresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("Regresar - 3");


                    finish();


                }
            });

            Button btnRemoto=(Button)  findViewById( R.id.botonBotonesFlotantes);

            btnRemoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("Regresar - 3");

                    //CZ 09 Oct 2017:start
                    //Open the floating controls in system
                    System.out.println("onCreateView paso conectar 1.1.5");
                    startService(new Intent(RobotRemotosActivity.this, ControlesBotones.class));
                    //CZ 09 Oct 2017:end
                    //finish();


                }
            });



//day 3, lista
            ListView lstTaskListLocal=(ListView)  findViewById(R.id.lstTasks);
            //agregar listener al boton

            arrayTasks.clear();
//day 3  array para objectos

            // se declara el adapter, intermediario, se le pasa context, y el layout. se utilizara el layout default de andriod para lista
            //y la lista de array con los objetos
            adapter= new ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    arrayTasks
            );

            //ahora amarrar el view, lstTaskListLocal con el adapter, la parte visual view, con el adapter, y el array de objetos
            lstTaskListLocal.setAdapter(adapter);


            lstTaskListLocal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0,
                                               View arg1,
                                               int position,
                                               long arg3){
                    //remove from the list
                    //arrayTasks.remove(position);
                    //apply changes on the adapter
                    //adapter.notifyDataSetChanged();
                    System.out.println("Click on list item - 1");
                    Intent i = new Intent(RobotRemotosActivity.this, PublicaRobotRemotoActivity.class);
                    i.putExtra("num_del_playlist",position+"");
                    i.putExtra("pasos_del_playlist",position+",top,down,down,down,left,top,top");
                    i.putExtra("CualRobot", dRobot.get(position).getId());
                    i.putExtra("CaracteristicasRobotName", dRobot.get(position).getName());
                    i.putExtra("CaracteristicasRobotTipo", dRobot.get(position).getTipo());
                    i.putExtra("CaracteristicasRobotManada", dRobot.get(position).getManada());
                    System.out.println("Click on list item passing,value:"+position+",top,down,down,down,left,top,top");

                    startActivity(i);

                    System.out.println("Click on list item - 3");

                    return true;
                }

            });

            AsyncListViewLoader task=new AsyncListViewLoader();
            Constants constantes= new Constants();
//            task.parametroURL="http://mexico.brinkster.net/tochi_get_lista_rutinas_robots_mobil.asp?operacion=lista";
            task.parametroURL=constantes.URL_LIST_ROBOTS;
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)

                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                task.execute();


            System.out.println("PlaylistActivity paso 5");

        }//end method create

        //--method-----------------------------------------------que manda a guardar a la web --------------------
        /** Called when the user clicks the goToAddEdificio  button */


//-------------------------------------------------Tarea asyncrona que lee datos de la web Lista de Robots --------------------

        private class AsyncListViewLoader extends AsyncTask<String, Void, List<DataDescriptorRobot>> {
            private final ProgressDialog dialog = new ProgressDialog(RobotRemotosActivity.this); //asi porque estamos dentro de un fragment

            public String parametroURL="aqui";

            @Override
            protected void onPostExecute(List<DataDescriptorRobot> result) {
                super.onPostExecute(result);
                dialog.dismiss();
//            adpt.setItemList(result);
//            adpt.notifyDataSetChanged();
                System.out.println("PlaylistActivity onPostExecute paso 1");

                //cylce through List Robot result, y ponerlo en lista de ArrayTask
                if(result!=null) {
                    for (DataDescriptorRobot f : result) {
                        arrayTasks.add(f.getName().toUpperCase() + " - " + f.getTipo().toUpperCase() + " - " + f.getTransmite().toUpperCase() + "-");//
                        dRobot.add(f); //aqui almacenamos cada dataDescriptor de robot, asi esta lista tendra los datos del robot web
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("Downloading playlist...");
                dialog.show();
            }
            @Override
            protected List<DataDescriptorRobot> doInBackground(String... params) {

                System.out.println("PrimerFragment doInBackground paso 1");

                List<DataDescriptorRobot> result = new ArrayList<DataDescriptorRobot>();
                System.out.println("PrimerFragment doInBackground paso 2");
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
                    } finally {
                        System.out.println("Obten Lista - 13");
                        if (inStream != null) {
                            try {
                                inStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    System.out.println("PrimerFragmentdoInBackground paso 4.5");

//antes asp tenia { edificio [ {     }, {    } ] }
//antes asp                JSONObject jsonResponse = new JSONObject(new String(JSONResp));
//antes asp                JSONArray arr = jsonResponse.getJSONArray("edificio");


//ahora solo es [ {     }, {    } ]
                    JSONArray arr = new JSONArray(new String(JSONResp));


                    System.out.println("PrimerFragment doInBackground paso 6");

                    for (int i=0; i < arr.length(); i++) {
                        result.add(DataDescriptorRobot(arr.getJSONObject(i)));
                    }
                    System.out.println("PrimerFragment doInBackground paso 7");
                    return result;
                } catch(Throwable t) {
                    t.printStackTrace();
                }
                System.out.println("PrimerFragment doInBackground paso 8");
                return null;
            }
            private DataDescriptorRobot DataDescriptorRobot(JSONObject obj) throws JSONException {

                System.out.println("PlaylistActivity convertEdificio paso 10");


                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 10");

            String id = obj.getString("_id");
                String name = obj.getString("name");
                String tipo = obj.getString("tipo");
                String manada = obj.getString("manada");
                String posLon = obj.getString("lon");
                String posLat = obj.getString("lat");
                String rentaTiempo = obj.getString("rentatiempo");
                String rentaCosto = obj.getString("rentacosto");

                String transmite = obj.getString("transmite");
                String transmiteCanal=obj.getString("transmitecanal");
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 10.5:"+name);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 11:"+name);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 12:"+posLon);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 13:"+posLat);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 14:"+transmite);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 15:"+transmiteCanal);

                return new DataDescriptorRobot(id,name, tipo,manada,posLon,posLat,rentaTiempo,rentaCosto,transmite, transmiteCanal);

            }

        }//end private class


    }

