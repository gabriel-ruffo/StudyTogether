package com.example.gabriel.studytogether2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gabriel.studytogether2.dbMedium_package.DBMediumGetUser;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumInsertUser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    private GoogleApiClient mGoogleApiClient;
    private Toast mToast;
    private SignInButton sib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();

        GApiContainer gac = GApiContainer.getInstance();
        gac.setGApi(mGoogleApiClient);

        sib = (SignInButton) findViewById(R.id.sign_in_button);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    private boolean takeClicks = true;

    @Override
    public void onClick(View v) {
        //if (takeClicks) {
        //  takeClicks = false;
        //sib.setEnabled(false);
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
        //}
        /*
        Intent signInIntent = new Intent(this, MainActivity.class);
        startActivity(signInIntent);
        */
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private String email;

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();

            email = "" + acct.getEmail();

            DBMediumGetUser dbmgu = new DBMediumGetUser(this);

            dbmgu.getScheduleId(email);




            /*if(mToast != null){
                mToast.cancel();
            }

            mToast.makeText(this, "Gud s.i.: " + acct.getEmail(), Toast.LENGTH_LONG).show();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);*/
            /*
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
            */
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
            //takeClicks = true;
            //sib.setEnabled(true);
            if (mToast != null) {
                mToast.cancel();
            }

            mToast.makeText(this, "Bad sign in : " + result.getStatus().getStatusCode(), Toast.LENGTH_LONG).show();
        }
    }

    public void loadFinished(String schedule_id) {
        if (schedule_id.equals("null*Connect")) {
            //sib.setEnabled(true);
            //takeClicks = true;
            Toast.makeText(this, "Error occurred, try again", Toast.LENGTH_SHORT).show();
        } else if (schedule_id.length() > 0) {
            Intent signInIntent = new Intent(this, MainActivity.class);
            signInIntent.putExtra("SID", Integer.parseInt(schedule_id));
            signInIntent.putExtra("EMAIL", email);
            GlobalState gs = (GlobalState) getApplicationContext();
            gs.setGoogleApiClient(mGoogleApiClient);
            startActivity(signInIntent);
        } else {
            DBMediumInsertUser dbmiu = new DBMediumInsertUser(this);
            dbmiu.insertNewEmail(email);
        }

    }

    public void doneInsertingUser() {
        DBMediumGetUser dbmgu = new DBMediumGetUser(this);

        dbmgu.getScheduleId(email);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
