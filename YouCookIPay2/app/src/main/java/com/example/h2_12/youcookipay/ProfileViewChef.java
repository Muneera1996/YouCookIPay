
package com.example.h2_12.youcookipay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileViewChef {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("session_status")
    @Expose
    private Boolean sessionStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(Boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
