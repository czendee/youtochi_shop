package com.tochi.RobotJUEGO;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;


public class InicioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


//        Button btnPeriscopio=(Button)  findViewById(R.id.botonPeriscopio);


/*        btnPeriscopio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(InicioActivity.this, PeriscopioActivity.class);
//                i.putExtra("cual_usuario",this.extraMessageUser+"");
//                System.out.println("Login activity passing,value:"+this.extraMessageUser);

                startActivity(i);

                System.out.println("onPostExecute - 3");

            }
        });
*/

        Button btnGrabar=(Button)  findViewById( R.id.botonGrabar);


        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(InicioActivity.this, GrabarActivity.class);
//                i.putExtra("cual_usuario",this.extraMessageUser+"");
//                System.out.println("Login activity passing,value:"+this.extraMessageUser);

                startActivity(i);



                System.out.println("onPostExecute - Grabar13");

            }
        });

        Button btnRobot=(Button)  findViewById( R.id.botonRobot);


        btnRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(InicioActivity.this, RobotActivity.class);
//                i.putExtra("cual_usuario",this.extraMessageUser+"");
//                System.out.println("Login activity passing,value:"+this.extraMessageUser);

                startActivity(i);



                System.out.println("onPostExecute - 3");

            }
        });
        Button btnPlayList=(Button)  findViewById( R.id.botonListaTareas);


        btnPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(InicioActivity.this, PlaylistActivity.class);
//                i.putExtra("cual_usuario",this.extraMessageUser+"");
//                System.out.println("Login activity passing,value:"+this.extraMessageUser);

                startActivity(i);



                System.out.println("onPostExecute - 3");

            }
        });

        Button btnRobotRemote=(Button)  findViewById( R.id.botonControlRemoto);


        btnRobotRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(InicioActivity.this, RobotRemotosActivity.class);
//                i.putExtra("cual_usuario",this.extraMessageUser+"");
//                System.out.println("Login activity passing,value:"+this.extraMessageUser);

                startActivity(i);



                System.out.println("onPostExecute - 3");

            }
        });

        Button btnRegresar=(Button)  findViewById( R.id.botonRegresarInicio);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Regresar - 3");
                finish();


            }
        });

    }
}
