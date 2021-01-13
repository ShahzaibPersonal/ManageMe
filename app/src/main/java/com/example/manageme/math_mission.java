package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class math_mission extends AppCompatActivity {

    // Variable deceleration

    TextView timerTextView,scoreTextView, queryTextView;
    EditText answerEditText;
    ArrayList<String> symbolArray=new ArrayList<String>();
    Random randomQueryVariable;

    CountDownTimer countDownTimer;

    int score;

    boolean stopGame;

    public void start(View view){
        // text views initialized
        queryTextView.setVisibility(View.VISIBLE);
        newQuestion(view);
    }

    public void timer(View view){
        countDownTimer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(millisUntilFinished / 1000 +"s");
            }
            @Override
            public void onFinish() {
                scoreTextView.setText(Integer.toString(score) +"/"+Integer.toString(3));
                newQuestion(view);
            }
        };
    }


    public void newQuestion(View view){
        timer(view);
        countDownTimer.start();

        int first= randomQueryVariable.nextInt(21);
        int second= randomQueryVariable.nextInt(21);
        int indexForFunction= randomQueryVariable.nextInt(4);

        queryTextView.setText(first+" "+symbolArray.get(indexForFunction)+" "+second);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_mission);
        initializer();
        setSymbolArray();

    }
    private void initializer() {
        // text views initialized
        timerTextView=findViewById(R.id.timerTextView);
        scoreTextView=findViewById(R.id.scoreTextView);
        queryTextView=findViewById(R.id.queryTextView);
        answerEditText=findViewById(R.id.answerEditText);
        randomQueryVariable =new Random();
        stopGame=false;
    }

    private void setSymbolArray(){
        symbolArray.add("+");
        symbolArray.add("-");
    }

}