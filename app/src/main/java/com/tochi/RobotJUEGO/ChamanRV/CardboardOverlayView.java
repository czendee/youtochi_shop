package com.tochi.RobotJUEGO.ChamanRV;

/* 
  * Copyright 2014 Google Inc. All Rights Reserved. 
  
  * Licensed under the Apache License, Version 2.0 (the "License"); 
  * you may not use this file except in compliance with the License. 
  * You may obtain a copy of the License at 
  * 
  *     http://www.apache.org/licenses/LICENSE-2.0 
  * 
  * Unless required by applicable law or agreed to in writing, software 
  * distributed under the License is distributed on an "AS IS" BASIS, 
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
  * See the License for the specific language governing permissions and 
  * limitations under the License. 
  */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tochi.RobotJUEGO.R;


/**
  * Contains two sub-views to provide a simple stereo HUD. 
  */ 
 public class CardboardOverlayView extends LinearLayout {
     private static final String TAG = CardboardOverlayView.class.getSimpleName();
     private final CardboardOverlayEyeView mLeftView; 
     private final CardboardOverlayEyeView mRightView; 
     private AlphaAnimation mTextFadeAnimation;
 
 
     public CardboardOverlayView(Context context, AttributeSet attrs) {
         super(context, attrs); 
         setOrientation(HORIZONTAL); 
 
 
         LayoutParams params = new LayoutParams(
             LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
         params.setMargins(0, 0, 0, 0); 
 
 
         mLeftView = new CardboardOverlayEyeView(context, attrs); 
         mLeftView.setLayoutParams(params); 
         addView(mLeftView); 
 
 
         mRightView = new CardboardOverlayEyeView(context, attrs); 
         mRightView.setLayoutParams(params); 
         addView(mRightView); 
 
 
         // Set some reasonable defaults. 
//        setDepthOffset(0.016f); 
         setDepthOffset(0.20f);
         
         setColor(Color.rgb(150, 255, 180));
         setVisibility(View.VISIBLE);
 
 
         mTextFadeAnimation = new AlphaAnimation(1.0f, 0.0f);
         mTextFadeAnimation.setDuration(10000); 
     } 
 
 
     public void show3DToast(String message) {
         setText(message); 
         setTextAlpha(1f); 
         mTextFadeAnimation.setAnimationListener(new EndAnimationListener() { 
             @Override
             public void onAnimationEnd(Animation animation) {
                 setTextAlpha(0f); 
             } 
         }); 
         startAnimation(mTextFadeAnimation); 
//         setIcon(); may 4 2016 CZ , para evitar se despliegue encima de la imagen
         setLine(); 
     } 
     
     public void showIcon() { 
         setIcon(); 
         mTextFadeAnimation.setAnimationListener(new EndAnimationListener() { 
             @Override
             public void onAnimationEnd(Animation animation) {
                 setTextAlpha(0f); 
             } 
         }); 
         startAnimation(mTextFadeAnimation); 
     }
/**
 * new method to receive the icon we want to display (arror up,down,left, right)
 * @param iconName string
 */

    public void showIcon(String iconName) {
        System.out.println("showIcon - 1:"+iconName);
        setIcon(iconName);
        mTextFadeAnimation.setAnimationListener(new EndAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                setTextAlpha(0f);
            }
        });
        startAnimation(mTextFadeAnimation);
    }



//     public void showImage(int mScore, Context context) {
//     setImg(mScore, context);    
    /**
     *  start: may 4 2016 CZ
     * display imagen
     * @param mScore
     */
         public void showImage(int mScore) {
             setImg(mScore);        	 
     }

    /**
     *  start: may 4 2016 CZ
     * display imagen
     * @param mScore
     */
    public void showImageTeledirigido(int mScore) {
        setImgTeledirigido(mScore);
    }
     // end: may 4 2016 CZ
     // display imagen  
     
     private abstract class EndAnimationListener implements Animation.AnimationListener {
         @Override
         public void onAnimationRepeat(Animation animation) {}
         @Override
         public void onAnimationStart(Animation animation) {}
     } 
 
 
     private void setDepthOffset(float offset) { 
         mLeftView.setOffset(offset); 
         mRightView.setOffset(-offset); 
     } 
 
 
     private void setText(String text) {
         mLeftView.setText(text); 
         mRightView.setText(text); 
     } 
 
 
     private void setTextAlpha(float alpha) { 
         mLeftView.setTextViewAlpha(alpha); 
         mRightView.setTextViewAlpha(alpha); 
     } 
 
 
     private void setColor(int color) { 
         mLeftView.setColor(color); 
         mRightView.setColor(color); 
     } 
 
     
     private void setIcon() {
         System.out.println("setIcon - 1:sin nombre");
         mLeftView.setIcon(); 
         mRightView.setIcon(); 
     }
