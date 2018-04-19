package com.tochi.RobotJUEGO;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class GrabarActivity extends Activity {

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

    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //END


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar);



//        viewTemporal=inflater.inflate(R.layout.fragment_tercer, container, false);
        Button btnSaveTaskLocal=(Button)  findViewById(R.id.conectar);

        EditText nombre= (EditText) findViewById(R.id.nombreRutina);
        nombre.setVisibility(View.INVISIBLE);

        System.out.println("onCreateView paso conectar 1");

        btnSaveTaskLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onCreateView paso conectar 1.1");
                Button tiny = (Button)v.findViewById(R.id.conectar);

                Button btnPairedEsconder;
                ListView devicelistEsconder;
                btnPairedEsconder = (Button)findViewById(R.id.btnBuscarDispBlue02);
                devicelistEsconder = (ListView)findViewById(R.id.lst_dispositivos02);
                Button btnGrabar=(Button)  findViewById(R.id.Grabar);
                Button btnUp02=(Button)  findViewById(R.id.botonUp);
                Button btnDown02=(Button)  findViewById(R.id.botonDown);
                Button btnRight02=(Button)  findViewById(R.id.botonRight);
                Button btnLeft02=(Button)  findViewById(R.id.botonLeft);
                Button tiny04 = (Button)findViewById(R.id.InicioGrabar);
                Button tiny05 = (Button)findViewById(R.id.FinGrabar);

                System.out.println("onCreateView paso conectar 1.2");

                if(conectado==0) {//conecta con robot bola
                    System.out.println("onCreateView paso conectar 1.3");
                    tiny.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                    btnPairedEsconder.setVisibility(View.INVISIBLE);
                    devicelistEsconder.setVisibility(View.INVISIBLE);
                    btnGrabar.setVisibility(View.VISIBLE);
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
                    btnGrabar.setVisibility(View.INVISIBLE);
                    btnUp02.setVisibility(View.INVISIBLE);
                    btnDown02.setVisibility(View.INVISIBLE);
                    btnRight02.setVisibility(View.INVISIBLE);
                    btnLeft02.setVisibility(View.INVISIBLE);
                    tiny04.setVisibility(View.INVISIBLE);
                    tiny05.setVisibility(View.INVISIBLE);
                    conectado=0;
                }
                System.out.println("onCreateView paso conectar 1. 5");
            }
        });

        Button btnSaveTaskLocal01=(Button)  findViewById(R.id.InicioGrabar);


        btnSaveTaskLocal01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button tiny = (Button)v.findViewById(R.id.InicioGrabar);
                Button tiny03 = (Button)findViewById(R.id.Publicar);
                Button tiny04 = (Button)findViewById(R.id.InicioGrabar);
                Button tiny05 = (Button)findViewById(R.id.FinGrabar);
                EditText nombre= (EditText) findViewById(R.id.nombreRutina);

                if(grabacionIniciada==0) {//conecta con robot bola
                    tiny.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                    tiny05.setVisibility(View.VISIBLE);//muestra fin



                    grabando=1;
                    grabacionIniciada=1;
                    timestapInicioGrabacion=(int) System.currentTimeMillis();//obtener la hora de cuadno comenzamos a grabar

                    grabacionTerminada=0;

                }else{ //desconecta con robot bola
                    tiny.setBackgroundResource(R.mipmap.ic_conectarconbola);
                    tiny05.setVisibility(View.INVISIBLE);//esconde fin
                    tiny03.setVisibility(View.INVISIBLE);//esconde publicar
                    nombre.setVisibility(View.INVISIBLE);

                    grabacionIniciada=0;
                    timestapInicioGrabacion=0;

                    grabacionTerminada=0;
                }

            }
        });


        Button btnSaveTaskLocal02=(Button)  findViewById(R.id.FinGrabar);
        btnSaveTaskLocal02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button tiny = (Button)v.findViewById(R.id.FinGrabar);
                Button tiny03 = (Button)findViewById(R.id.Publicar);
                Button tiny04 = (Button)findViewById(R.id.InicioGrabar);
                Button tiny05 = (Button)findViewById(R.id.FinGrabar);
                EditText nombre= (EditText) findViewById(R.id.nombreRutina);


                if(grabacionTerminada==0) {//termina o pone pausa la grabacion
                    tiny.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
                    tiny03.setVisibility(View.VISIBLE);//muestra publicar
                    nombre.setVisibility(View.VISIBLE);

                    grabacionTerminada=1;
                    System.out.println("termina grabacion");

                }else{ //si esta en pausa la grabacion, continua otra vez, desde el ultimo comando
                    tiny.setBackgroundResource(R.mipmap.ic_conectarconbola);
                    tiny03.setVisibility(View.INVISIBLE);//esconde publicar
                    nombre.setVisibility(View.INVISIBLE);
                    grabacionTerminada=0;

                    timestapInicioGrabacion=(int) System.currentTimeMillis();//obtener la hora de cuadno continuamos a grabar
                    System.out.println("continua grabacion");
                }

            }
        });


        Button btnSaveTaskLocal04=(Button)  findViewById(R.id.Grabar);

        btnSaveTaskLocal04.setVisibility(View.INVISIBLE); //que no se vea de inicio

        btnSaveTaskLocal04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button tiny = (Button)v.findViewById(R.id.Grabar);
                Button tiny03 = (Button)findViewById(R.id.Publicar);
                Button tiny04 = (Button)findViewById(R.id.InicioGrabar);
                Button tiny05 = (Button)findViewById(R.id.FinGrabar);
                EditText nombre= (EditText) findViewById(R.id.nombreRutina);
                if(grabando==0) {//conecta con robot bola
                    tiny.setBackgroundResource(R.mipmap.ic_conectadoconbolaok);
//                    tiny03.setVisibility(View.VISIBLE);
//                    tiny04.setVisibility(View.VISIBLE);
                    //solo se muestra Iniciar
                    tiny04.setVisibility(View.VISIBLE);
                    ///

//                    conectado=1;
                    grabando=1;
                    grabacionIniciada=0;
                    timestapInicioGrabacion=0;
                    grabacionTerminada=0;


                }else{ //desconecta con robot bola
                    tiny.setBackgroundResource(R.mipmap.ic_conectarconbola);
                    tiny03.setVisibility(View.INVISIBLE);
                    tiny04.setVisibility(View.INVISIBLE);
                    tiny05.setVisibility(View.INVISIBLE);
                    nombre.setVisibility(View.INVISIBLE);
                    conectado=0;
                    grabando=0;
                    grabacionIniciada=0;
                    timestapInicioGrabacion=(int) System.currentTimeMillis();//obtener la hora de cuadno iniciamos a grabar
                    grabacionTerminada=0;

                }


