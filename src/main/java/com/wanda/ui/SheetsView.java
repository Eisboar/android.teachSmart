package com.wanda.ui;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wanda.R;
import com.wanda.data.QuestionSheet;

import java.sql.Date;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SheetsView extends ActionBarActivity {

    ListView mListView;

    private List<QuestionSheet> createDummyData(){

        List<QuestionSheet> questionSheets = new ArrayList<QuestionSheet>();
        long time = System.currentTimeMillis();;

        QuestionSheet questionSheetA = new QuestionSheet();
        questionSheetA.setName("TestSheetA");
        questionSheetA.setCreate_date(new Timestamp(time));
        questionSheets.add(questionSheetA);

        QuestionSheet questionSheetB = new QuestionSheet();
        questionSheetB.setName("TestSheetB");
        questionSheetB.setCreate_date(new Timestamp(time));
        questionSheets.add(questionSheetB);

        return questionSheets;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_sheets_view);
        mListView = (ListView) findViewById(R.id.listView);

        List<QuestionSheet> questionSheets = createDummyData();
        SheetsListViewAdapter sheetsListViewAdapter = new SheetsListViewAdapter(SheetsView.this,
                R.layout.sheet_list_item_layout,questionSheets);
        mListView.setAdapter(sheetsListViewAdapter);
        sheetsListViewAdapter.notifyDataSetChanged();

        //Log.i("SASH", "main");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question_sheets_view, menu);
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

}

