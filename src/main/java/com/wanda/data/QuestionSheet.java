package com.wanda.data;

import java.util.Vector;

/**
 * Simple data class representing a question sheet
 * Created by sascha haseloff on 22/05/14.
 */
public class QuestionSheet {

    private Vector<Question> questions;

    public QuestionSheet(){
        questions = new Vector<Question>();
    }

    public Question getQuestion(int i){
        return questions.get(i);
    }

    public int getQuestionCount(){
        return questions.size();
    }

    public void addQuestion(Question question){
        questions.addElement(question);
    }

}
