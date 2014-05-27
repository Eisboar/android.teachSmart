package com.wanda.data;

import java.io.Serializable;

/**
 * Created by sash on 22/05/14.
 */
public abstract class Question implements Serializable {

    public int getPos() {
        return pos;
    }

    public String getQuestionText() {
        return questionText;
    }

    private int pos;

    public QuestionType getType() {
        return type;
    }

    private QuestionType type;

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    private String questionText;

    public Question (int pos, String questionText){
        this.pos = pos;
        this.questionText = questionText;
    }

    public Question(QuestionType type){
        this.type = type;
    }

}
