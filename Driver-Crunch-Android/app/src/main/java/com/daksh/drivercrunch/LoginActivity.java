package com.daksh.drivercrunch;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.daksh.drivercrunch.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by daksh on 17-Jun-17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST_CODE = 2125;
    private static final String TAG = "ASDASD";

    private ActivityLoginBinding b;
    private GoogleApiClient mGoogleApiClient;
    private Prefs prefs;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_login);
        prefs = new Prefs(this);

        if (prefs.isLoggedIn()) {
            doneLoggingIn();
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
                                Toast.makeText(LoginActivity.this,
                                        "Failed to connect using GoogleApiClient!", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        b.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient),
                        LOGIN_REQUEST_CODE);
            }
        });
    }

    private void doneLoggingIn() {
        if (prefs.isLocationSelected()) {
            startActivity(new Intent(this, PredictionsActivity.class));
        } else {
            startActivity(new Intent(this, MapsActivity.class));
        }
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == LOGIN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = result.getSignInAccount();

            prefs.setLoggedIn(true);
            doneLoggingIn();
            // TODO use account
        } else {
            prefs.setLoggedIn(false);
        }
    }
}
