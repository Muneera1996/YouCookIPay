package com.example.h2_12.youcookipay;

public class User {

    private String user_id;
    private String session_id;

    public User(String user_id, String session_id) {
        this.user_id = user_id;
        this.session_id = session_id;
    }
    public User(){

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
