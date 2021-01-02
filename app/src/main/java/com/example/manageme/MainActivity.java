package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity<firstTime> extends AppCompatActivity {
    boolean firstTime=true;


    ListView taskListView;
    static ArrayList<String> taskItemArrayList;
    static ArrayAdapter<String> taskItemArrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new CountDownTimer(3000, 3000) {
//
//            public void onTick(long millisUntilFinished) {
//                setContentView(R.layout.activity_main_page);
//            }
//
//            public void onFinish() {
//
//            }
//        }.start();
        initializer();
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.manage_me", Context.MODE_PRIVATE);

        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("task",null);

        taskItemArrayList.add(0, "Shahzaib");
        taskItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskItemArrayList);
        taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(taskItemArrayAdapter);


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
        taskItemArrayList =new ArrayList<>();
    }
}