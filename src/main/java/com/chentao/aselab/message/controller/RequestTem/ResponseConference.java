package com.chentao.aselab.message.controller.RequestTem;

public class ResponseConference {
    private Long id;
    private String fullName;
    private String topics;

    public ResponseConference() {
    }

    public ResponseConference(Long id, String fullName, String topics) {
        this.id = id;
        this.fullName = fullName;
        this.topics = topics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
}
