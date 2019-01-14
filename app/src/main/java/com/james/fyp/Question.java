package com.james.fyp;

public class Question {

    public static final String DIFFICULTY_BEGINNER = "Beginner";
    public static final String DIFFICULTY_COMPETENT = "Competent";

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answerNr;
    private String difficulty;


    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public Question(String question, String option1, String option2,
                    String option3, int answerNr, String difficulty) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
        this.difficulty = difficulty;

    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }


    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static String[] getAllDifficultyLevels() {
        return new String[]{
                DIFFICULTY_BEGINNER,
                DIFFICULTY_COMPETENT
        };
    }
}
