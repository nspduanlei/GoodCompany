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
        clear();
        message.saveThrows();
    }

    public static void clear() {
        DataSupport.deleteAll(Message.class);
    }

    public Message select() {
        List<Message> messages = DataSupport.findAll(Message.class);
        if (messages.size() > 0) {
            return DataSupport.findAll(Message.class).get(0);
        }
        return null;
    }
}
