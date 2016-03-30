package com.example.guest.discussionforum.models;

import com.example.guest.discussionforum.SnapTweetApplication;
import com.firebase.client.Firebase;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Guest on 3/30/16.
 */
public class SnapTweet {
    UUID messageId;
    Date timeStamp;
    String content;
    String userName;

    public SnapTweet(String content) {
        this.messageId = UUID.randomUUID();
        this.timeStamp = new Date();
        this.content = content;
        this.userName = getCurrentUserNameFromFirebase();
    }

    public UUID getSnapTweetId() {
        return messageId;
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

    public String getCurrentUserNameFromFirebase() {
        Firebase ref = SnapTweetApplication.getAppInstance().getFirebaseRef();
        return ref.child("users").child(ref.getAuth().getUid()).child("email").toString();
    }
}
