package com.apec.android.domain.entities.user;

/**
 * Created by duanlei on 2016/6/15.
 */
public class Question {
    private int id;
    private String question;
    private String content;

    public Question(int id, String question, String content) {
        this.id = id;
        this.question = question;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
