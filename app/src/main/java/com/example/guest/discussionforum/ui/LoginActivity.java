package com.example.guest.discussionforum.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.SnapTweetApplication;
import com.example.guest.discussionforum.models.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.passwordLoginButton) Button mPasswordLoginButton;
    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.registerButton) Button mRegisterButton;

    private Firebase mFirebaseRef;
    private ProgressDialog mAuthProgressDialog;
    private Firebase.AuthResultHandler mAuthResultHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mFirebaseRef = SnapTweetApplication.getAppInstance().getFirebaseRef();

        mPasswordLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        initializeProgressDialog();
        initializeAuthResultHandler();

    }



    @Override
    public void onClick(View v) {
        if (v == mPasswordLoginButton) {
            loginWithPassword();
        }
        if (v == mRegisterButton) {
            registerNewUser();
        }
    }

    public void loginWithPassword(){
        mAuthProgressDialog.show();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        mFirebaseRef.authWithPassword(email, password, mAuthResultHandler);
    }

    public void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NO_HISTORY);
        mAuthProgressDialog.dismiss();
        startActivity(intent);
    }

    public void registerNewUser() {
        mAuthProgressDialog.show();
        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        mFirebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                User newUser = new User(email);
//                saveUserToFirebase(newUser);
                mFirebaseRef.authWithPassword(email, password, mAuthResultHandler);
            }

            @Override
            public void onError (FirebaseError firebaseError) {
                Log.d("Registration error", firebaseError.toString());
            }
        });
    }

//    public void saveUserToFirebase(User newUser) {
//        mFirebaseRef.child("users").child(newUser.getName()).setValue(newUser);
//    }

    private void initializeProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(true);
    }

    private void initializeAuthResultHandler() {
        mAuthResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {

                //save user to database
                Map<String, String> map = new HashMap<>();
                map.put("provider", authData.getProvider());

                if (authData.getProviderData().containsKey("email")) {
                    map.put("email", authData.getProviderData().get("email").toString());
                }

                mFirebaseRef.child("users").child(authData.getUid()).setValue(map);


                if (authData.getProvider().equals("password")) {
                    mAuthProgressDialog.hide();
                    goToMainActivity();
                }
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mAuthProgressDialog.hide();
                showErrorDialog(firebaseError.toString());
            }
        };
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
