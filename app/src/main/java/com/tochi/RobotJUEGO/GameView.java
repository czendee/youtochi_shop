package com.tochi.RobotJUEGO;

/**
 * Created by 813743 on 05/03/2018.
 */


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.Canvas;

import android.graphics.Color;

import android.os.AsyncTask;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import android.view.SurfaceView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GameView extends SurfaceView {

    private SurfaceHolder holder;

    private GameLoopThread gameLoopThread;

    private List<Sprite> sprites = new ArrayList<Sprite>();

    private SpriteBackGround mySpriteBackGround;

    private long lastClick;

    private Bitmap bmp2;

    private Bitmap bmp3;

    private Bitmap bmp4;

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    private Context mContext;//to start activity from this surfaceView

    ////////////////////////

    final ArrayList<String> arrayTasks = new ArrayList<>();//store the list of robots from the web API



    final ArrayList<DataDescriptorRobot> dRobot = new ArrayList<>();//store the list of map markers robots,with info from the web API

    /////////////////////////

    public GameView(Context context) {

        super(context);

        ////////////////////////////////////////////////////////////////////////////////////////7
        //llmar el que traiga la lista de robots y su info del API
        System.out.println("ListRobots paso 1");
        AsyncListViewLoader task=new AsyncListViewLoader();
        System.out.println("ListRobots paso 2");
        Constants constantes= new Constants();
//        task.parametroURL="http://mexico.brinkster.net/tochi_get_lista_edificios_mobil.asp?operacion=lista";
        task.parametroURL=constantes.URL_LIST_ROBOTS;

        System.out.println("ListRobots paso 3");
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)

            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();


        System.out.println("ListRobots paso 4");
////////////////////////////////////////////////////////////////////////////////////////7


        gameLoopThread = new GameLoopThread(this);

        this.mContext = context;//store in the private, the Context passed

        holder = getHolder();



        holder.addCallback(new SurfaceHolder.Callback() {



            @Override

            public void surfaceDestroyed(SurfaceHolder holder) {

                boolean retry = true;

                gameLoopThread.setRunning(false);

                while (retry) {

                    try {

                        gameLoopThread.join();

                        retry = false;

                    } catch (InterruptedException e) {

                    }

                }

            }



            @Override

            public void surfaceCreated(SurfaceHolder holder) {

                mySpriteBackGround= createSpriteBackground(R.mipmap.back);
                createSprites();


                gameLoopThread.setRunning(true);

                gameLoopThread.start();

            }



            @Override

            public void surfaceChanged(SurfaceHolder holder, int format,

                                       int width, int height) {

            }

        });


        bmp2 = BitmapFactory.decodeResource(getResources(), R.mipmap.back);

        bmp3 = BitmapFactory.decodeResource(getResources(), R.mipmap.sky);

        bmp4 = BitmapFactory.decodeResource(getResources(), R.mipmap.ideagamegrande);



    }



    private void createSprites() {

        sprites.add(createSprite(R.mipmap.invagent,false,0));

        sprites.add(createSprite(R.mipmap.invagent2,false,1));
        sprites.add(createSprite(R.mipmap.invagent3,false,2));
//estos seran los sprites quietos

        sprites.add(createSprite(R.mipmap.ic_botonflechadown,true,3));
        sprites.add(createSprite(R.mipmap.ic_botonflechaleft,true,4));
        sprites.add(createSprite(R.mipmap.ic_botonflecharight,true,5));
    }



    private Sprite createSprite(int resouce, boolean quieto,int cualsoy) {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        Sprite temp = new Sprite(this,bmp);
        if(quieto){
            temp.setX(100*cualsoy);
            temp.setY(50);
            temp.setHeight(250);
            temp.setWidth(250);
        }
        return temp;
//        return new Sprite(this,bmp);

    }

    private SpriteBackGround createSpriteBackground(int resouce) {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);

        return new SpriteBackGround(this,bmp);

    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

        canvas.drawBitmap(bmp4, 10, 10, null);

        canvas.drawBitmap(bmp2, 30, 30, null);

        canvas.drawBitmap(bmp3, 70, 40, null);

        int quietos=5;
        int cuantos=0;

        for (Sprite sprite : sprites) {
            if (cuantos < quietos){

                sprite.draw(canvas, true);//true que se mueva
            }else{
                sprite.draw(canvas, false);//false que no se mueva
            }
            cuantos++;


        }

        mySpriteBackGround.draw(canvas);


    }
    @Override

    public boolean onTouchEvent(MotionEvent event) {

        if (System.currentTimeMillis() - lastClick > 500) {

            lastClick = System.currentTimeMillis();

            synchronized (getHolder()) {

                for (int i = sprites.size() - 1; i >= 0; i--) {
                    System.out.println("sprite :"+i);
                    Sprite sprite = sprites.get(i);

                    if (i==1 //do  for the sprite 1
                            && sprite.isCollition(event.getX(), event.getY())) { //coincidan x y con el sprite
                        System.out.println("sprite remove:"+i);
                        sprites.remove(sprite);
                        Intent intent = new Intent(mContext, RobotRemotosActivity.class);
                        mContext.startActivity(intent);

                        break;

                    }
                    if (i==2 //do  for the sprite 2
                            && sprite.isCollition(event.getX(), event.getY())) { //coincidan x y con el sprite
                        System.out.println("sprite remove:"+i);
                        sprites.remove(sprite);
                        Intent intent = new Intent(mContext, PlaylistActivity.class);
                        mContext.startActivity(intent);

                        break;

                    }
                    if(i>2 && sprite.isCollition(event.getX(), event.getY())){
                        System.out.println("sprite 1");
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                System.out.println("boton abajo 01");

                                System.out.println("boton abajo 01x:"+sprite.getX());
                                System.out.println("boton abajo 01y:"+sprite.getY());

                                //remember the initial position.
                                initialX=sprite.getX();
                                initialY= sprite.getY();

                                System.out.println("boton abajo 02");
                                //get the touch location
                                initialTouchX= event.getRawX();
                                initialTouchY = event.getRawY();
                                System.out.println("boton abajo 03");
                                return true;
                            case MotionEvent.ACTION_MOVE:
                                System.out.println("boton abajo 04");
                                //Calculate the X and Y coordinates of the view.
                                sprite.setX(initialX + (int) (event.getRawX() - initialTouchX)) ;
                                sprite.setY(initialY + (int) (event.getRawY() - initialTouchY));
                                System.out.println("boton abajo 05");

                        }//end switch
                    }//if
                }//for

            }

        }

        return true;

    }

    private void addOtrosSprites() {
        System.out.println("addOtrosSprites 7");
        sprites.add(createSprite(R.mipmap.ic_conectadoconbolaok,true,6));
        sprites.add(createSprite(R.mipmap.ic_conectarconbola,true,7));
    }

    /*
Custom method, to be used when the list of markers are read from the web


 */
    public void updateMap() {

        System.out.println("updateMap 7");
        addOtrosSprites();

/*
        holder.addCallback(new SurfaceHolder.Callback() {



            @Override

            public void surfaceDestroyed(SurfaceHolder holder) {

                boolean retry = true;

                gameLoopThread.setRunning(false);

                while (retry) {

                    try {

                        gameLoopThread.join();

                        retry = false;

                    } catch (InterruptedException e) {

                    }

                }

            }



            @Override

            public void surfaceCreated(SurfaceHolder holder) {

                mySpriteBackGround= createSpriteBackground(R.mipmap.back);
                createSprites();


                gameLoopThread.setRunning(true);

                gameLoopThread.start();

            }



            @Override

            public void surfaceChanged(SurfaceHolder holder, int format,

                                       int width, int height) {

            }

        });


        bmp2 = BitmapFactory.decodeResource(getResources(), R.mipmap.back);

        bmp3 = BitmapFactory.decodeResource(getResources(), R.mipmap.sky);

        bmp4 = BitmapFactory.decodeResource(getResources(), R.mipmap.ideagamegrande);
*/

/*

        System.out.println("robot 4 de web 1" );
        int primero=0;
        Marker localMarker=null;
        LatLng currentRobotPosicion = null;

        int icual=0;

        for( String actualElemento: arrayTasks)

        {
            System.out.println("robot 4 de web 2 "+actualElemento );
            double ivalorLat;
            double ivalorLon;
            try{
                String valorLat= dRobot.get(icual).getPosLat();
                String valorLon= dRobot.get(icual).getPosLon();
                ivalorLat = new Double(valorLat);
                ivalorLon = new Double(valorLon);

                System.out.println("robot 4 de web 2.0  "+ ivalorLat +"  "+ ivalorLon );
                currentRobotPosicion = new LatLng(ivalorLat, ivalorLon);
                mRobot04= mMap.addMarker(
//    1st                    new MarkerOptions().position(currentRobotPosicion).title(actualElemento).snippet("SII Renta por:(10mins) Renta $:(Gratis) Manada:(Si) Transmite Periscope:(Si)")
//    2nd                        new MarkerOptions().position(currentRobotPosicion).title(actualElemento).snippet("SII Renta por:("+dRobot.get(icual).getRentaTiempo()+") Renta $:("+dRobot.get(icual).getRentaCosto()+") Manada:("+dRobot.get(icual).getManada()+") Transmite:("+dRobot.get(icual).getTransmite()+")")
                        new MarkerOptions().position(currentRobotPosicion).title(dRobot.get(icual).getName()).snippet("[:)   Renta por:("+dRobot.get(icual).getRentaTiempo()+") Renta $:("+dRobot.get(icual).getRentaCosto()+") Manada:("+dRobot.get(icual).getManada()+") Transmite:("+dRobot.get(icual).getTransmite()+")")
                );
                System.out.println("robot 4 de web 2.1" );
                mRobot04.setIcon(bmd);
                localMarker=mRobot04;
            }catch (Exception e){
                ivalorLat = new Double(0.0);
                ivalorLon = new Double(0.0);

            }

            //arrayMarkers

            mRobot.add(localMarker); //agregar aqui los markers en esta lista
            localMarker=null;
            icual++;
            System.out.println("robot 4 de web 3" );
        }//end for
        System.out.println("robot 4 de web 4" );

        System.out.println("zooooom original" +mMap.getMaxZoomLevel());
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexicoCity,mMap.getMaxZoomLevel()-14));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexicoCity,mMap.getMaxZoomLevel()-4));
        System.out.println("zooooom menos" );
        mMap.setBuildingsEnabled(true);





        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.429, -98.146))
                .title("San Francisco")
                .snippet("Population: 776733"))
                .setIcon(bmd);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);

        */
    }
    //-------------------------------------------------Tarea asyncrona que lee datos de la web --------------------

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<DataDescriptorRobot>> {
//        private final ProgressDialog dialog = new ProgressDialog(RobotActivity.this); //asi porque estamos dentro de un fragment

        public String parametroURL="aqui";

        @Override
        protected void onPostExecute(List<DataDescriptorRobot> result) {
            super.onPostExecute(result);
//            dialog.dismiss();
            System.out.println("AsyncListViewLoader onPostExecute paso 1");

            //cylce through List DataDescriptorRobot result, y ponerlo en lista de ArrayTask
            if(result!=null) {
                for (DataDescriptorRobot f : result) {
//                    arrayTasks.add(f.getName().toUpperCase() + " - " + f.getTipo().toUpperCase() + " - " + f.getManada().toUpperCase() + "-"+ f.getPosLon().toUpperCase() + "-"+ f.getPosLat().toUpperCase() + "-");// origianl
                    arrayTasks.add(f.getName() + " - " + f.getTipo() + " - " + f.getManada() + "-" + f.getPosLon() + "-" + f.getPosLat() + "-");// api robots

                    dRobot.add(f); //aqui almacenamos cada dataDescriptor de robot, asi esta lista tendra los datos del robot web
                }
                // adapter.notifyDataSetChanged();//no hay un objeto en la screen que estara siendo modificado
                //quiza se repinten los markers
                System.out.println("AsyncListViewLoader onPostExecute paso 2");
                updateMap();
                System.out.println("AsyncListViewLoader onPostExecute paso 3");
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog.setMessage("Downloading contacts...");
//            dialog.show();
        }
        @Override
        protected List<DataDescriptorRobot> doInBackground(String... params) {

            System.out.println("PrimerFragment doInBackground paso 1");

            List<DataDescriptorRobot> result = new ArrayList<DataDescriptorRobot>();
            System.out.println("PrimerFragment doInBackground paso 2");
            try {

                BufferedReader inStream = null;
                System.out.println("ValidaRobot - 2"+parametroURL);
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

                System.out.println("PrimerFragmentdoInBackground paso 5");
//antes asp tenia { edificio [ {     }, {    } ] }
//antes asp                JSONObject jsonResponse = new JSONObject(new String(JSONResp));
//antes asp                JSONArray arr = jsonResponse.getJSONArray("edificio");


//ahora solo es [ {     }, {    } ]
                JSONArray arr = new JSONArray(new String(JSONResp));
                System.out.println("PrimerFragment doInBackground paso 6");

                for (int i=0; i < arr.length(); i++) {
                    result.add(convertDataDescriptorRobot(arr.getJSONObject(i)));
                }
                System.out.println("PrimerFragment doInBackground paso 7");
                return result;
            } catch(Throwable t) {
                t.printStackTrace();
            }
            System.out.println("PrimerFragment doInBackground paso 8");
            return null;
        }
        private DataDescriptorRobot convertDataDescriptorRobot(JSONObject obj) throws JSONException {

            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 10");

//            String id = obj.getString("id");
            String name = obj.getString("name");
            String tipo = obj.getString("tipo");
            String manada = obj.getString("manada");
            String posLon = obj.getString("lon");
            String posLat = obj.getString("lat");
            String rentaTiempo = obj.getString("rentatiempo");
            String rentaCosto = obj.getString("rentacosto");

            String transmite = obj.getString("transmite");
            String transmiteCanal=obj.getString("transmitecanal");
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 11:"+name);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 12:"+posLon);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 13:"+posLat);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 14:"+transmite);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 15:"+transmiteCanal);

            return new DataDescriptorRobot("01",name, tipo,manada,posLon,posLat,rentaTiempo,rentaCosto,transmite, transmiteCanal);
        }

    }//end private class
}
