package com.orhanobut.dialogplus;

import android.content.Context;
import android.view.View;
import android.widget.SimpleAdapter;

/**
 * Author: duanlei
 * Date: 2015-05-23
 */
public class DialogUtils {

    /**
     * 警告
     * @param context
     * @param holder
     * @param clickListener
     * @param gravity
     * @param header
     */
    public static void showDelDialog(Context context, Holder holder, OnClickListener clickListener, DialogPlus.Gravity gravity, View header) {
        final DialogPlus dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .setHeader(header)
                .create();
        dialog.show();
    }

    /**
     * 没有head view的弹窗
     * @param context
     * @param holder
     * @param clickListener
     * @param gravity
     */
    public static void showNoHeaderDialog(Context context, Holder holder, OnClickListener clickListener, DialogPlus.Gravity gravity) {
        final DialogPlus dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setBackgroundColorResourceId(R.color.default_background_color)
                .setOnClickListener(clickListener)
                .create();
        dialog.show();
    }

    /**
     * 列表
     * @param context
     * @param holder
     * @param clickListener
     * @param gravity
     * @param header
     */
    public static void showDelDialogList(Context context, Holder holder, SimpleAdapter adapter, OnItemClickListener clickListener, DialogPlus.Gravity gravity, View header) {
        final DialogPlus dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnItemClickListener(clickListener)
                .setHeader(header)
                .create();
        dialog.show();
    }

    public static void showDelDialogList(Context context, Holder holder, SimpleAdapter adapter, OnItemClickListener clickListener, DialogPlus.Gravity gravity) {
        final DialogPlus dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnItemClickListener(clickListener)
                .create();
        dialog.show();
    }


    /**
     * 书架书本选择
     * @param context
     * @param holder
     */
    public static void showDialogViewPager(Context context, Holder holder) {
        final DialogPlus dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.BOTTOM)
                .create();
        dialog.show();
    }



}
