package com.wanda.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.wanda.R;
import com.wanda.data.MultipleChoiceQuestion;
import com.wanda.data.QuestionAnswer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by sash on 27/05/14.
 */
public class MultipleChoiceQuestionView extends QuestionView {

    public MultipleChoiceQuestionView(){
        super();
    }

    private int lastChanged = -1;

    private Map<Integer,CheckBox> checkBoxes = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null && savedInstanceState.containsKey("lastChecked")){
            lastChanged = savedInstanceState.getInt("lastChecked");
        }

        intiQuestion();

        View view = inflater.inflate(R.layout.multiple_choice_question_view_layout, container, false);

        setQuestion(view);
        createAnswers(view);

        return view;
    }

    private void createAnswers(View view){

        checkBoxes = new HashMap<Integer, CheckBox>();
        question = (MultipleChoiceQuestion) question;
        for (int i=0; i < ((MultipleChoiceQuestion) question).getAnswers().size(); i++) {
            QuestionAnswer answer = ((MultipleChoiceQuestion) question).getAnswers().get(i);
            CheckBox cb = new CheckBox(getActivity().getApplicationContext());
            cb.setText(answer.getAnswerText());


            cb.setButtonDrawable(R.drawable.checkbox);
            cb.setBackgroundResource(R.drawable.answer_frame);
            cb.setPadding(30, 10, 15, 15);
            cb.setChecked(false);
            cb.setTextColor(getResources().getColor(R.color.dark_brown));
            LinearLayout answerLayout = (LinearLayout) view.findViewById(R.id.AnswerLayout);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {

                    if ( isChecked )
                    {
                        //ask if more than 1 is checked?
                        int count=0;
                        Iterator it = checkBoxes.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pairs = (Map.Entry)it.next();
                            CheckBox checkBox = ((CheckBox)pairs.getValue());
                            if (checkBox.isChecked()) count++;
                        }
                        if (count==0)
                            lastChanged=-1;
                        if (count==2)
                            checkBoxes.get(lastChanged).setChecked(false);

                        it = checkBoxes.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pairs = (Map.Entry)it.next();
                            CheckBox checkBox = ((CheckBox)pairs.getValue());
                            if (checkBox.isChecked()){
                                lastChanged = ((Integer)pairs.getKey());
                            }
                        }

                    }else {
                        lastChanged = -1;
                    }
                    reportAnswer();
                }



            });
            answerLayout.addView(cb);
            checkBoxes.put(answer.getPosition(), cb);

        }
        if (lastChanged != -1){
            checkBoxes.get(lastChanged).setChecked(true);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("lastChecked", lastChanged);
    }

    private void reportAnswer(){
        Bundle bundle = new Bundle();
        bundle.putString("questionType", "multiple_choice");
        bundle.putInt("questionPos",question.getPos());
        bundle.putInt("answerPos",lastChanged);
        //Log.d("SASH", String.valueOf(lastChanged));
        try {
            mCallback = (OnAnswerChangedListener) activity;
            mCallback.setCurrentAnswer(bundle);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listener");
        }
    }

}
