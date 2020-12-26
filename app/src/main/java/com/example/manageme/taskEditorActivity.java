package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class taskEditorActivity extends AppCompatActivity {
    int dataPosition;
    //String titleExtraTaskEditor;
    TextView titleTextTaskEditor;
    TextView descriptionTextTextEditor;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // priority + title task + description + time(layout shift)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_editor_activity);

        initializer();
        if(dataPosition != -1){
          titleTextTaskEditor.setText(MainActivity.taskArrayList.get(dataPosition));
        }
        titleTextTaskEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.taskArrayList.set(dataPosition,String.valueOf(s));
                MainActivity.taskArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initializer() {
        titleTextTaskEditor=findViewById(R.id.titleTextTaskEditor);
        descriptionTextTextEditor=findViewById(R.id.descriptionTaskEditor);
        intent= getIntent();
        dataPosition =intent.getIntExtra("dataPosition",-1);

    }

    public void doneUpdate(View view) {
    }
}