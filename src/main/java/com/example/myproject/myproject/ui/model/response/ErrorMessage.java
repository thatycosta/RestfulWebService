package com.example.myproject.myproject.ui.model.response;

import java.util.Date;

public class ErrorMessage {
    private Date timestamp;
    private String message;

    public ErrorMessage(){

    }

    public ErrorMessage(Date timeStamp, String message){
        this.timestamp = timeStamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
