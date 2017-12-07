package segproject.tmmpl;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    //this code was inspired from the following links
    // https://android.jlelse.eu/right-way-to-create-splash-screen-on-android-e7f1709ba154
    //www.youtube.com/watch?v=s8nuJpyQqVc

    protected void onPause(){
        super.onPause();
        //stops music rom playing
        ourSplashMusic.release();
        //destroys activity and restarts when app opens
        finish();
    }

    MediaPlayer ourSplashMusic;
    //global variable

    private static int SPLASH_TIME_OUT=4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ourSplashMusic = MediaPlayer.create(this,R.raw.angelicmusic);
        ourSplashMusic.start();
        //object and reference for sound


        //sets the layout view at the activity_splash_screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                //sets where the next intent is to be after splash screen
                Intent homeIntent= new Intent(SplashScreen.this, UserLogin.class);

                startActivity(homeIntent);
                finish();
            }


        },SPLASH_TIME_OUT);//after 4 seconds, stops and moves to User Login screen

    }


}