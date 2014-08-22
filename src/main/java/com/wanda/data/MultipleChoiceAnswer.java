package com.wanda.data;

/**
 * Created by sash on 03/06/14.
 */
public class MultipleChoiceAnswer  extends  Answer{

    public MultipleChoiceAnswer(){
        this.type = QuestionType.MULTIPLE_CHOICE;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    private int selectedAnswer;
}
