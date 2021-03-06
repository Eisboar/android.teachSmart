package com.wanda.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.wanda.data.Answer;
import com.wanda.data.MetaData;
import com.wanda.data.QuestionSheet;
import com.wanda.json.WandaJsonReader;
import com.wanda.json.WandaJsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by sash on 04/02/14.
 */
public class HttpsRequest extends AsyncTask<Object, String, Object > {

    //192.168.0.63
    private String serverAddress = "https://192.168.0.63:8443/wanda.backend/";
    //private String serverAddress = "https://172.20.10.8:8443/wanda.backend/";
    //private String serverAddress = "https://172.24.120.131:8443/wanda.backend/";

    private CallbackListenerInterface<Object> callback;

    private Context context;

    private String task = null;

    private WandaJsonWriter wandaJsonWriter = null;


    public HttpsRequest (CallbackListenerInterface<Object> callback) {
        this.context=(Context) callback;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Object... params) {
        task = (String) params[0];
        String s = null;

        try {
            HttpsClient myHttpClient = new HttpsClient(context);


            StringWriter jsonStringStream = new StringWriter();
            wandaJsonWriter = new WandaJsonWriter(jsonStringStream);

            if (task.equals("login")) {
                buildLoginRequest((String) params[1], (String) params[2]);
            } else if (task.equals("getSheets")) {

            } else if (task.equals("getSheet")){
                QuestionSheet questionSheet = (QuestionSheet) params[1];
                Log.d("SASH", "try to retrieve: "+questionSheet.getName());
                wandaJsonWriter.writeGetSheetRequest(questionSheet.getID());
            } else if (task.equals("addAnswers")){
                int questionSheetID = (Integer) params[1];
                Map<Integer,Answer> answers =  (Map<Integer,Answer>) params[2];
                wandaJsonWriter.writeAddAnswersRequest(questionSheetID, answers);
            }

            jsonStringStream.flush();
            Log.d("SASH",jsonStringStream.toString());
            s = myHttpClient.executeHttpPost(serverAddress+task, jsonStringStream.toString());

        } catch (InterruptedException e) {
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }



    @Override
    protected void onPostExecute(Object result) {
         WandaJsonReader jsonReader = new WandaJsonReader();
         if (task=="login") {
              //do sth
          } else if (task == "getSheet"){
             result = jsonReader.parseGetQuestionSheetResponse((String) result);
          } else if (task == "getSheets"){
             result = jsonReader.parseGetSheetsResponse((String) result);
          }
          callback.onTaskComplete(result);
    }

    @Override
    protected void onCancelled() {
        callback.onTaskComplete(null);
    }


    private void buildLoginRequest(String username, String password) throws IOException {
        MetaData metaData = new MetaData(username);
        wandaJsonWriter.writeMeta(metaData,password);
    }
}