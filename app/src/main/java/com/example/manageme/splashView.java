package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class splashView extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    boolean firstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);

        initializer();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                if(firstTime){
                    Intent intent = new Intent(splashView.this, WelcomePage.class);
                    firstTime=false;
                    sharedPreferences.edit().putBoolean("first_time",firstTime).apply();
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(splashView.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
    private void initializer() {
        sharedPreferences=getSharedPreferences("com.example.manage",MODE_PRIVATE);
        firstTime=sharedPreferences.getBoolean("first_time",true);

    }
}