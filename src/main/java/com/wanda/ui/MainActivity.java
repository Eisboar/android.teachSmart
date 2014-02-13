package com.wanda.ui;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.wanda.R;
import com.wanda.network.CallbackListenerInterface;
import com.wanda.network.HttpsRequest;


public class MainActivity extends ActionBarActivity  {

    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();

        }

        //new HttpRequest(this).execute("http://192.168.0.7:8080/wanda.backend/myresources");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
     * A placeholder fragment containing a simple view.
     */
    public  class PlaceholderFragment extends Fragment implements CallbackListenerInterface<String> {

        private EditText editText;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            editText = (EditText) rootView.findViewById(R.id.editText);
            new HttpsRequest(this).execute(new String[] {"http://192.168.0.63:8080/wanda.backend/myresources"});

            return rootView;
        }

        @Override
        public void onTaskComplete(String result) {
            editText.setText(result+"lala");
        }
    }

}
