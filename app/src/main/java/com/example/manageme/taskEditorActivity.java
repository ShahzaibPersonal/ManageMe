package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class taskEditorActivity extends AppCompatActivity {
    int dataPosition;
    TextView titleTextTaskEditor;
    TextView descriptionTextTextEditor;
    Button calenderButton;
    Intent intent;
    ArrayList<Task> arrayList;
    Gson gson;

    //date set
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    int year ;
    int month ;
    int dayOfMonth ;

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

        calendar=Calendar.getInstance();
        calenderButton=findViewById(R.id.calenderButton);

         year = calendar.get(Calendar.YEAR);
         month = calendar.get(Calendar.MONTH);
         dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

         calendar.set(year,month,dayOfMonth);
    }


    public void doneUpdate(View view) {
        arrayList.get(dataPosition).setTaskTitle(titleTextTaskEditor.getText().toString());
        arrayList.get(dataPosition).setTaskDescription(descriptionTextTextEditor.getText().toString());
        arrayList.get(dataPosition).setDue(calendar);
        String descriptionStore=gson.toJson(arrayList);
        sharedPreferences.edit().putString("identity_task",descriptionStore).apply();
        //Log.i("calender ",calendar.toString());
        Toast.makeText(getApplicationContext(),"Task Up Dated Successfully " + calendar.getTime(), Toast.LENGTH_SHORT).show();
    }


    public void setCalender(View view) {

        datePickerDialog = new DatePickerDialog(taskEditorActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        Log.i("Date Set is ",calendar.getTime().toString());
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}