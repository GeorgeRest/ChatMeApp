package com.george.chatmeapp.beans;

import java.io.Serializable;

public class UserItem implements Serializable {
    private String userName;
    private String content;
    private String time;
    private int imageId;
    private int isTop;
    private boolean aSwitch;
    public UserItem(){

    }

    public UserItem(String userName, String content, String time, int imageId, int isTop) {
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.imageId = imageId;
        this.isTop = isTop;
    }

    public boolean isaSwitch() {
        return aSwitch;
    }

    public void setaSwitch(boolean aSwitch) {
        this.aSwitch = aSwitch;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
