package com.tochi.RobotJUEGO;

    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.bluetooth.BluetoothAdapter;
    import android.bluetooth.BluetoothDevice;
    import android.bluetooth.BluetoothSocket;
    import android.content.Context;
    import android.location.Location;
    import android.location.LocationManager;
    import android.os.AsyncTask;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Handler;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

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
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;
    import java.util.Set;
    import java.util.UUID;


    public class PublicaRobotRemotoActivity extends Activity {

        // properties to handle the contol buttons, once the device is connected
        // START:

        public int conectado=0;

        public int grabando=0;

        public int grabacionIniciada=0;

        public int grabacionTerminada=0;

        public int timestapInicioGrabacion=0;
        public int secuenciaSig=0;

        View viewTemporal;


        ArrayList<PasosRutina> nuevaLista= new ArrayList<PasosRutina>();

        //END

        //Properties- to handle to BT connecting and detection
        //START
        private BluetoothAdapter myBluetooth = null;
        private Set pairedDevices;

        Button btnPaired;
        ListView devicelist;

        Button btnOn, btnOff, btnDis;

        String address = null;

        BluetoothSocket btSocket = null;

        private DataDescriptorRobot passedDatosDelRobot=null;

        private boolean isBtConnected = false;
        static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        //END

        //store the list of commnads robots,with info from the web API
        final ArrayList<DataDescriptorComandoRobot> dRobot = new ArrayList<>();//store the list of commnads robots,with info from the web API

        //bandera para controlar el loo infinto donde se estan leyendo los comandos Robot del API (mongoDB)
        public boolean mustStop=true;

        public DataDescriptorComandoRobot temporalParaBotones2=null;

        private MyRunnable myrunnable=null;
        private final Handler handler=new Handler();

        private View viewLa=null;

        //botones globales que se instancian en el click Play, y se usan en metdod ejecutaComandoRobot
         Button tiny01 ;
         Button tiny02 ;
         Button tiny03 ;
         Button tiny04 ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_publica_robot_remoto);

            Bundle extras = getIntent().getExtras();
            String  cualesBuldings=null;
            cualesBuldings="ninguno";
            if (extras != null) {
                String valuePos = extras.getString("num_del_playlist");
                String valuePasos = extras.getString("pasos_del_playlist");
                System.out.println("PublicaRobotRemoto step 1,value:" + valuePos);
                System.out.println("PublicaRobotRemoto step 2,value:" + valuePasos);


                //set the values passed, into the robot data structure, to be used

                passedDatosDelRobot=new DataDescriptorRobot(
                        (String) extras.get("CualRobot"),
                        (String) extras.get("CaracteristicasRobotName"),
                        (String) extras.get("CaracteristicasRobotTipo"),
                        (String) extras.get("CaracteristicasRobotManada"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );  //ponemos aqui estas caraacteristicas, para pdoelos usar en el metodo dodne se definen los botones
            }

            //logic to store the pasos on the class


//        viewTemporal=inflater.inflate(R.layout.fragment_tercer, container, false);
            Button btnSaveTaskLocal=(Button)  findViewById(R.id.conectarPlay);


            System.out.println("onCreateView paso conectar 1");

            btnSaveTaskLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("onCreateView paso conectar 1.1");
                    Button tiny = (Button)v.findViewById(R.id.conectarPlay);


                    Button btnPairedEsconder;
                    ListView devicelistEsconder;
                    btnPairedEsconder = (Button)findViewById(R.id.btnBuscarDispBlue02Play);
                    devicelistEsconder = (ListView)findViewById(R.id.lst_dispositivos02Play);
                    Button btnPlay=(Button)  findViewById(R.id.Play);
                    Button btnUp02=(Button)  findViewById(R.id.botonUpPlay);
                    Button btnDown02=(Button)  findViewById(R.id.botonDownPlay);
                    Button btnRight02=(Button)  findViewById(R.id.botonRightPlay);
                    Button btnLeft02=(Button)  findViewById(R.id.botonLeftPlay);

                    System.out.println("onCreateView paso conectar 1.2");

                    if(conectado==0) {//conecta con robot bola
                        System.out.println("onCreateView paso conectar 1.3");
                        tiny.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                        btnPairedEsconder.setVisibility(View.INVISIBLE);
                        devicelistEsconder.setVisibility(View.INVISIBLE);
                        btnPlay.setVisibility(View.VISIBLE);
                        btnUp02.setVisibility(View.VISIBLE);
                        btnDown02.setVisibility(View.VISIBLE);
                        btnRight02.setVisibility(View.VISIBLE);
                        btnLeft02.setVisibility(View.VISIBLE);
                        conectado=1;

                    }else{ //desconecta con robot bola
                        System.out.println("onCreateView paso conectar 1.4");
                        tiny.setBackgroundResource(R.mipmap.ic_conectarconbola);
                        btnPairedEsconder.setVisibility(View.VISIBLE);
                        devicelistEsconder.setVisibility(View.VISIBLE);
                        btnPlay.setVisibility(View.INVISIBLE);
                        btnUp02.setVisibility(View.INVISIBLE);
                        btnDown02.setVisibility(View.INVISIBLE);
                        btnRight02.setVisibility(View.INVISIBLE);
                        btnLeft02.setVisibility(View.INVISIBLE);

                        conectado=0;

                    }
                    System.out.println("onCreateView paso conectar 1. 5");
                }
            });


