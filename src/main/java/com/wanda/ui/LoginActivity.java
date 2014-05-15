package com.wanda.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wanda.R;
import com.wanda.data.MetaData;
import com.wanda.network.CallbackListenerInterface;
import com.wanda.network.HttpsClient;
import com.wanda.network.HttpsRequest;


/**
 * simple login screen, following the default android studio login
 * view.
 * showing an animation when logging in (progress view api 12+)
 */
public class LoginActivity extends Activity implements CallbackListenerInterface<String> {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private HttpsRequest mAuthTask = null;

    private MetaData metaData;

    // UI references.
    private EditText usernameField;
    private EditText passwordField;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //resources for predefined Strings
        Resources res = getResources();

        //set the username hint (grey default)
        String hintUsername = String.format(res.getString(R.string.username));
        usernameField = (EditText) findViewById(R.id.username);
        usernameField.setHint(hintUsername);


        //set the password hint (grey default)
        String hintPassword = String.format(res.getString(R.string.password));
        passwordField = (EditText) findViewById(R.id.password);
        passwordField.setHint(hintPassword);

        //perform login on enter key
        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //setting up the login form and the progress view
        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        //set up the button caption
        String loginButtonStr  = String.format(res.getString(R.string.login));
        Button loginButton = (Button) findViewById(R.id.sign_in_button);
        loginButton.setText(loginButtonStr);

        //perform login on login button press
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    @Override
    /**
     * default option menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    /**
     * method when an login attempt is triggered by the button or the 'enter'-key
     *
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        usernameField.setError(null);
        passwordField.setError(null);

        // Store values at the time of the login attempt.
        MetaData metaData = new MetaData(usernameField.getText().toString());
        String passwordStr = passwordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password (null empty and  more than 3 characters)
        if (TextUtils.isEmpty(passwordStr)) {
            passwordField.setError(getString(R.string.error_field_required));
            focusView = passwordField;
            cancel = true;
        } else if (passwordStr.length() < 4) {
            passwordField.setError(getString(R.string.error_invalid_password));
            focusView = passwordField;
            cancel = true;
        }

        // Check for a valid username (shouldn't be empty)
        if (TextUtils.isEmpty(metaData.getUsername())) {
            usernameField.setError(getString(R.string.error_field_required));
            focusView = usernameField;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            mAuthTask = new HttpsRequest(this);
            mAuthTask.execute("");
        }
    }



    /**
     * Method that is triggered by background task completion
     */
    @Override
    public void onTaskComplete(String result) {
        Context context = getApplicationContext();
        CharSequence text = result;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}