//                android:visibility="invisible"

            }
        });
/////////////////////////////////////botones de control ///////////////////////////////////////////////////77


        Button btnControlUp=(Button)  findViewById(R.id.botonUp);
        btnControlUp.setVisibility(View.INVISIBLE);// que no se vea de inicio
        btnControlUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Up:");
                if(conectado==1) {//hay conexion con el  con robot bola
                    //manda comando a Robot Bola
                    //START: send comando to arduino
                    System.out.println("turn UP Command 1");
                    if (btSocket!=null)
                    {
                        System.out.println("turn UP Command 2");
                        try
                        {
                            btSocket.getOutputStream().write("F".toString().getBytes());
                            System.out.println("turn UP Command 3");
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                        System.out.println("turn UP Command 4");
                    }
                    System.out.println("turn UP Command 5");
                    //END: send comando to arduino

                    //START: graba el comando mandado al robot
                    if(grabacionIniciada==1 && grabacionTerminada==0){
                        System.out.println("Up:graba");
                        int tiempeDesdeUltimoComando= ((int) System.currentTimeMillis()) ;// hora actual a ultimocomando
                        int tiempo=tiempeDesdeUltimoComando -timestapInicioGrabacion;//restar hora actual a ultimocomando

                        PasosRutina pasitoNuevo= new PasosRutina(secuenciaSig+"",tiempo+"","UP");
                        nuevaLista.add(pasitoNuevo);
                        secuenciaSig++;
                    }
                    //END: graba el comando mandado al robot
                }else{ //desconectado del robot bola
                    //no hacer nada
                }

            }
        });


        Button btnControlDown=(Button)  findViewById(R.id.botonDown);
        btnControlDown.setVisibility(View.INVISIBLE);// que no se vea de inicio
        btnControlDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Down:");
                if(conectado==1) {//hay conexion con el  con robot bola

                    //manda comando a Robot Bola
                    //START: send comando DOWN to arduino
                    System.out.println("turn DOWN Command 1");
                    if (btSocket!=null)
                    {
                        System.out.println("turn DOWN Command 2");
                        try
                        {
                            btSocket.getOutputStream().write("B".toString().getBytes());
                            System.out.println("turn DOWN Command 3");
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                        System.out.println("turn DOWN Command 4");
                    }
                    System.out.println("turn DOWN Command 5");
                    //END: send comando to arduino
                    //START: graba el comando mandado al robot
                    if(grabacionIniciada==1 && grabacionTerminada==0){
                        System.out.println("Down:graba");
                        int tiempeDesdeUltimoComando= ((int) System.currentTimeMillis()) ;// hora actual a ultimocomando
                        int tiempo=tiempeDesdeUltimoComando -timestapInicioGrabacion;//restar hora actual a ultimocomando

                        PasosRutina pasitoNuevo= new PasosRutina(secuenciaSig+"",tiempo+"","DOWN");
                        nuevaLista.add(pasitoNuevo);
                        secuenciaSig++;
                    }
                    //END: graba el comando mandado al robot
                }else{ //desconectado del robot bola
                    //no hacer nada
                }

            }
        });


        Button btnControlRigth=(Button)  findViewById(R.id.botonRight);
        btnControlRigth.setVisibility(View.INVISIBLE);//que no se vena de inicio
        btnControlRigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Right:");
                if(conectado==1) {//hay conexion con el  con robot bola
                    //manda comando a Robot Bola
                    //START: send comando RIGHT to arduino
                    System.out.println("turn RIGHT Command 1");
                    if (btSocket!=null)
                    {
                        System.out.println("turn RIGHT Command 2");
                        try
                        {
                            btSocket.getOutputStream().write("R".toString().getBytes()); //check this while testing
                            System.out.println("turn RIGHT Command 3");
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                        System.out.println("turn RIGHT Command 4");
                    }
                    System.out.println("turn RIGHT Command 5");
                    //END: send comando to arduino
                    if(grabacionIniciada==1 && grabacionTerminada==0){
                        System.out.println("Right:graba");
                        int tiempeDesdeUltimoComando= ((int) System.currentTimeMillis()) ;// hora actual a ultimocomando
                        int tiempo=tiempeDesdeUltimoComando -timestapInicioGrabacion;//restar hora actual a ultimocomando

                        PasosRutina pasitoNuevo= new PasosRutina(secuenciaSig+"",tiempo+"","RIGHT");
                        nuevaLista.add(pasitoNuevo);
                        secuenciaSig++;
                    }
                }else{ //desconectado del robot bola
                    //no hacer nada
                }

            }
        });


        Button btnControlLeft=(Button)  findViewById(R.id.botonLeft);
        btnControlLeft.setVisibility(View.INVISIBLE);// que no se vea de inicio
        btnControlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Left:");
                if(conectado==1) {//hay conexion con el  con robot bola
                    //manda comando a Robot Bola
                    //START: send comando LEFT to arduino
                    System.out.println("turn LEFT Command 1");
                    if (btSocket!=null)
                    {
                        System.out.println("turn LEFT Command 2");
                        try
                        {
                            btSocket.getOutputStream().write("L".toString().getBytes());//check this while testing
                            System.out.println("turn LEFT Command 3");
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                        System.out.println("turn LEFT Command 4");
                    }
                    System.out.println("turn LEFT Command 5");
                    //END: send comando to arduino

                    if(grabacionIniciada==1 && grabacionTerminada==0){
                        System.out.println("Left:graba");
                        int tiempeDesdeUltimoComando= ((int) System.currentTimeMillis()) ;// hora actual a ultimocomando
                        int tiempo=tiempeDesdeUltimoComando -timestapInicioGrabacion;//restar hora actual a ultimocomando
                        PasosRutina pasitoNuevo= new PasosRutina(secuenciaSig+"",tiempo+"","LEFT");
                        nuevaLista.add(pasitoNuevo);
                        secuenciaSig++;
                    }
                }else{ //desconectado del robot bola
                    //no hacer nada
                }

            }
        });


        ////////////////////////////////////////publicar ////////////////////////////////////


        Button btnSaveTaskLocal03=(Button)  findViewById(R.id.Publicar);
        btnSaveTaskLocal03.setVisibility(View.INVISIBLE);

        btnSaveTaskLocal03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("estar seria la lista");
                EditText nombreRutinario= (EditText) findViewById(R.id.nombreRutina);

                Button tiny = (Button)v.findViewById(R.id.Publicar);
                if(conectado==1) {//conecta con robot bola
                    if(grabacionTerminada==1){
                        System.out.println("va:"+nuevaLista.toString());
                    }
                    String listasecuencia="Inicio";
                    String listacomandos="Inicio";
                    String listatiempos="Inicio";
                    for (PasosRutina elemento:nuevaLista
                            ) {
                        // System.out.println("elemento:"+elemento.toString() );
                        listasecuencia= listasecuencia+","+elemento.getSecuencia();
                        listacomandos= listacomandos+","+elemento.getComando();
                        listatiempos= listatiempos+","+elemento.getTiempoEsperaEnSegundos();
                        System.out.println("elemento:"+elemento.getSecuencia()+"  "
                                +elemento.getComando()+"  "
                                +elemento.getTiempoEsperaEnSegundos() );
                    }

                    System.out.println("goToAddEdificio - 2");
                    RequestTaskAddNuevaRutinaWeb th=new RequestTaskAddNuevaRutinaWeb();
                    System.out.println("goToAddEdificio - 3");
//                    th.nombreRutina="saltitos";
                    th.nombreRutina= nombreRutinario.getText().toString();
                    //th.secuencia="1,2,3,4";

                    th.secuencia=listasecuencia;
                    System.out.println("goToAddEdificio - 4");
                    //th.comando="UP,DOWN,RIGTH,RIGTH";
                    th.comando=listacomandos;
                    System.out.println("goToAddEdificio - 5");
                    //th.tiempo="2344,32435,35345,345345";
                    th.tiempo=listatiempos;

                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAddEdificio - 4");

                }else{ //desconecta con robot bola

                }

            }
        });


/////////////////////////////////////botones/funcionalidad de connecting BT ///////////////////////////////////////////////////77
        btnPaired = (Button)findViewById(R.id.btnBuscarDispBlue02);



        devicelist = (ListView)findViewById(R.id.lst_dispositivos02);

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

    }
}
