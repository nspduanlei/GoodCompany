package com.apec.android.mvp.views;

import com.apec.android.domain.entities.user.User;

/**
 * Created by duanlei on 2016/5/11.
 */
public interface LoginView extends View {
    void getVerCodeReceived();
    void bindUser(User user);
    void completeData();
    void verCodeError();
}
