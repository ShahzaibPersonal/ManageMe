package com.example.manageme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    ArrayList<task> arrayList;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initializer();   // initialize



        String description = sharedPreferences.getString("identity_task", null);

        Type type = new TypeToken<ArrayList<task>>() {}.getType();              // storing type with type token
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

                String taskString = addDirectTaskEditText.getText().toString();

                // check if  edit text is not empty

                if(!taskString.matches("")){
                    taskItemArrayList.add( addDirectTaskEditText.getText().toString());
                    taskItemArrayAdapter.notifyDataSetChanged();
                    task myNewTask=new task(addDirectTaskEditText.getText().toString(),"");
                    arrayList.add(myNewTask);
                    String updated=gson.toJson(arrayList);
                    sharedPreferences.edit().putString("identity_task",updated).apply();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter title of task",Toast.LENGTH_SHORT).show();
                }


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

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("You really want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(position);
                                Log.i("array Now",arrayList.toString());
                                if(arrayList==null){
                                    arrayList=new ArrayList<>();
                                }
                                else {
                                    taskItemArrayList.clear();
                                    for (int i = 0; i < arrayList.size(); i++) {
                                        taskItemArrayList.add(arrayList.get(i).getTaskTitle());
                                    }
                                    taskItemArrayAdapter.notifyDataSetChanged();
                                }
                                String updated=gson.toJson(arrayList);
                                sharedPreferences.edit().putString("identity_task",updated).apply();
                            }
                        })
                        .setNegativeButton("No", null).show();
                return true;
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