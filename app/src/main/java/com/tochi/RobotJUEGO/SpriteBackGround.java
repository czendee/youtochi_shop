package com.tochi.RobotJUEGO;

/**
 * Created by 813743 on 05/03/2018.
 */
import java.util.Random;

import android.graphics.Bitmap;

import android.graphics.Canvas;

import android.graphics.Rect;



public class SpriteBackGround {

    private Bitmap bmp;

    private int x = 0;

    private int y = 0;

    private GameView gameView;

    private int width;

    private int height;



    public SpriteBackGround(GameView gameView, Bitmap bmp) {

        this.width = bmp.getWidth() ;

        this.height = bmp.getHeight() ;

        this.gameView = gameView;

        this.bmp = bmp;


    }







    public void draw(Canvas canvas) {



        int srcX =  this.width;

        int srcY =  this.height;


        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        Rect dst = new Rect(x, y, x + width, y + height);


        canvas.drawBitmap(this.bmp, src, dst, null);

    }








}
