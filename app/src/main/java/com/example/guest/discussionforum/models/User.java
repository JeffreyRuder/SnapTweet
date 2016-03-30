package com.example.guest.discussionforum.models;

import java.util.UUID;

/**
 * Created by Guest on 3/30/16.
 */
public class User {
    String email;
    String provider;

    public User() {}

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}


