package com.apec.android.views.utils;

import com.apec.android.domain.entities.user.Message;
import com.apec.android.domain.entities.user.User;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by duanlei on 2016/7/6.
 */
public class MessageUtils {

    public void add(Message message) {
        message.saveThrows();
    }

    public static void clear() {
        DataSupport.deleteAll(Message.class);
    }

    public List<Message> select() {
        List<Message> messages = DataSupport.order("id desc").find(Message.class);
        //List<Message> messages = DataSupport.findAll(Message.class);
        return messages;
    }
}
