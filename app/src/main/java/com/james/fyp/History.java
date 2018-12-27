package com.james.fyp;

public class History {

    //field
    //private int historyID;
    private String word;
    private String meaning;

    //contructor
    public History() {
    }

    public History(int historyID,String word,String meaning) {

        //this.historyID = historyID;
        this.word = word;
        this.meaning = meaning;
    }

    //properties


    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }



    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
