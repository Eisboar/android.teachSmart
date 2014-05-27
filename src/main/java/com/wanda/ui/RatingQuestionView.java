package com.wanda.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
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
        Bundle bundle = getArguments();
        question = (Question) bundle.getSerializable("question");


        View rootView = inflater.inflate(R.layout.rating_question_view_layout, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(question.getQuestionText());

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        ratingBar.setStepSize((float) 1.0);
        LayerDrawable starsForground = (LayerDrawable) ratingBar.getProgressDrawable();
        LayerDrawable starsBackground = (LayerDrawable) ratingBar.getProgressDrawable();
        starsBackground.getDrawable(2).setColorFilter(Color.parseColor("#E4DFA0"), PorterDuff.Mode.SRC_ATOP);
        starsBackground.getDrawable(0).setColorFilter(Color.parseColor("#7C6542"), PorterDuff.Mode.SRC_ATOP);
        return rootView;
    }
}
