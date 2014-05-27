package com.wanda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wanda.R;
import com.wanda.data.QuestionSheet;
import com.wanda.network.CallbackListenerInterface;
import com.wanda.network.HttpsRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SheetsView extends ActionBarActivity implements CallbackListenerInterface<Object> {


    ListView mListView;
    SheetsListViewAdapter sheetsListViewAdapter = null;
    Vector<QuestionSheet> questionSheets = null;



//    private List<QuestionSheet> createDummyData(){
//
//        List<QuestionSheet> questionSheets = new ArrayList<QuestionSheet>();
//        long time = System.currentTimeMillis();
//
//        QuestionSheet questionSheetA = new QuestionSheet();
//        questionSheetA.setName("TestSheetA");
//        questionSheetA.setCreate_date(new Timestamp(time));
//        questionSheets.add(questionSheetA);
//
//        QuestionSheet questionSheetB = new QuestionSheet();
//        questionSheetB.setName("TestSheetB");
//        questionSheetB.setCreate_date(new Timestamp(time));
//        questionSheets.add(questionSheetB);
//
//        return questionSheets;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_sheets_view);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadQuestionSheet(position);
                //QuestionSheet questionSheet = loadQuestionSheetData();
            }
        });

        loadSheets();

       // List<QuestionSheet> questionSheets = createDummyData();

       // mListView.setAdapter(sheetsListViewAdapter);
        //sheetsListViewAdapter.notifyDataSetChanged();

        //Log.i("SASH", "main");

    }

    private void loadSheets(){
        HttpsRequest httpsRequest = new HttpsRequest(this);
        httpsRequest.execute("getSheets");
    }


    private void loadQuestionSheet(int position){
        HttpsRequest httpsRequest = new HttpsRequest(this);
        httpsRequest.execute("getSheet",  questionSheets.get(position));
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

    @Override
    public void onTaskComplete(Object result) {
        if ( result instanceof java.util.Vector) {
            questionSheets = (Vector<QuestionSheet>) result;
            updateListView();
        } else { //got questionSheet
            QuestionSheet questionSheet = (QuestionSheet) result;
            if (questionSheet == null) {
                Log.d("SASH", "can't retieve question sheet");
                return;
            }
            Log.d("SASH", "got questionSheet");
            startQuestionSheetView(questionSheet);
        }

    }

    private void startQuestionSheetView(QuestionSheet questionSheet){
        Intent intent = new Intent(this, QuestionSheetView.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("questionSheet", questionSheet);
        intent.putExtras(bundle); //Put your id to your next Intent
        startActivity(intent);
        //finish();
    }

    private void updateListView(){
        if (sheetsListViewAdapter!=null){
        sheetsListViewAdapter.clear();
        sheetsListViewAdapter.addAll(questionSheets);
        //sheetsListViewAdapter = new SheetsListViewAdapter(SheetsView.this,
       //         R.layout.sheet_list_item_layout,questionSheets);
        } else {
           sheetsListViewAdapter = new SheetsListViewAdapter(SheetsView.this,
                             R.layout.sheet_list_item_layout,questionSheets);
           mListView.setAdapter(sheetsListViewAdapter);
        }
        sheetsListViewAdapter.notifyDataSetChanged();

    }
}

