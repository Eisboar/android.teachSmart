package com.wanda.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanda.R;
import com.wanda.data.Question;
import com.wanda.data.QuestionSheet;

/**
 * Created by sash on 26/05/14.
 */
public abstract class QuestionView extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    protected Question question;

    public QuestionView() {
    }

    protected void intiQuestion(){
        Bundle bundle = getArguments();
        question = (Question) bundle.getSerializable("question");
    }

    protected void setQuestion(View view){
        TextView textView = (TextView) view.findViewById(R.id.questionTextView);
        textView.setText(question.getQuestionText());
        TextView headline = (TextView) view.findViewById(R.id.textView);
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        Bundle bundle = getArguments();
//        question = (Question) bundle.getSerializable("question");
//
//
//        View rootView = inflater.inflate(R.layout.rating_question_view_layout, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//        textView.setText(question.getQuestionText());
//
//        return rootView;
//    }
}
