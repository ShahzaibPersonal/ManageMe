package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity<firstTime> extends AppCompatActivity {

    ListView taskListView;
    EditText addDirectTaskEditText;
    //hello


    Button addButton;
    static ArrayList<String> taskItemArrayList;
    static ArrayAdapter<String> taskItemArrayAdapter;
    SharedPreferences sharedPreferences;
    ArrayList<Task> arrayList;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initializer();   // initialize


        String description = sharedPreferences.getString("identity_task", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();              // storing type with type token
        arrayList = gson.fromJson(description, type);           // json format to java object

        if(arrayList==null){
            arrayList=new ArrayList<>();
        }
        else {
           for(int i=0; i<arrayList.size();i++){
               taskItemArrayList.add(arrayList.get(i).getTaskTitle());
           }
//            taskItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskItemArrayList);
//            taskListView.setAdapter(taskItemArrayAdapter);
        }

//        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("task",null);

//        Log.i("Set: ", String.valueOf(set));
//        taskItemArrayList=new ArrayList<>(set);
//       taskItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskItemArrayList);
//        taskListView.setAdapter(taskItemArrayAdapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){


                // check if  edit text is not empty
                taskItemArrayList.add( addDirectTaskEditText.getText().toString());
                taskItemArrayAdapter.notifyDataSetChanged();

                Task myNewTask=new Task(addDirectTaskEditText.getText().toString(),"");
                arrayList.add(myNewTask);

                String descriptionStore=gson.toJson(arrayList);
                sharedPreferences.edit().putString("identity_task",descriptionStore).apply();

//                HashSet<String> set = new HashSet<>(MainActivity.taskItemArrayList);
//                sharedPreferences.edit().putStringSet("task", set).apply();
//                Log.i("Shared Preference", String.valueOf(sharedPreferences.getStringSet("task", null)));
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, taskEditorActivity.class);
                intent.putExtra("dataPosition", position);
                startActivity(intent);
            }
        });
    }


    private void initializer() {
        taskListView = findViewById(R.id.taskListView);
        addButton = findViewById(R.id.addButton);
        taskItemArrayList = new ArrayList<>();

        taskListView = (ListView) findViewById(R.id.taskListView);
        addDirectTaskEditText = findViewById(R.id.addDirectTaskEditText);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.manage", Context.MODE_PRIVATE);

        taskItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskItemArrayList);
        taskListView.setAdapter(taskItemArrayAdapter);

        gson = new Gson();
    }
}