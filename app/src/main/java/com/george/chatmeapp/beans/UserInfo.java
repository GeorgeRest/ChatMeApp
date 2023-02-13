package com.george.chatmeapp.beans;

import org.litepal.crud.LitePalSupport;

public class UserInfo extends LitePalSupport {
    private int id;
    private String userName;
    private String email;
    private String pw;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String useName) {
        this.userName = useName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
