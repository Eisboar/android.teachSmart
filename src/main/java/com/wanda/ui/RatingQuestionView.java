package com.wanda.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wanda.R;
import com.wanda.data.Question;

/**
 * Created by sash on 26/05/14.
 */
public class RatingQuestionView extends QuestionView {
    public RatingQuestionView() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        intiQuestion();

        View view = inflater.inflate(R.layout.rating_question_view_layout, container, false);
        setQuestion(view);

        //init ratingBar
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar.setStepSize((float) 1.0);
        LayerDrawable starsBackground = (LayerDrawable) ratingBar.getProgressDrawable();
        starsBackground.getDrawable(2).setColorFilter(Color.parseColor("#E4DFA0"), PorterDuff.Mode.SRC_ATOP);
        starsBackground.getDrawable(0).setColorFilter(Color.parseColor("#7C6542"), PorterDuff.Mode.SRC_ATOP);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                reportAnswer((int) rating);
            }
        });

        return view;
    }

    private void reportAnswer(int currentRating){
        Bundle bundle = new Bundle();
        bundle.putString("questionType", "rating");
        bundle.putInt("questionPos",question.getPos());
        bundle.putInt("rating",currentRating);
        //Log.d("SASH", String.valueOf(currentRating));
        try {
            mCallback = (OnAnswerChangedListener) activity;
            mCallback.setCurrentAnswer(bundle);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listener");
        }
    }
}