////////////////////////////////////////play the rutina ////////////////////////////////////

            Button btnPlay=(Button)  findViewById(R.id.Play);

            btnPlay.setVisibility(View.INVISIBLE); //que no se vea de inicio

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button tiny = (Button)v.findViewById(R.id.Play);

                    if(grabando==0) {//conecta con robot bola
                        tiny.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                        grabando=1;

                        if(conectado==1) {//hay conexion con el  con robot bola
                            //manda comando a Robot Bola
                            //START: send comando to arduino
                            System.out.println("turn UP Command 1");
//ssssi se usara                             if (btSocket!=null)
//siiii se usuara                            {

/*                                viewLa=v;//se usa para que desde donde el methodo ejecutaComando, se actualicen
                                System.out.println("PlaylistActivity paso 5");
                                                            */
//siiiiii se usuara                            }//end socket
                            System.out.println("turn UP Command 5");
                            //END: get the pending comands

                            mustStop=false;//start the while(!mustStop) para recorrer los comnados
                                        //empieza el ciclo infinito de handler myrunnable para recorrer los comandos penietnes
                        }else{ //desconectado del robot bola
                            //no hacer nada
                            mustStop=true;//detinien el ciclo infinito de AsyncTask lee records de commando robot de la DB usando API
                        }


                    }else{ //desconecta con robot bola
                        tiny.setBackgroundResource(R.mipmap.ic_conectarconbola);

                        grabando=0;

                        timestapInicioGrabacion=(int) System.currentTimeMillis();//obtener la hora de cuadno iniciamos a grabar


                    }



                }
            });
/////////////////////////////////////botones de control ///////////////////////////////////////////////////77








            ////////////////////////////////////////publicar ////////////////////////////////////



/////////////////////////////////////botones/funcionalidad de connecting BT ///////////////////////////////////////////////////77
            btnPaired = (Button)findViewById(R.id.btnBuscarDispBlue02Play);



            devicelist = (ListView)findViewById(R.id.lst_dispositivos02Play);

            System.out.println("onCreateView paso 31");
            myBluetooth = BluetoothAdapter.getDefaultAdapter();

            System.out.println("onCreateView paso 32");
            if(myBluetooth == null)
            {
                System.out.println("onCreateView paso 33");
                //Show a mensag. that thedevice has no bluetooth adapter
//            Toast.makeText(viewTemporal.getContext().getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
                Toast.makeText(this.getBaseContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
                System.out.println("onCreateView paso 34");
                //finish apk
                //   finish();
            }
            else
            {
                System.out.println("onCreateView paso 35");
                if (myBluetooth.isEnabled())
                {
                    System.out.println("onCreateView paso 36:Exioto!!!");
                }
                else
                {
                    System.out.println("onCreateView paso 37: no, pedir que activen bluetooth");
                    //Ask to the user turn the bluetooth on
                    //Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    //startActivityForResult(turnBTon,1);

                }
            }
            System.out.println("onCreateView paso 40:boton listener!!!");
            btnPaired.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    System.out.println("onCreateView paso 50");
                    pairedDevicesList(v); //method that will be called
                    System.out.println("onCreateView paso 60");
                }
            });
