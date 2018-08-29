package com.example.h2_12.youcookipay;

public class User {

    private String user_id;
    private String session_id;
    private String type;
    private String name;
    private String email;

    public User(String user_id, String session_id, String type, String name, String email) {
        this.user_id = user_id;
        this.session_id = session_id;
        this.type = type;
        this.name = name;
        this.email = email;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
