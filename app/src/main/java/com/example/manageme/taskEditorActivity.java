package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class taskEditorActivity extends AppCompatActivity {
    int dataPosition;

    TextView titleTextTaskEditor;
    TextView descriptionTextTextEditor;
    //Buttons
    Button calenderButton;
    Button timerButtonTaskEditor;

    Intent intent;
    public static ArrayList<task> arrayList;
    Gson gson;

    //date set
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    int Year;
    int Month;
    int DayOfMonth;
    int HoursOfDay;
    int Min;
    boolean Is24HourView;


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // priority + title task + details + time(layout shift)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_editor_activity);


        initializer();

        //initialize Gson
        gson = new Gson();

        String details = sharedPreferences.getString("identity_task", null);
        Type type = new TypeToken<ArrayList<task>>() {
        }.getType();
        arrayList = gson.fromJson(details, type);


        if (arrayList == null) {  // base case
            arrayList = new ArrayList<>();
        } else {
            //
            titleTextTaskEditor.setText(arrayList.get(dataPosition).getTaskTitle());
            descriptionTextTextEditor.setText(arrayList.get(dataPosition).getTaskDescription());
            calendar = arrayList.get(dataPosition).getDue();
            updateCalender();
            Is24HourView = false;  // time picker dialogue
        }


        if (dataPosition != -1) {
            titleTextTaskEditor.setText(MainActivity.taskItemArrayList.get(dataPosition));
        } else {
            MainActivity.taskItemArrayList.add("");
            dataPosition = MainActivity.taskItemArrayList.size() - 1;
        }

        titleTextTaskEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.taskItemArrayList.set(dataPosition, String.valueOf(s));
                MainActivity.taskItemArrayAdapter.notifyDataSetChanged();
                HashSet<String> set = new HashSet<>(MainActivity.taskItemArrayList);
                sharedPreferences.edit().putStringSet("task", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(taskEditorActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                calendar=Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);
                                Log.i("Calendar value Calendar", String.valueOf(calendar.getTime()));
                            }
                        }, Year, Month, DayOfMonth);
                datePickerDialog.show();
            }
        });

        // TIME SET BUTTON
        timerButtonTaskEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(taskEditorActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar = Calendar.getInstance();
                                calendar.set(Year, Month, DayOfMonth, hourOfDay, minute);
                                Log.i("Calendar value Calendar", String.valueOf(calendar.getTime()));
                            }

                        }, HoursOfDay, Min, Is24HourView);
                timePickerDialog.show();

            }
        });

    }


    private void setNotification(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        intent.putExtra("identity_task", calendar);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    public void doneUpdate(View view) {


        setNotification(calendar);
        arrayList.get(dataPosition).setTaskTitle(titleTextTaskEditor.getText().toString());
        arrayList.get(dataPosition).setTaskDescription(descriptionTextTextEditor.getText().toString());
        arrayList.get(dataPosition).setDue(calendar);

        String update = gson.toJson(arrayList);
        sharedPreferences.edit().putString("identity_task", update).apply();

        //storing values of  date so it will show accordingly when user is on same page after setting his date.  NOTE: NOT ON LOAD CASE
        updateCalender();
        newAlarm(calendar);
        Toast.makeText(getApplicationContext(), "Task Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    private void newAlarm(Calendar calendar) {
        int hour=calendar.get(Calendar.HOUR);
        int min=calendar.get(Calendar.MINUTE);
        Intent intent= new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,min);

        startActivity(intent);

    }

    public void updateCalender() {

//        calendar=Calendar.getInstance();
        //  Log.i("Calender: ", String.valueOf(calendar.getTime()));
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        DayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        HoursOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        Min = calendar.get(Calendar.MINUTE);

        // CALENDER SET BUTTON
    }

    private void initializer() {
        titleTextTaskEditor = findViewById(R.id.titleTextTaskEditor);
        descriptionTextTextEditor = findViewById(R.id.descriptionTaskEditor);

        calendar = Calendar.getInstance();

        intent = getIntent();
        dataPosition = intent.getIntExtra("dataPosition", -1);
        sharedPreferences = getSharedPreferences("com.example.manage", MODE_PRIVATE);

        calenderButton = findViewById(R.id.calenderButtonTaskEditor);
        timerButtonTaskEditor = findViewById(R.id.timerButtonTaskEditor);

        Year = 0;
        Month = 0;
        DayOfMonth = 0;
        HoursOfDay = 0;
        Min = 0;

        updateCalender();

        Is24HourView = false;
        Log.i("Line 142: ", String.valueOf(calendar.getTime()));
        calendar.set(Year, Month, DayOfMonth, HoursOfDay, Min);
        Log.i("Line 144: ", String.valueOf(calendar.getTime()));

    }

    public void setOnetimeTimer(Context context){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("Shahzaib", Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }

    public void setPriority(View view) {
    }

    public void setMathMission(View view) {
        Intent intent = new Intent(this, math_mission_setting.class);
        intent.putExtra("position", dataPosition);
        startActivity(intent);
    }
}