/**
 * new method to display the icon name received in the left and rigth view (arror up,down,left, rigt)
 * @param nameFileIcon string
 *   @author 813743 CZ
 */

    private void setIcon(String nameFileIcon) {
        System.out.println("setIcon - 1:"+nameFileIcon);
        mLeftView.setIcon(nameFileIcon);
        System.out.println("setIcon - 2:despues izquierdo");
        mRightView.setIcon(nameFileIcon);
        System.out.println("setIcon - 3:despues derecho");
    }


    private void setLine() {
         mLeftView.setLine(); 
         mRightView.setLine(); 
     } 
     
     // start: may 4 2016 CZ
     // set imagen 

//     private void setImg(int mScore, Context context) {
     private void setImg(int mScore) {




         switch (mScore) {

         case 0:

                mLeftView.imageView.setLayoutParams(new LayoutParams(

                             LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                mLeftView.imageView.setBackgroundResource(R.mipmap.ic_launcher);

                mRightView.imageView.setLayoutParams(new LayoutParams(

                             LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                mRightView.imageView.setBackgroundResource(R.mipmap.ic_launcher);

                break;
         case 1:

             mLeftView.imageView.setLayoutParams(new LayoutParams(

                          LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

//             mLeftView.imageView.setBackgroundResource(R.mipmap.personaje32hectoriz ed);
             mLeftView.imageView.setBackgroundResource(R.mipmap.youtochismall);

             mRightView.imageView.setLayoutParams(new LayoutParams(

                          LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

//             mRightView.imageView.setBackgroundResource(R.mipmap.personaje32hectorized);
             mRightView.imageView.setBackgroundResource(R.mipmap.youtochismall);


             break;

         default:

//                Intent intent = new Intent(context, VrMagenetActivity.class);

//                context.startActivity(intent);

         }

  }
     // start: may 4 2016 CZ
     // set imagen

    /**
     * set image indicating the image for control teledirigido
     * @param mScore
     */



    private void setImgTeledirigido(int mScore) {




        switch (mScore) {

            case 0:

                mLeftView.imageView.setLayoutParams(new LayoutParams(

                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                mLeftView.imageView.setBackgroundResource(R.mipmap.ic_launcher);

                mRightView.imageView.setLayoutParams(new LayoutParams(

                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                mRightView.imageView.setBackgroundResource(R.mipmap.ic_launcher);

                break;
            case 1:

                mLeftView.imageView.setLayoutParams(new LayoutParams(

                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//cz
//  iamen de boton a la izq de inicio
                mLeftView.imageView.setBackgroundResource(R.mipmap.ic_botonflecharight);

                mRightView.imageView.setLayoutParams(new LayoutParams(

                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

//  iamen de boton a la izq de inicio
                //cz
                mRightView.imageView.setBackgroundResource(R.mipmap.ic_botonflecharight);

                break;

            default:

//                Intent intent = new Intent(context, VrMagenetActivity.class);

//                context.startActivity(intent);

        }

    }

     
     /** 
      * A simple view group containing some horizontally centered text underneath a horizontally 
      * centered image. 
      * 
      * This is a helper class for CardboardOverlayView. 
      */ 
     private class CardboardOverlayEyeView extends ViewGroup {
         private final ImageView imageView;
         private final TextView textView;
         private final ImageView iconView;
         private final DrawView lineView; 
         private float offset; 
 
 
         public CardboardOverlayEyeView(Context context, AttributeSet attrs) {
             super(context, attrs); 
             imageView = new ImageView(context, attrs);
             imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

             imageView.setAdjustViewBounds(true);  // Preserve aspect ratio. 
             
             addView(imageView); 
 

 
             textView = new TextView(context, attrs);
             textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0f);
             textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
             textView.setGravity(Gravity.CENTER);
             textView.setShadowLayer(3.0f, 0.0f, 0.0f, Color.DKGRAY);
             addView(textView); 
             
             iconView = new ImageView(context, attrs);
             iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
             iconView.setAdjustViewBounds(true);  // Preserve aspect ratio. 
             addView(iconView); 
             
             lineView = new DrawView(context); 
             addView(lineView);             
         } 
 
 
         public void setColor(int color) { 
             imageView.setColorFilter(color); 
             textView.setTextColor(color);
             iconView.setColorFilter(color);
         } 
 
 
         public void setText(String text) {
             textView.setText(text); 
         } 
 
 
         public void setTextViewAlpha(float alpha) { 
             textView.setAlpha(alpha); 
         } 
 
 
         public void setOffset(float offset) { 
             this.offset = offset; 
         } 
 
         public void setIcon() { 
             iconView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher) );


         }
         /**
          * new method to display the icon name received in the left and rigth view (arror up,down,left, rigt)
          * @param nameIcon string
          * @author 813743
          */
         public void setIcon(String nameIcon) {
             System.out.println("CardboardOverlayEyeView setIcon - 1:"+nameIcon);
             int imagenInt=(int)R.mipmap.ic_launcher;

             if(nameIcon!=null){
                 System.out.println("CardboardOverlayEyeView setIcon - 2:");
                 if(nameIcon.equals("up")){
                     System.out.println("CardboardOverlayEyeView setIcon - 3:");
                     imagenInt=R.mipmap.ic_botonflechaup;
                 }else  if(nameIcon.equals("down")){
                     System.out.println("CardboardOverlayEyeView setIcon - 4:");
                     imagenInt=R.mipmap.ic_botonflechadown;
                 }else if(nameIcon.equals("left")){
                     System.out.println("CardboardOverlayEyeView setIcon - 5:");
                     imagenInt=R.mipmap.ic_botonflechaleft;
                 }else if(nameIcon.equals("right")){
                     System.out.println("CardboardOverlayEyeView setIcon - 6:");
                     imagenInt=R.mipmap.ic_botonflecharight;
                 }else{
                     System.out.println("CardboardOverlayEyeView setIcon - 7:");
                     imagenInt=R.mipmap.ic_conectarconbola;

                 }
             }

//             iconView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher) );
             iconView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), imagenInt) );


         }
         public void setLine() { 
             //


         } 
         
         @Override
         protected void onLayout(boolean changed, int left, int top, int right, int bottom) { 
             // Width and height of this ViewGroup. 
             final int width = right - left; 
             final int height = bottom - top; 
 
 
             // The size of the image, given as a fraction of the dimension as a ViewGroup. We multiply 
             // both width and heading with this number to compute the image's bounding box. Inside the 
             // box, the image is the horizontally and vertically centered. 
//cz may 4 2016             final float imageSize = 0.12f; 
             
             final float imageSize = 0.15f;
 
 
             // The fraction of this ViewGroup's height by which we shift the image off the ViewGroup's 
             // center. Positive values shift downwards, negative values shift upwards. 
//             final float verticalImageOffset = -0.07f; 
//             final float verticalImageOffset = -0.35f; //mas arriba

             final float verticalImageOffset = 0.4f; //mas arriba
 
 
             // Vertical position of the text, specified in fractions of this ViewGroup's height. 
             final float verticalTextPos = 0.52f; 
 
             // Layout ImageView 
             float imageMargin = (1.0f - imageSize) / 2.0f; 
             float leftMargin = (int) (width * (imageMargin + offset)); 
             float topMargin = (int) (height * (imageMargin + verticalImageOffset)); 
             imageView.layout( 
                 (int) leftMargin, (int) topMargin, 
                 (int) (leftMargin + width * imageSize), (int) (topMargin + height * imageSize)); 
 
 
             // Layout TextView 
             leftMargin = offset * width; 
             topMargin = height * verticalTextPos; 
             textView.layout( 
                 (int) leftMargin, (int) topMargin, 
                 (int) (leftMargin + width), (int) (topMargin + height * (1.0f - verticalTextPos)));
             
             //ICON
             // The size of the icon, given as a fraction of the dimension as a ViewGroup. We multiply 
             // both width and heading with this number to compute the image's bounding box. Inside the 
             // box, the image is the horizontally and vertically centered. 
             final float iconSize = 0.10f; 
 
 
             // The fraction of this ViewGroup's height by which we shift the image off the ViewGroup's 
             // center. Positive values shift downwards, negative values shift upwards. 
             final float verticalIconOffset = 0.55f; 
  
             // Layout IconView 
             float iconMargin = (1.0f - iconSize) / 2.0f; 
             float leftMarginIcon = (int) (width * (iconMargin + offset)); 
             float topMarginIcon = (int) (height * (iconMargin + verticalIconOffset)); 
             iconView.layout( 
                 (int) leftMarginIcon, (int) topMargin, 
                 (int) (leftMarginIcon + width * iconSize), (int) (topMarginIcon + height * iconSize));
             
             //Line
             // The size of the icon, given as a fraction of the dimension as a ViewGroup. We multiply 
             // both width and heading with this number to compute the image's bounding box. Inside the 
             // box, the image is the horizontally and vertically centered. 
             final float lineSize = 0.20f; 
 
 
             // The fraction of this ViewGroup's height by which we shift the image off the ViewGroup's 
             // center. Positive values shift downwards, negative values shift upwards. 
             final float verticalLineOffset = 0.65f; 
  
             // Layout IconView 
             float lineMargin = (1.0f - lineSize) / 2.0f; 
             float leftMarginLine = (int) (width * (lineMargin + offset)); 
             float topMarginLine = (int) (height * (lineMargin + verticalLineOffset)); 
             lineView.layout( 
                 (int) leftMarginLine, (int) topMargin, 
                 (int) (leftMarginLine + width * lineSize), (int) (topMarginLine + height * lineSize));
             
         } 
     } 
 } 
