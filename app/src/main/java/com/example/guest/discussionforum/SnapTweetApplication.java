package com.example.guest.discussionforum;

import android.app.Application;
import com.firebase.client.Firebase;

/**
 * Created by Guest on 3/28/16.
 */
public class SnapTweetApplication extends Application {
    private static SnapTweetApplication app;
    private Firebase mFirebaseRef;

    public static SnapTweetApplication getAppInstance() {
        return app;
    }

    public Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase(this.getString(R.string.firebase_url));
    }
}