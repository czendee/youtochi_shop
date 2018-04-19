package com.tochi.RobotJUEGO;


import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by martha on 09/10/2017.
 */
public class ControlesBotones extends Service implements View.OnTouchListener {

    Button mButtonIzq;
    Button mButtonArriba;
    Button mButtonAbajo;
    Button mButtonDerecho;
    ImageButton mImgButtonIzq;
    ImageButton mImgButtonArriba;
    ImageButton mImgButtonAbajo;
    ImageButton mImgButtonDerecho;

    //boton para cerrar: oculta los controles
    ImageButton mImgButtonEnds;

    WindowManager wm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
/*
        mButtonIzq = new Button(this);
        mButtonIzq.setText("<-");
        mButtonIzq.setTextColor(Color.BLACK);
        mButtonIzq.setOnTouchListener(this);
*/
        mImgButtonIzq = new ImageButton(this);
        mImgButtonIzq.setBackgroundResource(R.mipmap.ic_botonflecharight);
//        mImgButtonIzq.setOnTouchListener(this);
        mImgButtonIzq.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
                    Toast.makeText(getBaseContext(), "Izq", Toast.LENGTH_LONG).show();
//                    stopSelf();


                    System.out.println("goToAdd Comando RIGHT Robot - 2");
//old asp api /accessDB                   RequestTaskAddNuevaRutinaWeb th=new RequestTaskAddNuevaRutinaWeb();
// new nodejs api/mongodb
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando RIGHT Robot - 3");
                    th.nombreRutina="bolastino";
//                    th.nombreRutina= nombreRutinario.getText().toString();
                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando RIGHT Robot - 4");
                    th.comando="RIGTH";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando RIGHT Robot - 5");
                    th.tiempo="2344";
//                    th.tiempo=listatiempos;

//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd RIGHT ComandoRobot - 4");

                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });

        WindowManager.LayoutParams paramsIzq = new WindowManager.LayoutParams(150,
                150, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsIzq.gravity = Gravity.LEFT | Gravity.CENTER;


//boton de arriba
/*        mButtonArriba = new Button(this);
        mButtonArriba.setText("Arriba");
        mButtonArriba.setTextColor(Color.BLACK);
        mButtonArriba.setOnTouchListener(this);
*/
        mImgButtonArriba = new ImageButton(this);
        mImgButtonArriba.setBackgroundResource(R.mipmap.ic_botonflechaup);
//        mImgButtonArriba.setOnTouchListener(this);
        mImgButtonArriba.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
                    Toast.makeText(getBaseContext(), "Arriba", Toast.LENGTH_LONG).show();
//                    stopSelf(); cierra los controles botones

                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 2");
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 3");
                    th.nombreRutina="bolastino";
//                    th.nombreRutina= nombreRutinario.getText().toString();
                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 4");
                    th.comando="Arriba/Adelante";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 5");
                    th.tiempo="2344";
//                    th.tiempo=listatiempos;

//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd Arriba/Adelante ComandoRobot - 4");

                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });

        WindowManager.LayoutParams paramsTop = new WindowManager.LayoutParams(150,
                150, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsTop.gravity = Gravity.TOP | Gravity.CENTER;

//boton de abajo
        /*
        mButtonAbajo = new Button(this);
        mButtonAbajo.setText("Abajo");
        mButtonAbajo.setTextColor(Color.BLACK);
        mButtonAbajo.setOnTouchListener(this);
*/
        mImgButtonAbajo = new ImageButton(this);
        mImgButtonAbajo.setBackgroundResource(R.mipmap.ic_botonflechadown);
        //mImgButtonAbajo.setOnTouchListener(this);

        mImgButtonAbajo.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
                    Toast.makeText(getBaseContext(), "Abajo", Toast.LENGTH_LONG).show();
//                    stopSelf(); cierra controles botones
// new nodejs api/mongodb
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando Abajo/Atras Robot - 3");
                    th.nombreRutina="bolastino";
//                    th.nombreRutina= nombreRutinario.getText().toString();
                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando Abajo/Atras Robot - 4");
                    th.comando="Abajo/Atras";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando Abajo/Atras Robot - 5");
                    th.tiempo="2344";
//                    th.tiempo=listatiempos;

//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd Abajo/Atras ComandoRobot - 4");


                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });

        WindowManager.LayoutParams paramsAbajo = new WindowManager.LayoutParams(150,
                150, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsAbajo.gravity = Gravity.BOTTOM | Gravity.CENTER;

        //boton de derecho
        /*
        mButtonDerecho = new Button(this);
        mButtonDerecho.setText("->");
        mButtonDerecho.setTextColor(Color.BLACK);
        mButtonDerecho.setOnTouchListener(this);
     */
        mImgButtonDerecho = new ImageButton(this);
        mImgButtonDerecho.setBackgroundResource(R.mipmap.ic_botonflechaleft);
        // mImgButtonDerecho.setOnTouchListener(this);

        mImgButtonDerecho.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
                    Toast.makeText(getBaseContext(), "Derecha", Toast.LENGTH_LONG).show();
//                    stopSelf(); cierra controles botones

                    System.out.println("goToAdd Comando LEFT Robot - 2");
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando LEFT Robot - 3");
                    th.nombreRutina="bolastino";
//                    th.nombreRutina= nombreRutinario.getText().toString();
                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando LEFT Robot - 4");
                    th.comando="LEFT";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando LEFT Robot - 5");
                    th.tiempo="2344";
//                    th.tiempo=listatiempos;

//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd LEFT ComandoRobot - 4");
                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });
        WindowManager.LayoutParams paramsDerecho = new WindowManager.LayoutParams(150,
                150, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsDerecho.gravity = Gravity.RIGHT | Gravity.CENTER;

//bot0on para terminar/cerrar los controles de boton


        mImgButtonEnds = new ImageButton(this);
        mImgButtonEnds.setBackgroundResource(R.mipmap.stop_smallito);


        mImgButtonEnds.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
                    Toast.makeText(getBaseContext(), "Cerramos/Close -", Toast.LENGTH_LONG).show();

                    stopSelf();


                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });
        WindowManager.LayoutParams paramsCerrar = new WindowManager.LayoutParams(50,
                50, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsCerrar.gravity = Gravity.LEFT | Gravity.TOP;

//agregar los botonas al viw que flta en la pantalla

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mImgButtonIzq, paramsIzq);
        wm.addView(mImgButtonArriba, paramsTop);
        wm.addView(mImgButtonAbajo, paramsAbajo);
        wm.addView(mImgButtonDerecho, paramsDerecho);
        wm.addView(mImgButtonEnds, paramsCerrar);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(), "onDestroy", Toast.LENGTH_LONG).show();
        if (mImgButtonIzq != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonIzq);
            mImgButtonIzq = null;
        }
        if (mImgButtonArriba != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonArriba);
            mImgButtonArriba = null;
        }
        if (mImgButtonAbajo != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonAbajo);
            mImgButtonAbajo = null;
        }
        if (mImgButtonDerecho != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonDerecho);
            mImgButtonDerecho = null;
        }

        if (mImgButtonEnds != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonEnds);
            mImgButtonEnds = null;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d("OverlayButton onTouch", "touched the button");
            stopSelf();
        }
        return true;
    }
}

