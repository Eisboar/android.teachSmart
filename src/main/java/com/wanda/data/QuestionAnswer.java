package com.wanda.data;

import java.io.Serializable;

/**
 * Created by sash on 27/05/14.
 */
public class QuestionAnswer implements Serializable {

    private int position;
    private String answerText;

    public QuestionAnswer(){

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

}
