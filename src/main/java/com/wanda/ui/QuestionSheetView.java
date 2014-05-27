package com.wanda.ui;

import java.util.Locale;
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

import com.wanda.data.Question;
import com.wanda.data.QuestionSheet;

public class QuestionSheetView extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    QuestionSheetPagerAdapter questionSheetPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

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
        QuestionSheet questionSheet = (QuestionSheet) bundle.getSerializable("questionSheet");

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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle bundle = new Bundle();
            bundle.putSerializable("question", questionSheet.getQuestion(position));
            QuestionView questionView = new RatingQuestionView();
            questionView.setArguments(bundle);
            return questionView;
        }

        @Override
        public int getCount() {
            //Log.i("SASH", "get count");
            // Show 3 total pages.
            return questionSheet.getQuestionCount();
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
