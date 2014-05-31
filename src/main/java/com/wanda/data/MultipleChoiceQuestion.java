package com.wanda.data;

import java.util.Vector;

/**
 * Created by sash on 27/05/14.
 */
public class MultipleChoiceQuestion extends Question {

    private Vector<QuestionAnswer> answers;

    public MultipleChoiceQuestion(){
        super(QuestionType.MULTIPLE_CHOICE);
    }

    public Vector<QuestionAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Vector<QuestionAnswer> answers) {
        this.answers = answers;
    }

}
