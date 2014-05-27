package com.wanda.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;

/**
 * Simple data class representing a question sheet
 * Created by sascha haseloff on 22/05/14.
 */
public class QuestionSheet implements Serializable {

    public Vector<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Vector<Question> questions) {
        this.questions = questions;
    }

    private Vector<Question> questions;
    private String name;
    private Timestamp create_date;



    private int ID;

    public QuestionSheet(){ }

    public Question getQuestion(int i){
        return questions.get(i);
    }

    public int getQuestionCount(){
        return questions.size();
    }

    public void addQuestion(Question question){
        questions.addElement(question);
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
