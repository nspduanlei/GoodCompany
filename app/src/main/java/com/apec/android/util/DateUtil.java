package com.apec.android.util;

import android.annotation.SuppressLint;
import android.text.format.Time;

import com.apec.android.R;
import com.apec.android.app.MyApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author duanlei 说明：对日期格式的格式化与转换操作等一系列操作
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    private static int MILL_MIN = 1000 * 60;
    private static int MILL_HOUR = MILL_MIN * 60;
    private static int MILL_DAY = MILL_HOUR * 24;

    private static String JUST_NOW = MyApplication.getInstance().getString(
            R.string.justnow);
    private static String MIN = MyApplication.getInstance().getString(
            R.string.min);
    private static String HOUR = MyApplication.getInstance().getString(
            R.string.hour);
    private static String DAY = MyApplication.getInstance().getString(
            R.string.day);
    private static String MONTH = MyApplication.getInstance().getString(
            R.string.month);
    private static String YEAR = MyApplication.getInstance().getString(
            R.string.year);

    private static String YESTER_DAY = MyApplication.getInstance()
            .getString(R.string.yesterday);
    private static String THE_DAY_BEFORE_YESTER_DAY = MyApplication
            .getInstance().getString(R.string.the_day_before_yesterday);
    private static String TODAY = MyApplication.getInstance().getString(
            R.string.today);

    private static String DATE_FORMAT = MyApplication.getInstance()
            .getString(R.string.date_format);
    private static String YEAR_FORMAT = MyApplication.getInstance()
            .getString(R.string.year_format);

    private static Calendar msgCalendar = null;

    public static Calendar getMsgCalendar() {
        return msgCalendar;
    }

    private static SimpleDateFormat dayFormat = null;

    public static SimpleDateFormat getDayFormat() {
        return dayFormat;
    }

    private static SimpleDateFormat dateFormat = null;

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    private static SimpleDateFormat yearFormat = null;

    public static SimpleDateFormat getYearFormat() {
        return yearFormat;
    }

    /**
     * 1s==1000ms
     */
    private final static int TIME_MILLISECONDS = 1000;
    /**
     * 时间中的分、秒最大值均为60
     */
    private final static int TIME_NUMBERS = 60;
    /**
     * 时间中的小时最大值
     */
    private final static int TIME_HOURSES = 24;
    /**
     * 格式化日期的标准字符串
     */
    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
     *
     * @param strFormat 格式定义 如：yyyy-MM-dd HH:mm:ss
     * @param dateValue 日期对象
     * @return
     */
    public static Date stringToDate(String strFormat, String dateValue) {
        if (dateValue == null)
            return null;
        if (strFormat == null)
            strFormat = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(dateValue);
        } catch (ParseException pe) {
            newDate = null;
        }
        return newDate;
    }

    /**
     * 将总秒数转换为时分秒表达形式
     *
     * @param seconds 任意秒数
     * @return %s小时%s分%s秒
     */
    public static String formatTime(long seconds) {

        long hh = seconds / TIME_NUMBERS / TIME_NUMBERS;
        long mm = (seconds - hh * TIME_NUMBERS * TIME_NUMBERS) > 0 ? (seconds - hh
                * TIME_NUMBERS * TIME_NUMBERS)
                / TIME_NUMBERS
                : 0;
        long ss = seconds < TIME_NUMBERS ? seconds : seconds % TIME_NUMBERS;

        return (hh == 0 ? "" : (hh < 10 ? "0" + hh : hh) + "小时")
                + (mm == 0 ? "" : (mm < 10 ? "0" + mm : mm) + "分")
                + (ss == 0 ? "" : (ss < 10 ? "0" + ss : ss) + "秒");
    }

    /**
     * 将总秒数转换为时分秒表达形式
     *
     * @param seconds 任意秒数
     * @return mm:ss
     */
    public static String formatTimeRecorder(long seconds) {

        long hh = seconds / TIME_NUMBERS / TIME_NUMBERS;
        long mm = (seconds - hh * TIME_NUMBERS * TIME_NUMBERS) > 0 ? (seconds - hh
                * TIME_NUMBERS * TIME_NUMBERS)
                / TIME_NUMBERS
                : 0;
        long ss = seconds < TIME_NUMBERS ? seconds : seconds % TIME_NUMBERS;

        return (mm == 0 ? "" : (mm < 10 ? "0" + mm : mm) + ":")
                + (ss == 0 ? "" : (ss < 10 ? "0" + ss : ss) + "'");
    }

    /**
     * 日期转成字符串
     *
     * @param strFormat 格式定义 如：yyyy-MM-dd HH:mm:ss
     * @param date      日期字符串
     * @return
     */
    public static String dateToString(String strFormat, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        return dateFormat.format(date);
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String GetlocalTime() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d %H:%M:%S");
    }

    /**
     * 计算两个日期间隔天数
     *
     * @param begin
     * @param end
     * @return
     */
    public static int countDays(Date begin, Date end) {
        int days = 0;
        Calendar c_b = Calendar.getInstance();
        Calendar c_e = Calendar.getInstance();
        c_b.setTime(begin);
        c_e.setTime(end);
        while (c_b.before(c_e)) {
            days++;
            c_b.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }

    /**
     * 格式化时间
     * @param time_g 秒
     * @return
     */
    public static String getNearTime(String time_g) {

        Long ltime = Long.valueOf(time_g);
        if (ltime == 0) {
            return "";
        }
        ltime = ltime * 1000;
        Date date = new Date(ltime);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(date);

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();	//今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set( Calendar.HOUR_OF_DAY, 0);
        today.set( Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();	//昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
        yesterday.set( Calendar.HOUR_OF_DAY, 0);
        yesterday.set( Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if(current.after(today)){
            return "今天 "+time.split(" ")[1];
        }else if(current.before(today) && current.after(yesterday)){

            return "昨天 "+time.split(" ")[1];
        }else{
            int index = time.indexOf("-")+1;
            return time.substring(index, time.length());
        }
    }

    /**
     * 判断时间是否过期
     * @param time_g
     * @return
     */
    public static boolean isTimeout(String time_g) {
        Long ltime = Long.valueOf(time_g);
        ltime = ltime * 1000;
        Date date = new Date(ltime);
        Date current = new Date();
        return current.after(date);
    }

    /**
     * 获取倒计时的秒数
     *
     * @param time_g
     * @return
     */
    public static long getTimerCount(String time_g) {

        String time = StringUtils.getDataFormatStr(time_g, "yyyy-MM-dd HH:mm:ss");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;

        long sec = 0;
        try {
            one = df.parse(time); //后台返回时间
            two = new Date();     //当前时间

            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                return 0;
            } else {
                diff = time1 - time2;
            }

            sec = diff;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sec;
    }


    public static String getListTime(long time) {
        long now = System.currentTimeMillis();
        long msg = time * 1000;

        Calendar nowCalendar = Calendar.getInstance();

        if (msgCalendar == null)
            msgCalendar = Calendar.getInstance();

        getMsgCalendar().setTimeInMillis(msg);

        long calcMills = now - msg;

        long calSeconds = calcMills / 1000;

        if (calSeconds < 60) {
            return JUST_NOW;
        }

        long calMins = calSeconds / 60;

        if (calMins < 60) {
            return new StringBuilder().append(calMins).append(MIN).toString();
        }

        long calHours = calMins / 60;

        if (calHours < 24 && isSameDay(nowCalendar, msgCalendar)) {
            if (dayFormat == null)
                dayFormat = new SimpleDateFormat("HH:mm");

            String result = getDayFormat().format(msgCalendar.getTime());
            return new StringBuilder().append(TODAY).append(" ").append(result)
                    .toString();
        }

        long calDay = calHours / 24;

        if (calDay < 31) {
            if (isYesterDay(nowCalendar, msgCalendar)) {
                if (dayFormat == null)
                    dayFormat = new SimpleDateFormat("HH:mm");

                String result = getDayFormat().format(msgCalendar.getTime());
                return new StringBuilder(YESTER_DAY).append(" ").append(result)
                        .toString();

            } else if (isTheDayBeforeYesterDay(nowCalendar, msgCalendar)) {
                if (dayFormat == null)
                    dayFormat = new SimpleDateFormat("HH:mm");

                String result = getDayFormat().format(msgCalendar.getTime());
                return new StringBuilder(THE_DAY_BEFORE_YESTER_DAY).append(" ")
                        .append(result).toString();

            } else {
                if (dateFormat == null)
                    dateFormat = new SimpleDateFormat(DATE_FORMAT);

                String result = getDayFormat().format(msgCalendar.getTime());
                return result;
            }
        }

        long calMonth = calDay / 31;

        if (calMonth < 12) {
            if (dateFormat == null)
                dateFormat = new SimpleDateFormat(DATE_FORMAT);

            String result = getDateFormat().format(msgCalendar.getTime());
            return new StringBuilder().append(result).toString();

        }
        if (yearFormat == null)
            yearFormat = new SimpleDateFormat(YEAR_FORMAT);
        String result = getYearFormat().format(msgCalendar.getTime());
        return new StringBuilder().append(result).toString();

    }

    private static boolean isSameDay(Calendar now, Calendar msg) {
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int msgDay = msg.get(Calendar.DAY_OF_YEAR);

        return nowDay == msgDay;
    }

    private static boolean isYesterDay(Calendar now, Calendar msg) {
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int msgDay = msg.get(Calendar.DAY_OF_YEAR);

        return (nowDay - msgDay) == 1;
    }

    private static boolean isTheDayBeforeYesterDay(Calendar now, Calendar msg) {
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int msgDay = msg.get(Calendar.DAY_OF_YEAR);

        return (nowDay - msgDay) == 2;
    }


    //获取当月的 天数
    public static int getCurrentMonthDay(Date date) {
        Calendar a = Calendar.getInstance();

        a.setTime(date);

        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
