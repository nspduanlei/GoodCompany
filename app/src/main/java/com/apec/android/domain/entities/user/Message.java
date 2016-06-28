package com.apec.android.domain.entities.user;

/**
 * Created by duanlei on 2016/6/28.
 */
public class Message {
    private int id;
    private String title;
    private int time;
    private String content;
    private int type;
    private int gotoId;

    public Message(int id, String title, int time, String content) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGotoId() {
        return gotoId;
    }

    public void setGotoId(int gotoId) {
        this.gotoId = gotoId;
    }
}
