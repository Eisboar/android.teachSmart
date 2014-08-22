package com.wanda.data;

/**
 * Created by sash on 03/06/14.
 */
public class RatingAnswer extends Answer {

    public RatingAnswer(){
        this.type = QuestionType.RATING;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    //1-5
    public int rating;

}
