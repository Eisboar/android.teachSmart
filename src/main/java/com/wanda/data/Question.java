package com.wanda.data;

/**
 * Created by sash on 22/05/14.
 */
public class Question {

    public int getPos() {
        return pos;
    }

    public String getQuestionText() {
        return questionText;
    }

    private int pos;
    private String questionText;

    public Question (int pos, String questionText){
        this.pos = pos;
        this.questionText = questionText;
    }

}
