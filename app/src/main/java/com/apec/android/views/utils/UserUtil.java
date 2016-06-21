package com.apec.android.views.utils;

import com.apec.android.domain.entities.user.User;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by duanlei on 2016/6/15.
 */
public class UserUtil {

    //获取用户信息
    public static User getUser() {
        List<User> users = DataSupport.findAll(User.class);

        if (users.size() > 0) {
            return DataSupport.findAll(User.class).get(0);
        }

        return null;
    }

    public static void clear() {
        DataSupport.deleteAll(User.class);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public static void updateUser(User user) {
        user.update(user.getId());
    }
}
