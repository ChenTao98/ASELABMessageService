package com.chentao.aselab.message.controller.request;

public class SentMessage {
    private Long userToSent;
    private String message;
    private String type;

    public Long getUserToSent() {
        return userToSent;
    }

    public void setUserToSent(Long userToSent) {
        this.userToSent = userToSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
