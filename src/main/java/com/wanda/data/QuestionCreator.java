package com.wanda.data;

/**
 * Created by sash on 26/05/14.
 */
public class QuestionCreator {

    public static Question createQuestion(String typeString){
        Question question = null;
        if (typeString.toLowerCase().equals("rating")){
            question = new RatingQuestion();
        }
        return question;

    }

}
