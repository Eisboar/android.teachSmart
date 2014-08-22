package com.wanda.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.internal.ac;
import com.wanda.R;

public class ResumeView extends Fragment {

    OnResumeViewShowedListener mCallback;
    OnSendButtonClickedListener sendButtonClickedCallback;
    Button button;
    Activity activity;
    View view;


    public interface OnResumeViewShowedListener {
        public void getCurrentAnswers(ResumeView resumeview);
    }

    public interface OnSendButtonClickedListener {
        public void sendAnswers();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Bundle bundle = getArguments();
        //question = (Question) bundle.getSerializable("question");


        view = inflater.inflate(R.layout.fragment_resume_view, container, false);

        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    sendButtonClickedCallback = (OnSendButtonClickedListener) activity;
                    sendButtonClickedCallback.sendAnswers();
                } catch (ClassCastException e) {
                    throw new ClassCastException(activity.toString()
                            + " must implement OnHeadlineSelectedListener");
                }

            }

        });

        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(question.getQuestionText());

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("SASH","now visibale");
            try {
                mCallback = (OnResumeViewShowedListener) activity;
                mCallback.getCurrentAnswers(this);
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
        else {  }
    }

    public void updateView(String resultString){
        Log.d("SASH","update sth");
        //erase existing
        LinearLayout answerLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        answerLayout.removeAllViews();

        TextView resultText = new TextView(getActivity().getApplicationContext());
        resultText.setGravity(Gravity.LEFT);
        resultText.setTextColor(getResources().getColor(R.color.dark_brown));
        resultText.setText(resultString);
        resultText.setTextSize(22.0f);
        //resultText.setTypeface(null, Typeface.BOLD);
        resultText.setPadding(30,30,0,0);

        //answerLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        answerLayout.addView(resultText);
    }

}

