package com.wanda.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wanda.R;

import com.wanda.data.Answer;
import com.wanda.data.MultipleChoiceAnswer;
import com.wanda.data.Question;
import com.wanda.data.QuestionSheet;
import com.wanda.data.QuestionType;
import com.wanda.data.RatingAnswer;
import com.wanda.network.CallbackListenerInterface;
import com.wanda.network.HttpsRequest;

import static com.wanda.data.QuestionType.*;

public class QuestionSheetView extends ActionBarActivity
implements ResumeView.OnResumeViewShowedListener, QuestionView.OnAnswerChangedListener,
        ResumeView.OnSendButtonClickedListener, CallbackListenerInterface<Object> {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    QuestionSheetPagerAdapter questionSheetPagerAdapter;

    QuestionSheet questionSheet = null;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Map<Integer, Answer> answers;


//    private QuestionSheet createDummyData(){
//        Log.i("SASH", "creating Dummy-data");
//        QuestionSheet questionSheet = new QuestionSheet();
//        Vector<Question> questions = new Vector<Question>();
//        questionSheet.setQuestions(questions);
//        questionSheet.addQuestion(new Question(1,"erste frage"));
//        questionSheet.addQuestion(new Question(2,"zweite frage"));
//        return questionSheet;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        questionSheet = (QuestionSheet) bundle.getSerializable("questionSheet");

        //QuestionSheet questionSheet = createDummyData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_sheet);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        questionSheetPagerAdapter = new QuestionSheetPagerAdapter(
                getSupportFragmentManager(),questionSheet);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(questionSheetPagerAdapter);

        setTitle(questionSheet.getName());
        answers = new HashMap<Integer, Answer>();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCurrentAnswers(ResumeView resumeview) {
        //Log.d("SASH","got signal from resumeview");

        //build the resume String (ex: 4/5 questions answered)
        //get total number of Questions
        int totalQuestionCount = questionSheet.getQuestionCount();
        //get number of answered qeustions
        int answeredQuestionCount =0;
        Iterator it = answers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            Answer answer = (Answer) pairs.getValue();
            if (answer.getType()==MULTIPLE_CHOICE)
                if (((MultipleChoiceAnswer) answer).getSelectedAnswer()==-1)
                    continue;
            answeredQuestionCount++;
        }
        //build String
        String resultString = String.valueOf(answeredQuestionCount) +
                "/" + String.valueOf(totalQuestionCount) +
                " Fragen beantwortet";

        resumeview.updateView(resultString);
    }

    @Override
    public void setCurrentAnswer(Bundle bundle) {
        Answer answer = null;
        String questionType = bundle.getString("questionType");
        if (questionType.equals("multiple_choice")){
            answer = new MultipleChoiceAnswer();
            ((MultipleChoiceAnswer) answer).setSelectedAnswer(bundle.getInt("answerPos"));
        } else if (questionType.equals("rating")){
            answer = new RatingAnswer();
            ((RatingAnswer) answer).setRating(bundle.getInt("rating"));
        }
        //answer.setAnswerText();
        int questionPos = bundle.getInt("questionPos");
        if (answers.containsKey(questionPos)){
            answers.remove(questionPos);
        }
        answers.put(questionPos,answer);
    }

    @Override
    public void sendAnswers() {
        deleteEmptyAnswers();
        HttpsRequest httpsRequest = new HttpsRequest(this);
        httpsRequest.execute("addAnswers", questionSheet.getID(), answers);
    }

    private void deleteEmptyAnswers() {
        Vector<Integer> vec = new Vector<Integer>();
        Iterator it = answers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            Answer answer = (Answer) pairs.getValue();
            if (answer.getType()==MULTIPLE_CHOICE)
                if (((MultipleChoiceAnswer) answer).getSelectedAnswer()==-1)
                    vec.add((Integer) pairs.getKey());
        }
        for (Integer i: vec){
            answers.remove(i);
        }
    }

    @Override
    public void onTaskComplete(Object result) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class QuestionSheetPagerAdapter extends FragmentPagerAdapter {

        private QuestionSheet questionSheet;

        public QuestionSheetPagerAdapter(FragmentManager fragmentManager, QuestionSheet questionSheet) {
            super(fragmentManager);
            this.questionSheet=questionSheet;
        }

        @Override
        public Fragment getItem(int position) {
            //Log.d("SASH","item request "+ String.valueOf(position));
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == questionSheet.getQuestionCount()){
                return new ResumeView();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("question", questionSheet.getQuestion(position));
            bundle.putInt("questionCount", questionSheet.getQuestionCount());
            QuestionView questionView = null;
            switch (questionSheet.getQuestion(position).getType()) {
                case RATING:
                    questionView = new RatingQuestionView();
                    break;
                case MULTIPLE_CHOICE:
                    questionView = new MultipleChoiceQuestionView();
                    break;
            }
            questionView.setArguments(bundle);
            return questionView;
        }

        @Override
        public int getCount() {

            //Log.d("SASH", "get count " + String.valueOf(questionSheet.getQuestionCount()));
            // Show 3 total pages.

            return questionSheet.getQuestionCount()+1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }



}
