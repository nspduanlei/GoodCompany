package com.apec.android.util;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Some utility methods related with the String class.
 *
 * @author duanlei
 */
public class StringUtils {

    private static final String EMPTY_STRING = "";

    private StringUtils() {
        //Empty
    }

    public static boolean isNullOrEmpty(final String string) {
        return string == null || EMPTY_STRING.equals(string.trim());
    }

    /**
     * aes加密
     * @return
     * @throws Exception
     */
    public static String desEncrypt() throws Exception {
        try
        {
            String data = "2fbwW9+8vPId2/foafZq6Q==";
            String key = "1234567812345678";
            String iv = "1234567812345678";

            //byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            byte[] encrypted1 = Base64.decode(data, 0);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证邮箱输入是否合法
     *
     * @param strEmail
     * @return
     */
    public static String checkEmail(String strEmail) {
        if(isNullOrEmpty(strEmail)) {
            return "邮箱不能为空";
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(strEmail);
        if(!m.matches()) {
            return "邮箱格式不正确";
        }
        return "";
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static String checkMobile(String str) {
        if(isNullOrEmpty(str)) {
            return "手机号不能为空";
        }
        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(str);
        if(!matcher.matches()) {
            return "手机号格式不正确";
        }
        return "";
    }


    /**
     * 验证注册的密码是否合法
     *
     * @param password
     * @return
     */
    public static String checkPassword(String password) {
        if (password.length() < 6 || password.length() > 16) {
            return "密码6到16个字符喔!";
        }
        String strPattern = "[0-9A-Za-z]*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(password);
        if (!m.matches()) {
            return "密码只能由数字和字母组成喔!";
        }
        return "";
    }

    /**
     * MD5加密
     *
     * @param secret_key
     * @return
     */
    public static String createSign(String secret_key) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(secret_key.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (byte aByteArray : byteArray) {
            if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & aByteArray));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
        }
        return md5StrBuff.toString();
    }

    /**
     *
     * 将秒数转化成指定格式的时间字符串
     * MM月dd日 HH:mm
     * yyyy-MM-dd
     * @param time  秒数
     * @param formatStr
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDataFormatStr(String time, String formatStr) {
        Long ltime;
        try{
            ltime = Long.valueOf(time);
        } catch (Exception e) {
            return time;
        }

        if (ltime == 0) {
            return "";
        }
        ltime = ltime * 1000;

        Date d = new Date(ltime);
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        d.getTime();
        return sdf.format(d);
    }

    private static final int NICKNAME_LEN = 14;

    //nickname填充
    public static void setNickname(TextView textView, String nickname) {
        if (nickname.length() > NICKNAME_LEN) {
            textView.setText(nickname.substring(0, NICKNAME_LEN));
        } else {
            textView.setText(nickname);
        }
    }

    public static String checkUserName(String username) {
        if (username.length() < 1) {
            return "用户名长度不能为空";
        }

        if (username.length() > NICKNAME_LEN) {
            return "用户名长度不能超过7位";
        }

        return "";
    }

    public static String checkShopName(String shopName) {
        if (shopName.length() < 1) {
            return "店铺名长度不能为空";
        }

        if (shopName.length() > NICKNAME_LEN) {
            return "店铺名长度不能超过7位";
        }
        return "";
    }
}
