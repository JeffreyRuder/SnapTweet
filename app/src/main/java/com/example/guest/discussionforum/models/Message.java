package com.example.guest.discussionforum.models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Guest on 3/30/16.
 */
public class Message {
    UUID messageId;
    Date timeStamp;
    String content;

    public Message(String content) {
        this.messageId = UUID.randomUUID();
        this.timeStamp = new Date();
        this.content = content;
    }

    public UUID getMessageId() {

        return messageId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getContent() {
        return content;
    }
}
