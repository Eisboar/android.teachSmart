package com.wanda.network;

import android.content.Context;
import android.os.AsyncTask;

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
        String s = null;            try {
            HttpsClient myHttpClient = new HttpsClient(context);
            s = myHttpClient.executeHttpGet("https://192.168.0.63:8443/wanda.backend/myresources");



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