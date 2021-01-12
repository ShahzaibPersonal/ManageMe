package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomePage extends AppCompatActivity {

Button getStartedButtonWelcomePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        getStartedButtonWelcomePage=findViewById(R.id.getStartedButtonWelcomePage);

    }

    public void getStarted(View view) {
        Intent intent = new Intent(WelcomePage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}