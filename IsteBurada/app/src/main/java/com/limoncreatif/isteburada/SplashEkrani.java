package com.limoncreatif.isteburada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashEkrani extends AppCompatActivity {
    int gelen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ekrani);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.limoncreatif.isteburada", Context.MODE_PRIVATE);
        gelen = sharedPreferences.getInt("oneboard",0);



        Thread mSplashThread;//thread classdan obje olustrduk uygulamann 4 saniye uyutulmasi icin
        mSplashThread = new Thread(){
            @Override public void run(){
                try {

                    synchronized(this){
                        wait(4000);
                    }
                }catch(InterruptedException ex){

                }
                finally{

                    if(gelen == 0){
                        Intent splash = new Intent(getApplicationContext(),OnBoardScreen.class);
                        startActivity(splash);
                    }
                    else if(gelen ==1)
                    {
                        Intent splash = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(splash);
                    }

                    finish();
                }

            }
        };//thread objesini olustrduk ve istedmz sekilde sekillendrdik
        mSplashThread.start();// thread objesini calistriyoruz



    }
}
