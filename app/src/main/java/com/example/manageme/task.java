package com.example.manageme;

import android.renderscript.RenderScript;

import java.sql.Time;
import java.util.Calendar;

public class task {

    String taskTitle;
    String taskDescription;
    Calendar due;
    Boolean priority;
    MathMission mathMission;




    public MathMission getMathMission() {
        return mathMission;
    }

    public void setMathMission(MathMission mathMission) {
        this.mathMission = mathMission;
    }

    public Calendar getDue() {
        return due;
    }

    public void setDue(Calendar due) {
        this.due = due;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public task(String taskTitle, String taskDescription) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

}
