package com.chentao.aselab.message.controller.request;

import org.springframework.web.bind.annotation.RequestParam;

public class SentMessageRequestBody {
    private Long usernameOfChair;
    private Long conferenceId;
    private Long iDOfUserToBeSent;
    private String checkedOrAbolished;
    private String acceptOrReject;

    public String getCheckedOrAbolished() {
        return checkedOrAbolished;
    }

    public void setCheckedOrAbolished(String checkedOrAbolished) {
        this.checkedOrAbolished = checkedOrAbolished;
    }

    public String getAcceptOrReject() {
        return acceptOrReject;
    }

    public void setAcceptOrReject(String acceptOrReject) {
        this.acceptOrReject = acceptOrReject;
    }

    public Long getUsernameOfChair() {
        return usernameOfChair;
    }

    public void setUsernameOfChair(Long usernameOfChair) {
        this.usernameOfChair = usernameOfChair;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Long getiDOfUserToBeSent() {
        return iDOfUserToBeSent;
    }

    public void setiDOfUserToBeSent(Long iDOfUserToBeSent) {
        this.iDOfUserToBeSent = iDOfUserToBeSent;
    }
}
