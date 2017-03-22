package com.jimmified.search.authenticate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import com.jimmified.search.R;
import com.jimmified.search.main.MainActivity;
import com.jimmified.search.JimmifyApplication;
import com.jimmified.search.settings.SaveSharedPreference;
import com.jimmified.search.request.BasicCallback;
import com.jimmified.search.request.model.AuthenticateModel;
import com.jimmified.search.request.model.RenewTokenModel;
import retrofit2.Call;

public class AuthenticateActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.authenFormView) View mAuthenFormView;
    @BindView(R.id.authenProgressView) View mProgressView;
    @BindView(R.id.nameEditText) EditText mNameView;
    @BindView(R.id.passwordEditText) EditText mPasswordView;
    @BindView(R.id.authenButton) Button mAttemptAuthenButton;

    // Butterknife bindings
    @OnClick(R.id.authenButton) void authenButtonClick() {
        attemptAuth();
    }

    @OnEditorAction(R.id.passwordEditText)
    boolean onEditorAction(TextView v, int id, KeyEvent event) {
        if (id == EditorInfo.IME_ACTION_DONE) {
            attemptAuth();
            return true;
        } return false;
    }

    private Call<AuthenticateModel> authenticateCall = null;
    private Call<RenewTokenModel> renewTokenCall = null;

    private void startMain() {
        Intent intent = new Intent(AuthenticateActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authen);
        ButterKnife.bind(this);

//        getSupportActionBar().hide(); // hide action bar

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuth();
    }

    private void checkAuth() {
        final String token = SaveSharedPreference.getToken();
        if (!token.equals("")) {
            final RenewTokenModel renewTokenModel = new RenewTokenModel(token);
            renewTokenCall = JimmifyApplication.getJimmifyAPI().attemptRenewToken(renewTokenModel);

            renewTokenCall.enqueue(new BasicCallback<RenewTokenModel>() {
                @Override
                public void handleSuccess(RenewTokenModel responseModel) {
                    if (responseModel.getStatus()) {
                        SaveSharedPreference.setToken(responseModel.getToken());
                        startMain();
                    } else {
                        handleStatusError(0); // TODO: Fix
                    }
                }

                @Override
                public void handleConnectionError() {
                    JimmifyApplication.showServerConnectionToast();
                    startMain();
                }

                @Override
                public void handleStatusError(int responseCode) {
                    mPasswordView.requestFocus();
                    showProgress(false);
                }

                @Override
                public void onFinish() {
                    renewTokenCall = null;
                }
            });
        } else {
            showProgress(false);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid name, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptAuth() {
        if (authenticateCall != null) {
            return;
        }

        JimmifyApplication.hideKeyboard(this);

        // Reset errors.
        mNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            final AuthenticateModel authModel = new AuthenticateModel(name, password);
            authenticateCall = JimmifyApplication.getJimmifyAPI().attemptAuthentication(authModel);

            authenticateCall.enqueue(new BasicCallback<AuthenticateModel>() {
                @Override
                public void handleSuccess(AuthenticateModel responseModel) {
                    if (responseModel.getStatus()) {
                        SaveSharedPreference.setToken(responseModel.getToken());
                        startMain();
                    } else {
                        handleStatusError(0); // TODO: Fix
                    }
                }

                @Override
                public void handleConnectionError() {
                    JimmifyApplication.showServerConnectionToast();
                    showProgress(false);
                }

                @Override
                public void handleStatusError(int responseCode) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    showProgress(false);
                }

                @Override
                public void onFinish() {
                    authenticateCall = null;
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        showProgress(show, getResources().getInteger(android.R.integer.config_shortAnimTime));
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, final int animationTime) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            mAuthenFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAuthenFormView.animate().setDuration(animationTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAuthenFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(animationTime).alpha(
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
            mAuthenFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Do not allow user to 'back' into the feed.
     */
    @Override
    public void onBackPressed() { moveTaskToBack(true); }

}
