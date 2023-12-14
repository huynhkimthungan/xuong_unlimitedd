package com.example.unlimited_store.model;

public class Feedback {
    //idFeedback,content
    int idFeedback;
    String content;
    String username;

    public Feedback(int idFeedback, String content, String username) {
        this.idFeedback = idFeedback;
        this.content = content;
        this.username = username;
    }

    public Feedback(String content, String username) {
        this.content = content;
        this.username = username;
    }

    public Feedback() {
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
