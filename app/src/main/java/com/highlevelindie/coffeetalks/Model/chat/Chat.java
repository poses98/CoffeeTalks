package com.highlevelindie.coffeetalks.Model.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat {
    private String title;
    private String lastmsg;
    private long timestamp;



    public Chat() {
        timestamp = new Date().getTime();
    }

    public Chat(String title, String lastmsg) {
        this.title = title;
        this.lastmsg = lastmsg;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getlastmsg() {
        return lastmsg;
    }

    public void setlastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public void AddMessage(){

    }

}
