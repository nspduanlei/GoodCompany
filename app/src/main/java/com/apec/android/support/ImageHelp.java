package com.apec.android.support;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by duanlei on 2016/5/27.
 */
public class ImageHelp {

    public static void display(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }

}
