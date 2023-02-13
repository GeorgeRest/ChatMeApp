package com.george.chatmeapp.beans;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class MessageList extends LitePalSupport {
    public static final int MSG_RECEIVE = 0;
    public static final int MSG_SEND = 1;
    private String userName;
    List<String> content = new ArrayList<>();
    private int type=1;
    int imageId;
    private String time;
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}