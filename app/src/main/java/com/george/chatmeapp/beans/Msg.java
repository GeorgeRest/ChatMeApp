package com.george.chatmeapp.beans;

public class Msg {
    public static final int MSG_RECEIVE = 0;
    public static final int MSG_SEND = 1;
    private int type=1;
    private String content;
    private String time;
    private int LeftImageId;
    private int RightImageId;

    public int getRightImageId() {
        return RightImageId;
    }

    public void setRightImageId(int rightImageId) {
        RightImageId = rightImageId;
    }

    public int getLeftImageId() {
        return LeftImageId;
    }

    public void setLeftImageId(int leftImageId) {
        this.LeftImageId = leftImageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

