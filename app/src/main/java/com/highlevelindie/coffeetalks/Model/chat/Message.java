package com.highlevelindie.coffeetalks.Model.chat;


import java.util.Date;

public class Message {
    private String name;
    private String message;
    private long time;

    public Message() {
    }

    public Message(String name, String message) {
        this.name = name;
        this.message = message;

        time = new Date().getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
