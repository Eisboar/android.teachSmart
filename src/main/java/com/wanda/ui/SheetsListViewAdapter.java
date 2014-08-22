package com.wanda.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanda.R;
import com.wanda.data.QuestionSheet;

import java.util.List;
import java.util.Vector;

public class SheetsListViewAdapter extends ArrayAdapter<QuestionSheet> {

    private int resource;

    public SheetsListViewAdapter(Context context ,int resource, List<QuestionSheet> questionSheets){
        super(context, resource, questionSheets);
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Log.i("SASH", "in");
        LinearLayout sheetInfoView;
        QuestionSheet questionSheet = this.getItem(position);

        if(convertView==null) {
            sheetInfoView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;

            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, sheetInfoView, true);
        }else{
            sheetInfoView = (LinearLayout) convertView;
        }

        //retrieve the TextView's
        TextView sheetNameTextView =(TextView)sheetInfoView.findViewById(R.id.sheetName);
        TextView sheetTimestampTextView =(TextView)sheetInfoView.findViewById(R.id.sheetTimestamp);

        //Fill them with the appropriate data from the question sheet
        sheetNameTextView.setText(questionSheet.getName());
        sheetNameTextView.setTextColor(parent.getResources().getColor(R.color.dark_brown));
        //sheetNameTextView.setTextColor(R.color.dark_brown);
        sheetNameTextView.setTextSize(18.00f);
        sheetNameTextView.setTypeface(null, Typeface.BOLD);

        sheetTimestampTextView.setText(questionSheet.getCreate_date().toString());
       // sheetTimestampTextView.setTextColor(R.color.dark_brown);
        sheetTimestampTextView.setTextColor(parent.getResources().getColor(R.color.brown));

        return sheetInfoView;
    }

}
