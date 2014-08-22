package com.wanda.data;

/**
 * Created by sash on 03/06/14.
 */
public abstract class Answer {

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    protected QuestionType type;
    protected String answerText;


}
