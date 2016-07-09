package com.apec.android.support.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.apec.android.views.activities.LoginActivity;

import java.util.Date;

/**
 * Author: duanlei
 * Date: 2015-10-14
 * 接收短信，获取短信内容
 */
public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            for (Object pdu : pdus) {
                //创建一个短信
                SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdu);

                //获取发送的手机号
                String address = msg.getOriginatingAddress();

                //获取短信内容
                String body = msg.getMessageBody();

                //获取短信的时间
                Date date = new Date(msg.getTimestampMillis());

                String test = address.substring(address.length() - 4);

                if (address.substring(address.length() - 4).equals("5310")) {

                    String vCode = body.substring(body.indexOf("为") + 1,
                            body.length()-1);

                    //发广播自动填充验证码
                    Intent mIntent = new Intent(LoginActivity.ACTION_OBTAIN_SMS);
                    mIntent.putExtra("vCode", vCode);
                    context.sendBroadcast(mIntent);
                }
            }
        }

        //中断手机接收操作
        abortBroadcast();
    }
}
