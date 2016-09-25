package kr.re.ec.talk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import kr.re.ec.talk.dto.AuthRequest;
import kr.re.ec.talk.dto.AuthResponse;
import kr.re.ec.talk.util.Constants;
import kr.re.ec.talk.util.GsonRequest;
import kr.re.ec.talk.util.LogUtil;
import kr.re.ec.talk.util.PrefUtil;
import kr.re.ec.talk.util.RequestController;

/**
 * A sign-in screen that offers sign-in via token.
 * Created by slhyv on 9/21/2016.
 */
public class SignInActivity extends AppCompatActivity {
    private static final String TAG = SignInActivity.class.getSimpleName();

    private Context mContext = null;

    // UI references.
    private EditText mEtToken;
    private View mProgressView;
    private View mSignInFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //check preferences
        if(!Constants.MetaInfo.getMyToken().equals("")) { //if have valid values
            startActivity(new Intent(mContext, ChattingActivity.class));
            finish(); //TODO: do validation
        }

        // Set up the sign-in form.
        setContentView(R.layout.activity_sign_in);

        mEtToken = (EditText) findViewById(R.id.et_token);
        mEtToken.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.sign_in || id == EditorInfo.IME_NULL) {
                    attemptSignIn();
                    return true;
            }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.token_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();
            }
        });

        mSignInFormView = findViewById(R.id.sign_in_form);
        mProgressView = findViewById(R.id.sign_in_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the sign-in form.
     * If there are form errors (invalid fields, etc.), the
     * errors are presented and no actual sign-in attempt is made.
     */
    private void attemptSignIn() {

        // Reset errors.
        mEtToken.setError(null);

        // Store values at the time of the sign-in attempt.
        String token = mEtToken.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid token, if the user entered one.
        if (!TextUtils.isEmpty(token) && !isTokenFormatValid(token)) {
            mEtToken.setError(getString(R.string.error_invalid_token));
            focusView = mEtToken;
            cancel = true;
        }

        if (cancel) {
            LogUtil.e(TAG, "err");
            focusView.requestFocus();
        } else {
            // Show a progress spinner
            showProgress(true);

            //request auth
            requestAuth(mEtToken.getText().toString());
        }
    }

    private void requestAuth(String token) {
        //make param
        final AuthRequest requestParam = new AuthRequest();
        requestParam.setCode(Constants.Network.CODE_TYPE_AUTH);
        requestParam.setToken(token);
        if("".equals(Constants.MetaInfo.getMyDeviceId())) {
            LogUtil.e(TAG, "no device id!!");
            //TODO: register gcm
//            throw new Exception("no deviceId registered");
        }
        requestParam.setDeviceId(Constants.MetaInfo.getMyDeviceId());
        LogUtil.v(TAG, "requestParam: " + requestParam);

        GsonRequest<AuthResponse> request = new GsonRequest<>(
                Request.Method.POST, Constants.Network.AUTH_URL,
                new Gson().toJson(requestParam),
                AuthResponse.class, null,
                new Response.Listener<AuthResponse>() {
                    @Override
                    public void onResponse(AuthResponse response) {
                        LogUtil.v(TAG, "response: " + response.toString());
                        showProgress(false);

                        if(response.getSuccess()) { //on Success
                            Constants.MetaInfo.setMyToken(requestParam.getToken());
                            Constants.MetaInfo.setMyNickname(response.getNickname());

                            startActivity(new Intent(mContext, ChattingActivity.class));
                            finish();
                         } else {
                            LogUtil.e(TAG, "error! = " + response.getMessage());
                            mEtToken.setError(getString(R.string.error_invalid_token));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.e(TAG, "error! = " + error.toString());

                mEtToken.setError(getString(R.string.error_invalid_token));
            }
        });

        RequestController.getInstance(this.getApplicationContext()).addToRequestQueue(request, TAG);
    }

    private boolean isTokenFormatValid(String token) {
        return token.contains("-");
    }

    /**
     * Shows the progress UI and hides the sign-in form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignInFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignInFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignInFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSignInFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

