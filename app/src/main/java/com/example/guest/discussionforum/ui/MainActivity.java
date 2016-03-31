package com.example.guest.discussionforum.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.SnapTweetApplication;
import com.example.guest.discussionforum.adapters.FirebaseMessageRecyclerAdapter;
import com.example.guest.discussionforum.models.SnapTweet;
import com.example.guest.discussionforum.models.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.RecyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.userTextView) TextView mUserTextView;
    @Bind(R.id.submitButton) Button mSubmitButton;
    @Bind(R.id.contentEditText) EditText mContentEditText;

    private Firebase mFirebaseRef;
    private String mCurrentUserId;
    private User mCurrentUser;
    private Query mQuery;
    private FirebaseMessageRecyclerAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFirebaseRef = SnapTweetApplication.getAppInstance().getFirebaseRef();
        mSharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        checkForAuthenticatedUser();
        mSubmitButton.setOnClickListener(this);


    }

    private void checkForAuthenticatedUser() {
        if (mFirebaseRef.getAuth() != null) {
            mCurrentUserId = mFirebaseRef.getAuth().getUid();
            setupFirebaseQuery();
            setupRecyclerView();
            Query ref = mFirebaseRef.child("users/" + mCurrentUserId);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    mUserTextView.setText("Welcome " + user.getEmail());
                    mEditor.putString("email", user.getEmail()).commit();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } else {
            goToLoginActivity();
        }
    }

    private void setupFirebaseQuery(){
        mQuery = mFirebaseRef.child("snaptweets");
    }

    private void setupRecyclerView() {
        mAdapter = new FirebaseMessageRecyclerAdapter(mQuery, SnapTweet.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                this.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mFirebaseRef.unauth();
        goToLoginActivity();
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitButton) {
            if (mContentEditText.getText().toString().isEmpty()) {
                mContentEditText.setError("Please enter a SnapTweet!");
            } else {
                String message = mContentEditText.getText().toString().trim();
                SnapTweet snapTweet = new SnapTweet(message, mSharedPreferences.getString("email", "anonymous user"));
                snapTweet.save();
                mContentEditText.setText("");
            }
        }
    }
}
