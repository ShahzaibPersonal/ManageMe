package com.example.manageme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import static com.example.manageme.R.id.difficultyLevelSeekBar;
import static com.example.manageme.R.id.mathQueryDifficultPreviewTextView;

public class math_mission_setting extends AppCompatActivity {

    Intent intent;
    SharedPreferences sharedPreferences;
    ArrayList<task> arrayList;
    int position;
    SeekBar seekBar;
    Gson gson;
    Type type;

    int variable1;
    int variable2;
    int limit;

    ArrayList<String> symbolArray;

    Button option1;
    Button option2;
    Button option3;
    TextView mathQuery;
    MathMission mathMissionTaskDetails;

    int numberOfQuestions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_mission_setting);
        initializer();
        setSymbolArray();

        gson = new Gson();
        type = new TypeToken<ArrayList<task>>() {
        }.getType();


        String details = sharedPreferences.getString("identity_task", null);
        Type type = new TypeToken<ArrayList<task>>() {
        }.getType();
        arrayList = gson.fromJson(details, type);

        if (arrayList == null) {  // base case
            arrayList = new ArrayList<>();
        } else {
            mathMissionTaskDetails.setDifficultLevel(1);  // set default value first when --> already not set
            mathMissionTaskDetails.setNumberOfQuestions("1");
            arrayList.get(position).setMathMission(mathMissionTaskDetails);
            String detail = gson.toJson(arrayList);
            sharedPreferences.edit().putString("identity_task", detail).apply();
            seekBar.setProgress(arrayList.get(position).getMathMission().getDifficultLevel());
        }

        setQueryUpdate();
    // Button Listeners

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mathMissionTaskDetails.setNumberOfQuestions((String) option1.getText());
                Log.i("Number of Questions", String.valueOf(mathMissionTaskDetails.getNumberOfQuestions()));
                option1.setBackground(getResources().getDrawable(R.drawable.custom_selected_button));
                option2.setBackground(getResources().getDrawable(R.drawable.custom_button));
                option3.setBackground(getResources().getDrawable(R.drawable.custom_button));

            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mathMissionTaskDetails.setNumberOfQuestions((String) option2.getText());
                Log.i("Number of Questions", String.valueOf(mathMissionTaskDetails.getNumberOfQuestions()));
                option2.setBackground(getResources().getDrawable(R.drawable.custom_selected_button));
                option1.setBackground(getResources().getDrawable(R.drawable.custom_button));
                option3.setBackground(getResources().getDrawable(R.drawable.custom_button));

            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mathMissionTaskDetails.setNumberOfQuestions((String) option3.getText());
                Log.i("Number of Questions", String.valueOf(mathMissionTaskDetails.getNumberOfQuestions()));
                option3.setBackground(getResources().getDrawable(R.drawable.custom_selected_button));
                option1.setBackground(getResources().getDrawable(R.drawable.custom_button));
                option2.setBackground(getResources().getDrawable(R.drawable.custom_button));


            }
        });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Position", String.valueOf(progress));

                mathMissionTaskDetails.setDifficultLevel(progress);  // set default value first when --> already not set

                arrayList.get(position).setMathMission(mathMissionTaskDetails);
                String detail = gson.toJson(arrayList);
                sharedPreferences.edit().putString("identity_task", detail).apply();
                seekBar.setProgress(arrayList.get(position).getMathMission().getDifficultLevel());
                setQueryUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setQueryUpdate() {
        int querySymbol;
        switch (mathMissionTaskDetails.getDifficultLevel()){

                case 0:
                    limit=5;
                    variable1 = new Random().nextInt(limit);
                    variable2 = new Random().nextInt(limit);
                    querySymbol=new Random().nextInt(symbolArray.size());

                    while( variable2 == variable1){
                        variable2 = new Random().nextInt(limit);
                    }
                    mathQuery.setText(variable1+" "+symbolArray.get(querySymbol)+" "+variable2);
                    break;
                case 1:
                    limit=10;
                    variable1 = new Random().nextInt(limit);
                    variable2 = new Random().nextInt(limit);
                    querySymbol=new Random().nextInt(symbolArray.size());

                    while( variable2 == variable1){
                        variable2 = new Random().nextInt(limit);
                    }
                    mathQuery.setText(variable1+" "+symbolArray.get(querySymbol)+" "+variable2);
                    break;
                case 2:
                    limit=30;
                    variable1 = new Random().nextInt(limit);
                    variable2 = new Random().nextInt(limit);
                    querySymbol=new Random().nextInt(symbolArray.size());

                    while( variable2 == variable1){
                        variable2 = new Random().nextInt(limit);
                    }
                    mathQuery.setText(variable1+" "+symbolArray.get(querySymbol)+" "+variable2);
                    break;
        }
    }

    private void initializer() {
        seekBar=findViewById(difficultyLevelSeekBar);
        Log.i("seek bar ", String.valueOf(seekBar));
        sharedPreferences = getSharedPreferences("com.example.manage", MODE_PRIVATE);
        intent = getIntent();
        position = intent.getIntExtra("position", -1);
        symbolArray=new ArrayList<String>();
        mathQuery=findViewById(mathQueryDifficultPreviewTextView);
        mathMissionTaskDetails = new MathMission();
        option1 =findViewById(R.id.oneQuestion);
        option2=findViewById(R.id.twoQuestion);
        option3=findViewById(R.id.threeQuestion);



    }

    private void setSymbolArray() {
        symbolArray.add("+");
        symbolArray.add("-");
    }

}