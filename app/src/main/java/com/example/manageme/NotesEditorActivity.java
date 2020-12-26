package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

class Notes_Editor_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // priority + title task + description + time(layout shift)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_editor_activity);
    }
}