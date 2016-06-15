package com.apec.android.views.utils;

import com.apec.android.domain.entities.user.User;

import org.litepal.crud.DataSupport;

/**
 * Created by duanlei on 2016/6/15.
 */
public class UserUtil {

    //获取用户信息
    public static User getUser() {
        return DataSupport.findAll(User.class).get(0);
    }

}
