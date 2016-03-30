package com.example.guest.discussionforum.models;

import java.util.UUID;

/**
 * Created by Guest on 3/30/16.
 */
public class User {
    UUID uid;
    String name;

    public User(String name) {
        this.uid = UUID.randomUUID();
        this.name = name;
    }

    public UUID getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
