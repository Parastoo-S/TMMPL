package segproject.tmmpl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    //private TextView textView;
    //private ImageView imageView;

    private static int SPLASH_TIME_OUT=4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent= new Intent(SplashScreen.this, NewQuickAccess.class);
                startActivity(homeIntent);
                finish();
            }


        },SPLASH_TIME_OUT);

    }
}