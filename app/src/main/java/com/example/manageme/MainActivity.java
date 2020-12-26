package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView taskListView;
    static ArrayList<String> taskArrayList;
    static ArrayAdapter<String> taskArrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
//        new CountDownTimer(3000, 3000) {
////
////            public void onTick(long millisUntilFinished) {
////                setContentView(R.layout.activity_main_page);
////            }
////
////            public void onFinish() {
////                setContentView(R.layout.activity_main);
////            }
////        }.start();

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.manage_me", Context.MODE_PRIVATE);

        taskArrayList.add(0, "Shahzaib");
        taskArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskArrayList);
        taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(taskArrayAdapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this, taskEditorActivity.class);
                intent.putExtra("dataPosition",position);
                startActivity(intent);
            }
        });
    }



    private void initializer() {
        taskListView=findViewById(R.id.taskListView);
        taskArrayList=new ArrayList<>();
    }
}