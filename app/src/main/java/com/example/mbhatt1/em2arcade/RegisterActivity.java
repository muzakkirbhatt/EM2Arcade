package com.example.mbhatt1.em2arcade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private EditText mPasswordView, mPasswordView2, mUsernameView, mEmailView;
    private View mProgressView;
    private View mLoginFormView;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseManager(this);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mPasswordView2 = findViewById(R.id.password2);
        Button mEmailSignInButton = findViewById(R.id.registerBtn);

        mLoginFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.progressBar);
    }


    public void attemptRegister(View view) {

        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordView2.setError(null);

        String username = mUsernameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Password does not match format");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!password2.equalsIgnoreCase(password)) {
            mPasswordView2.setError("Does not match entered password");
            focusView = mPasswordView2;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (db.findUsername(username, "username")) {
            mUsernameView.setError("Username is not available");
            focusView = mUsernameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (db.findUsername(email, "email")) {
            mEmailView.setError("Account with this email already exists");
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
//            showProgress(true);
            User user = new User(username, email, password, "0", "0", "0");
            db.registerUser(user);

            Snackbar.make(this.mPasswordView, "Registration was successful. Please login to continue", Snackbar.LENGTH_LONG).show();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(loginIntent);
        }

    }

    public void goBack(View view) {
        this.finish();
    }

    public void goLogin(View view) {
        Intent login = new Intent(this, LoginActivity.class);
        finish();
        startActivity(login);
    }

    private boolean isEmailValid(String email) {
        boolean hasAt = false;
        boolean hasDot = false;

        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);

            if (c == '@' && !hasDot) {
                hasAt = true;
            }

            if (c == '.' && hasAt) {
                hasDot = true;
            }
        }

        if (hasAt && hasDot) {
            return true;
        } else return false;
    }

    private boolean isPasswordValid(String password) {
        boolean hasLowerCase = false; // a to z
        boolean hasUpperCase = false; // A to Z
        boolean hasNumber = false; // 0 to 9
        boolean hasSpecial = false; // !, *, %, $, #, &, ?, ^, -, +

        if (password.length() > 12) {
            return false;
        } else if (password.length() < 6) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }

            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }

            if (Character.isDigit(c)) {
                hasNumber = true;
            }

            if (c == '!' || c == '*' || c == '%' || c == '$' || c == '#' || c == '&' || c == '?' || c == '^' || c == '-' || c == '+') {
                hasSpecial = true;
            }
        }

        if (!hasLowerCase) {
            return false;
        } else if (!hasUpperCase) {
            return false;
        } else if (!hasNumber) {
            return false;
        } else if (!hasSpecial) {
            return false;
        } else {
            return true;
        }
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
