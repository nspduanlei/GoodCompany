package com.apec.android.domain.entities.user;

/**
 * Created by duanlei on 2016/6/14.
 */
public class MyMenu {
    private int resId;
    private String title;
    private boolean hasNew;
    private String context;
    private String hint;

    public MyMenu(int resId, String title, boolean hasNew, String context, String hint) {
        this.resId = resId;
        this.title = title;
        this.hasNew = hasNew;
        this.context = context;
        this.hint = hint;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasNew() {
        return hasNew;
    }

    public void setHasNew(boolean hasNew) {
        this.hasNew = hasNew;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
