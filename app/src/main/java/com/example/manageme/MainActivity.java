package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView taskListView;
    ArrayList<String> taskArrayList =new ArrayList<>();
    ArrayAdapter<String> taskArrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        new CountDownTimer(3000, 3000) {

            public void onTick(long millisUntilFinished) {
                setContentView(R.layout.activity_main_page);
            }

            public void onFinish() {
                setContentView(R.layout.activity_main);
            }
        }.start();

        //sharedPreferences= getApplicationContext().getSharedPreferences("com.example.manage_me", Context.MODE_PRIVATE);

        taskArrayList.add("Temp task");
        taskArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,taskArrayList);

        taskListView=findViewById(R.id.taskListView);
        taskListView.setAdapter(taskArrayAdapter);
    }
}