package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class taskEditorActivity extends AppCompatActivity {
    int dataPosition;
    TextView titleTextTaskEditor;
    TextView descriptionTextTextEditor;
    Intent intent;
    ArrayList<Task> arrayList;
    Gson gson;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // priority + title task + description + time(layout shift)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_editor_activity);
        initializer();

        //initialize Gson
        gson=new Gson();


        String description=sharedPreferences.getString("identity_task",null);
        Type type= new TypeToken<ArrayList<Task>>(){}.getType();
        arrayList=gson.fromJson(description,type);

        if(arrayList==null){
           arrayList=new ArrayList<>();
        }
        else {
        titleTextTaskEditor.setText(arrayList.get(dataPosition).getTaskTitle());
        descriptionTextTextEditor.setText(arrayList.get(dataPosition).getTaskDescription());
        }


        if(dataPosition != -1){
          titleTextTaskEditor.setText(MainActivity.taskItemArrayList.get(dataPosition));
        }
        else{
            MainActivity.taskItemArrayList.add("");
            dataPosition=MainActivity.taskItemArrayList.size()-1;
        }
        titleTextTaskEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.taskItemArrayList.set(dataPosition,String.valueOf(s));
                MainActivity.taskItemArrayAdapter.notifyDataSetChanged();
                HashSet<String> set=new HashSet<>(MainActivity.taskItemArrayList);
                sharedPreferences.edit().putStringSet("task",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initializer() {
        titleTextTaskEditor=findViewById(R.id.titleTextTaskEditor);
        descriptionTextTextEditor=findViewById(R.id.descriptionTaskEditor);
      //  addButton=findViewById(R.id.addButton);
        intent= getIntent();
        dataPosition =intent.getIntExtra("dataPosition",-1);
        sharedPreferences=getSharedPreferences("com.example.manage",MODE_PRIVATE);
    }


    public void doneUpdate(View view) {
        arrayList.get(dataPosition).setTaskTitle(titleTextTaskEditor.getText().toString());
        arrayList.get(dataPosition).setTaskDescription(descriptionTextTextEditor.getText().toString());
        String descriptionStore=gson.toJson(arrayList);
        sharedPreferences.edit().putString("identity_task",descriptionStore).apply();
    }
}