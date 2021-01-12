package com.example.manageme;

public class MathMission {

    int difficultLevel;
    String numberOfQuestions;
    public MathMission() {
        this.difficultLevel = 0;
        this.numberOfQuestions = null;
    }
    public MathMission(int difficultLevel, String numberOfQuestions) {
        this.difficultLevel = difficultLevel;
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(int difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}