//        Button btnRegresar=(Button)  findViewById( R.id.botonRegresarRobot);
//        btnRegresar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("Regresar - 3");
//                finish();
//            }
//        });


            final int i = 0;



            myrunnable = new MyRunnable(handler,i);

            handler.post(myrunnable);

            //termina handler y runnable
        }



        ////////////////////////////////////////////////////////////////////////////////////
        //--method-----------------------------------------------lista paired BT --------------------
        /** Called to get the list of bluetooth devices paired with the mobile */
        ////////////////////////////////////////////////////////////////////////////////////
        private void pairedDevicesList(View v)
        {
            //pairedDevices = myBluetooth.getBondedDevices();
            System.out.println("onCreateView paso 21");
            Set<BluetoothDevice> pairedDevices =myBluetooth.getBondedDevices();
            System.out.println("onCreateView paso 22");
            ArrayList list = new ArrayList();
            System.out.println("onCreateView paso 23");
            if (pairedDevices.size()>0)
            {
                System.out.println("onCreateView paso 24");
                for(BluetoothDevice bt : pairedDevices)
                {
                    System.out.println("onCreateView paso 25");
                    list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
                    System.out.println("onCreateView paso 26"+bt.getAddress());
                }
                System.out.println("onCreateView paso 27");
            }
            else
            {
                System.out.println("onCreateView paso 28");
                Toast.makeText(v.getContext().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
            }
            System.out.println("onCreateView paso 29");
            final ArrayAdapter adapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1, list);
            System.out.println("onCreateView paso 29.1");
            devicelist.setAdapter(adapter);
            System.out.println("onCreateView paso 29.2");
            devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

            adapter.notifyDataSetChanged();
            System.out.println("onCreateView paso 29.3");
        }

        ////////////////////////////////////////////////////////////////////////////////////
        //--method-----------------------------------------------que manda a guardar a la web --------------------
        /** Called when the user clicks the goToAddEdificio  button */
        ////////////////////////////////////////////////////////////////////////////////////
        public void goToConectar(View view) {

            //get the result data
            String resultData = null;

            System.out.println("goToAddEdificio - 1");



        }
        private void msg(String s)
        {
            System.out.println("msg 1"+s);
            // Toast.makeText(getContext().getApplicationContext(),s,Toast.LENGTH_LONG).show();
            System.out.println("msg 2");
        }

        @Override
        public void onPause(){
            handler.removeCallbacks(myrunnable);
            super.onPause();
            mustStop=true;//stop the infinite loop in the handler myrunnable que procesa comandos traidos de la DB, usando el API
        }

        @Override
        public void onResume(){
            handler.postDelayed(myrunnable, 5000);
            super.onResume();
            mustStop=true;//stop the infinite loop in the handler myrunnable que procesa comandos traidos de la DB, usando el API
        }
        /*
        Custom method,
        se ejecuta con los comandos robot ques e leyenron como creados de la DB mongo usando el API



         */
        public void ejecutaComandoRobot(String comandoRobot) {


            System.out.println("ejecutaComandoRobot turn UP Command 2");

            //for each send comand to robot
            //for each set color to flecha


            String command ="UP";

            String arduinoCommand="";


/***START: foreach comando***/
            System.out.println("ejecutaComandoRobot turn UP Command 2");
            try
            {
                System.out.println("ejecutaComandoRobot  send  Command to arduino paso 3");
                if (command.equals("UP") ){
                    arduinoCommand="F";


                }else if (command.equals("DOWN") ){
                    arduinoCommand="B";

                }else if (command.equals("LEFT") ){
                    arduinoCommand="G";               ////pending to check with robot

                }else if (command.equals("RIGHT") ){
                    arduinoCommand="H";               ////pending to check with robot

                }else{
                    //error
                    System.out.println("send  Command to arduino paso Error");

                }
                System.out.println("send  Command to arduino paso 1");
//ssiiiiiii se usara                btSocket.getOutputStream().write(arduinoCommand.getBytes());
                System.out.println("send  Command to arduino paso 2");
                /***ENDSTART: foreach paso in the playlist***/
            }
//            catch (IOException e)
            catch (Exception e)
            {
                msg("Error"+e);
            }
            System.out.println("turn UP Command 4");



        }

                    /*
            Custom method,
            se ejecuta logica para los botones en base al comando robot ques e leyenron como creados de la DB mongo usando el API



            */


        private void logicToUpdateBotones2(String commandRobot){

            System.out.println("logicToUpdateBotones2  ejecutaComandoRobot turn UP Command 2");

            //for each send comand to robot
            //for each set color to flecha
            String command =commandRobot;

            String arduinoCommand="";


/***START: foreach comando***/
            System.out.println("logicToUpdateBotones2  ejecutaComandoRobot turn UP Command 2");
            try
            {
                System.out.println("logicToUpdateBotones2  ejecutaComandoRobot  send  Command to arduino paso 3");

                Button tempo01 = (Button) findViewById(R.id.botonUpPlay);
                //display.setText(values[0]);
                Button tempo02 = (Button)findViewById(R.id.botonDownPlay);
                Button tempo03 = (Button)findViewById(R.id.botonLeftPlay);
                Button tempo04 = (Button)findViewById(R.id.botonRightPlay);

                if (command.equals("UP") ){
                    arduinoCommand="F";
                    System.out.println("logicToUpdateBotones2 pinta boton UP");


                    tempo01.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                    tempo02.setBackgroundResource(R.mipmap.ic_botonflechadown);
                    tempo03.setBackgroundResource(R.mipmap.ic_botonflechaleft);
                    tempo04.setBackgroundResource(R.mipmap.ic_botonflecharight);

                }else if (command.equals("DOWN") ){
                    arduinoCommand="B";
                    System.out.println("logicToUpdateBotones2 pinta boton DOWN");
                    tempo01.setBackgroundResource(R.mipmap.ic_botonflechaup);
                    tempo02.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                    tempo03.setBackgroundResource(R.mipmap.ic_botonflechaleft);
                    tempo04.setBackgroundResource(R.mipmap.ic_botonflecharight);



                }else if (command.equals("LEFT") ){
                    arduinoCommand="G";               ////pending to check with robot
                    System.out.println("logicToUpdateBotones2 pinta boton LEFT");
                    tempo01.setBackgroundResource(R.mipmap.ic_botonflechaup);
                    tempo02.setBackgroundResource(R.mipmap.ic_botonflechadown);
                    tempo03.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                    tempo04.setBackgroundResource(R.mipmap.ic_botonflecharight);


                }else if (command.equals("RIGHT") ){
                    arduinoCommand="H";               ////pending to check with robot
                    System.out.println("logicToUpdateBotones2 pinta boton RIGHT");
                    tempo01.setBackgroundResource(R.mipmap.ic_botonflechaup);
                    tempo02.setBackgroundResource(R.mipmap.ic_botonflechadown);
                    tempo03.setBackgroundResource(R.mipmap.ic_botonflechaleft);
                    tempo04.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);

                }else{
                    //error
                    tempo01.setBackgroundResource(R.mipmap.ic_botonflechaup);
                    tempo02.setBackgroundResource(R.mipmap.ic_botonflechadown);
                    tempo03.setBackgroundResource(R.mipmap.ic_botonflechaleft);
                    tempo04.setBackgroundResource(R.mipmap.ic_botonflecharight);

                }
                System.out.println("logicToUpdateBotones2 despues de pintar");
//ssiiiiiii se usara                btSocket.getOutputStream().write(arduinoCommand.getBytes());

                /***ENDSTART: foreach paso in the playlist***/
            }
