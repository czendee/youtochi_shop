package com.tochi.RobotJUEGO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tochi.RobotJUEGO.ChamanRV.PubliperiscopioActivity;
import com.tochi.RobotJUEGO.ChamanRV.PubliteleperiscopioActivity;


public class PeriscopioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periscopio);




        Button btnRegresar=(Button)  findViewById( R.id.botonRegresar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Regresar - 3");
                finish();


            }
        });

        Button btnPublicarPeri=(Button)  findViewById( R.id.botonPublicarPeri);

        btnPublicarPeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("btnPublicarPeri - 1");
                Intent i = new Intent(PeriscopioActivity.this, PubliperiscopioActivity.class);
//                i.putExtra("cual_usuario",this.extraMessageUser+"");
//                System.out.println("Login activity passing,value:"+this.extraMessageUser);

                startActivity(i);

                System.out.println("btnPublicarPeri - 3");


            }
        });



        Button btnPublicarPeriInter=(Button)  findViewById( R.id.botonPublicarInterPeri);

        btnPublicarPeriInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("btnPublicarTelePeri - 1");
                Intent i = new Intent(PeriscopioActivity.this, PubliteleperiscopioActivity.class);
//                i.putExtra("cual_usuario",this.extraMessageUser+"");
//                System.out.println("Login activity passing,value:"+this.extraMessageUser);

                startActivity(i);

                System.out.println("btnPublicarTelePeri - 3");


            }
        });

    }
}
