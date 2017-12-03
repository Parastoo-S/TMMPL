package segproject.tmmpl;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    //private TextView textView;
    //private ImageView imageView;

    protected void onPause(){
        super.onPause();
        //stops music rom playing
        ourSplashMusic.release();
        //destroys activty and restarts when app opens
        finish();
    }

    MediaPlayer ourSplashMusic;
    //global variable



    private static int SPLASH_TIME_OUT=4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        ourSplashMusic = MediaPlayer.create(this,R.raw.angelicmusic);
        ourSplashMusic.start();
        //object and refrence for sound



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent= new Intent(SplashScreen.this, Shopping.class);
                startActivity(homeIntent);
                finish();
            }


        },SPLASH_TIME_OUT);

    }


}