//            catch (IOException e)
            catch (Exception e)
            {
                msg("Error"+e);
            }
            System.out.println("logicToUpdateBotones2 salir4");




        }
        ////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////
        // se usa para click en cada elemento Paired de Bluettoth
        ////////////////////////////////////////////////////////////////////////////////////7
        ////////////////////////////////////////////////////////////////////////////////////
        private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
        {
            public void onItemClick (AdapterView av, View v, int arg2, long arg3)
            {
                System.out.println("myListClickListener onItemClick paso 0");

                // Get the device MAC address, the last 17 chars in the View
                String info = ((TextView) v).getText().toString();
                System.out.println("myListClickListener onItemClick paso 1, el valor del arg2 es:"+arg2);

                address = info.substring(info.length() - 17);

                System.out.println("myListClickListener onItemClick paso 2"+address);


                //call the widgtes
                ConnectBT bt  = new ConnectBT() ;
                System.out.println("myListClickListener onItemClick paso 3");
                bt.cualSocket=arg2;
                bt.execute();
                System.out.println("myListClickListener onItemClick paso 4");

                // Make an intent to start next activity.
//            Intent i = new Intent(DeviceList.this, ledControl.class);
                //Change the activity.
//            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
//            startActivity(i);
            }
        };


        ////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////
        // se usa para conectar al BLuetooth
        ////////////////////////////////////////////////////////////////////////////////////7
        ////////////////////////////////////////////////////////////////////////////////////

        private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
        {
            private boolean ConnectSuccess = true; //if it's here, it's almost connected
            public int cualSocket=0;
            @Override
            protected void onPreExecute()
            {
                System.out.println("ConnectBT onPreExecute paso 1");
//            progress = ProgressDialog.show(getContext().getApplicationContext(), "Connecting...", "Please wait!!!");  //show a progress dialog
                System.out.println("ConnectBT onPreExecute paso 2");
            }

            @Override
            protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
            {
                try
                {
                    System.out.println("ConnectBT doInBackground paso 1");
                    if (btSocket == null || !isBtConnected)
                    {
                        System.out.println("ConnectBT doInBackground paso 2");
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        System.out.println("ConnectBT doInBackground paso 3"+address);
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                        System.out.println("ConnectBT doInBackground paso 4");
                        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                        System.out.println("ConnectBT doInBackground paso 5");
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        System.out.println("ConnectBT doInBackground paso 6");
                        btSocket.connect();//start connection
                        System.out.println("ConnectBT doInBackground paso 7");
                    }
                }
                catch (IOException e)
                {
                    ConnectSuccess = false;//if the try failed, you can check the exception here
                    System.out.println("ConnectBT doInBackground paso 8");
                }
                System.out.println("ConnectBT doInBackground paso 9");
                return null;
            }
            @Override
            protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
            {
                System.out.println("ConnectBT onPostExecute paso 1");
                super.onPostExecute(result);
                System.out.println("ConnectBT onPostExecute paso 2");
                if (!ConnectSuccess)
                {
                    System.out.println("ConnectBT onPostExecute paso 3");
                    msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                    System.out.println("ConnectBT onPostExecute paso 4");
//                finish();
                }
                else
                {
                    System.out.println("ConnectBT onPostExecute paso 5");
                    msg("Connected.");
                    isBtConnected = true;
                }
                System.out.println("ConnectBT onPostExecute paso 6");
                //progress.dismiss();
                System.out.println("ConnectBT onPostExecute paso 7");
            }

        }//end class connectBT

        //-------------------------------------------------Tarea asyncrona que lee datos de la web Lista de Comandos del Robot --------------------

        private class AsyncListViewLoader extends AsyncTask<String, Void, List<DataDescriptorComandoRobot>> {
            private final ProgressDialog dialog = new ProgressDialog(PublicaRobotRemotoActivity.this); //asi porque estamos dentro de un fragment

            public String parametroURL="aqui";


            private Handler mHandler = new Handler();

            public DataDescriptorComandoRobot temporal=null;
            public DataDescriptorComandoRobot temporalParaBotones=null;

            @Override
            protected void onPostExecute(List<DataDescriptorComandoRobot> result) {
                super.onPostExecute(result);


                dialog.dismiss();
//            adpt.setItemList(result);
//            adpt.notifyDataSetChanged();
                System.out.println("PlaylistActivity onPostExecute paso 1");
                if(result!=null) {
                    //llena un array con los comandos, para poder utilizarlo despues en el handler, con espera de tiempo
                    for (DataDescriptorComandoRobot f : result) {
                        dRobot.add(f); //aqui almacenamos cada dataDescriptor de robot, asi esta lista tendra los datos del robot web
                        System.out.println("AsyncListViewLoader onPostExecute paso 2 cada comando  .."+f.getComando());
                        temporal=f;

                    }//end for
                    System.out.println("AsyncListViewLoader onPostExecute paso 2.antes HANDLER");

                    System.out.println("AsyncListViewLoader onPostExecute paso 3 despues pausa");
                }
//          }//fin del ciclo infinto
//END:aqui iria el loop infinito
            }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("Downloading comandos robot...");
                dialog.show();
            }
            @Override
            protected List<DataDescriptorComandoRobot> doInBackground(String... params) {

                System.out.println("PrimerFragment doInBackground paso 1");

                List<DataDescriptorComandoRobot> result = new ArrayList<DataDescriptorComandoRobot>();
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
                        result.add(getDataDescriptorComandoRobot(arr.getJSONObject(i)));
                    }
                    System.out.println("PrimerFragment doInBackground paso 7");
                    return result;
                } catch(Throwable t) {
                    t.printStackTrace();
                }
                System.out.println("PrimerFragment doInBackground paso 8");
                return null;
            }
            private DataDescriptorComandoRobot getDataDescriptorComandoRobot(JSONObject obj) throws JSONException {

                System.out.println("PlaylistActivity convertEdificio paso 10");


                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 10");

                String id = obj.getString("_id");
                String name = obj.getString("nombrerobot");
                String secuencia = obj.getString("secuencia");
                String comando = obj.getString("comando");
                String tiempo = obj.getString("tiempo");
                String status = obj.getString("status");
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 10.5:"+id);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 10.5:"+name);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 11:"+status);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 12:"+comando);
                System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 13:"+tiempo);

                return new DataDescriptorComandoRobot(id,name, secuencia,comando,tiempo,status);

            }

            /*
            Custom method,
            se ejecuta logica para los botones en base al comando robot ques e leyenron como creados de la DB mongo usando el API



            */




        }//end private class

        class MyRunnable implements Runnable {
            private Handler handler;
            private int i;


            public MyRunnable(Handler handler, int i) {
                this.handler = handler;
                this.i = i;
                System.out.println("222 myrunable create ");
            }
            @Override
            public void run() {
                System.out.println("222 myrunable run "+i+"mustStop"+mustStop);
                this.handler.postDelayed(this, 15000);//entre irevisar si hay mas commandos cada 15 segundos
                this.i++;
                System.out.println("222 myrunable run "+i+"mustStop"+mustStop);

                //leer los comandos pendientes, si estamos en PLAY
                if(!mustStop ) {
                    //START: get the pending comands
                    System.out.println("get the pending comands 1");
                    AsyncListViewLoader task = new AsyncListViewLoader();
                    Constants constantes = new Constants();

                    System.out.println("get the pending comands 2");
//                     antes traia todos los comandos de todos los robots
//                               task.parametroURL=constantes.URL_LIST_COMANDO_ROBOT;
                    //solo trae los comnados de nokbre robot que le mandemos
                    //en la logica del REST service tiene filtro de solo los pendientes de ejecutar
                    task.parametroURL = constantes.URL_LIST_COMANDO_ROBOT_PENDIENTE_EJECUTAR + "/" + passedDatosDelRobot.getName();


                    System.out.println("get the pending comands url es:" + task.parametroURL);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        task.execute();

                    System.out.println("get the pending comands 3");
                    //the information obtained from the API (DB pending commands) will be available , if any,
                    //in the dRobot list

                    //END: get the pending comands

                }

//START:aqui iria el loop infinito, de ejecutarlos
                System.out.println("222 myrunable run antes while "+i+"mustStop"+mustStop);
                while(!mustStop && dRobot.size()>0 ){//solo si estamos en PLAY




                    for (DataDescriptorComandoRobot f : dRobot) {
                        temporalParaBotones2=f;
                        System.out.println("222mHandler.postDelayed mandado desde  onPostExecute ciclo "+temporalParaBotones2.getComando());
                        try{
                            Thread.sleep(6000);//esperara 6segs entre cada comando
                        }catch(Exception e){

                            System.out.println("222mHandler.postDelayed esperando error "+e.toString());
                        }
                        //pinta boton
                        logicToUpdateBotones2(temporalParaBotones2.getComando());
                        //ejecuta el comando en el bluetooth al robot
                        ejecutaComandoRobot(temporalParaBotones2.getComando());



                        //mandar a actualizar el comando en la DB Mongo
                        //usar los datos de
                        //   si fuera get    http://peaceful-retreat-91246.herokuapp.com/api/comandosrobotscambiaestatus/5a25e90e7d4cea000420cd48/balderola/25/LEFT/123/ejecutado
                        // post actualiza solo uno con id pasado, http://peaceful-retreat-91246.herokuapp.com/api/comandosrobotscambiaestatus02/
                        // get este trae la lista de todos http://peaceful-retreat-91246.herokuapp.com/api/comandosrobots
                        // get trae solo uno con id pasado, http://peaceful-retreat-91246.herokuapp.com/api/comandosrobots/5a25e90e7d4cea000420cd48



                        RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                        System.out.println("ciclo handler 3.1"+passedDatosDelRobot.getName());

                        th.id=temporalParaBotones2.getId();
                        th.nombreRutina=temporalParaBotones2.getName();
                        th.secuencia=temporalParaBotones2.getSecuencia();


                        System.out.println("ciclo handler 3.2");
                        th.comando=temporalParaBotones2.getComando();

                        System.out.println("ciclo handler 3.3");
                        th.tiempo=temporalParaBotones2.getTiempo();
                        th.status="ejecutado";

                        TextView txtText=null;
                        th.execute(txtText); // here the result in text will be displayed
                        System.out.println("ciclo handler 4");


                        //mandar a agregar un nuevo el robotHistory en la DB Mongo
                        //usar los datos de
                        //   si fuera get    http://peaceful-retreat-91246.herokuapp.com/api/robothistorycambiaposicion/5a25e90e7d4cea000420cd48/balderola/muchasbolas/LEFT/19.428/-99.147
                        // post actualiza solo uno con id pasado, http://peaceful-retreat-91246.herokuapp.com/api/robothistorycambiaposicion02/
                        // get este trae la lista de todos http://peaceful-retreat-91246.herokuapp.com/api/robothistory
                        // get trae solo uno con id pasado, http://peaceful-retreat-91246.herokuapp.com/api/robothistoryWithId/5a25e90e7d4cea000420cd48

                        System.out.println("ciclo handler 4.1 Robot History"+passedDatosDelRobot.getName());


                        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location!=null){//el celular no tiene gps

                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();


                            RequestTaskEnviarRobotHistoryWeb thRobotHist=new RequestTaskEnviarRobotHistoryWeb();
                            System.out.println("ciclo handler 4.2 Robot History");

                            thRobotHist.id=temporalParaBotones2.getId();
                            thRobotHist.name=temporalParaBotones2.getName();
                            thRobotHist.tipo=passedDatosDelRobot.getTipo();
                            System.out.println("ciclo handler 4.3 Robot History");


                            thRobotHist.comandoprevio=temporalParaBotones2.getComando();

                            System.out.println("ciclo handler 4.4 Robot History");

                            thRobotHist.lon=longitude+"";
                            thRobotHist.lat=latitude+"";
                            System.out.println("ciclo handler 4.4 Robot History Lon"+longitude+"");
                            System.out.println("ciclo handler 4.3 Robot History Lat"+latitude+"");
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String strDate = sdf.format(c.getTime());
                            SimpleDateFormat sdfYY = new SimpleDateFormat("yyyy");
                            SimpleDateFormat sdfMM = new SimpleDateFormat("MM");
                            SimpleDateFormat sdfDD = new SimpleDateFormat("dd");
                            String strDateYY = sdfYY.format(c.getTime());
                            String strDateMM = sdfMM.format(c.getTime());
                            String strDateDD = sdfDD.format(c.getTime());
                            int millisecond = c.get(Calendar.MILLISECOND);
                            int second = c.get(Calendar.SECOND);
                            int minute = c.get(Calendar.MINUTE);
                            //12 hour format
                            int hour = c.get(Calendar.HOUR);
                            //24 hour format
                            int hourofday = c.get(Calendar.HOUR_OF_DAY);

                            thRobotHist.dia=strDateDD;
                            thRobotHist.mes=strDateMM;
                            thRobotHist.ano=strDateYY;
                            thRobotHist.hora=hourofday+"";
                            thRobotHist.minuto=minute+"";
                            thRobotHist.segundo=second+"";


                            TextView txtTextHist=null;
                            thRobotHist.execute(txtTextHist); // here the result in text will be displayed
                            System.out.println("ciclo handler 5 Robot History");

                        }

                    }//end for
                   // mustStop=true;
                    dRobot.clear();//remove all the elements

                }//fin del ciclo infinto
//END:aqui iria el loop infinito

            }
        }
    }

