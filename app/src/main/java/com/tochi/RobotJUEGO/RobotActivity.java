package com.tochi.RobotJUEGO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class RobotActivity extends Activity {

    // properties to handle the contol buttons, once the device is connected
    // START:

    public int conectado=0;

    public int grabando=0;


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
    Button btnRobotsEnlazados;

    ListView devicelist;

    ListView devicelistRobotsEnlazados;

    Button btnOn, btnOff, btnDis;

    String address = null;

    BluetoothSocket btSocket = null;

    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //END


    List BTbtSocket =new ArrayList();
    List BTisBtConnected =new ArrayList();


    List BTrobotsDisponiblesAddress =new ArrayList();
    List BTrobotsDisponiblesName =new ArrayList();

    List BTposicionRobotDisponibles =new ArrayList();

    ////////////////////////

    final ArrayList<String> arrayTasks = new ArrayList<>();//store the list of robots from the web API



    final ArrayList<DataDescriptorRobot> dRobot = new ArrayList<>();//store the list of map markers robots,with info from the web API

    /////////////////////////

    GameView  miGameView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_robot);
        miGameView=new GameView(this);
        setContentView(miGameView);



    }



}
