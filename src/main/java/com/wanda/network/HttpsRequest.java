package com.wanda.network;

import android.content.Context;
import android.os.AsyncTask;

import com.wanda.data.MetaData;
import com.wanda.json.WandaJsonWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * Created by sash on 04/02/14.
 */
public class HttpsRequest extends AsyncTask<String, String, String > {

    private CallbackListenerInterface<String> callback;

    private Context context;

    public HttpsRequest (CallbackListenerInterface<String> callback) {
        this.context=(Context) callback;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO: attempt authentication against a network service.
        String s = null;
        try {
            HttpsClient myHttpClient = new HttpsClient(context);

            StringWriter jsonStringStream = new StringWriter();
            WandaJsonWriter wandaJsonWriter = new WandaJsonWriter(jsonStringStream);
            MetaData metaData = new MetaData(params[0]);
            wandaJsonWriter.writeMeta(metaData,params[1]);
            jsonStringStream.flush();

            s = myHttpClient.executeHttpPost("https://10.0.2.2:8443/wanda.backend/login", jsonStringStream.toString());
            //s = myHttpClient.executeHttpPost("https://192.168.0.63:8443/wanda.backend/login", metaData.getUsername());
            //s = myHttpClient.executeHttpPost("https://192.168.0.63:8443/wanda.backend/login", "lala");



            // Simulate network access.
            //Thread.sleep(2000);
        } catch (InterruptedException e) {
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(usernameStr)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(passwordStr);
//                }
//            }

        // TODO: register the new account here.
        return s;
    }



    @Override
    protected void onPostExecute(String result) {
//        mAuthTask = null;
//        showProgress(false);
//
//        if (result != null) {
          callback.onTaskComplete(result);
//        } else {
//            passwordField.setError(getString(R.string.error_incorrect_password));
//            passwordField.requestFocus();
//        }

    }

    @Override
    protected void onCancelled() {
        callback.onTaskComplete(null);
    }
}