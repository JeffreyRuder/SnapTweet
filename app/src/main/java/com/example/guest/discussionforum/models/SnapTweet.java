package com.example.guest.discussionforum.models;

import android.content.SharedPreferences;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.SnapTweetApplication;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Guest on 3/30/16.
 */
public class SnapTweet {
    Date timeStamp;
    String content;
    String userName;
    String ref;
    SharedPreferences mSharedPreferences;

    public SnapTweet() {}


    public SnapTweet(String content, String currentUserName) {
        this.timeStamp = new Date();
        this.content = content;
        this.userName = currentUserName;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void save() {
        Firebase ref = SnapTweetApplication.getAppInstance().getFirebaseRef();
        Firebase snapTweetRef = ref.child("snaptweets").push();
        this.setRef(snapTweetRef.toString());
        snapTweetRef.setValue(this);
    }
}
