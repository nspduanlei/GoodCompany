package com.apec.android.support;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.apec.android.R;
import com.apec.android.views.activities.ShowUserDataActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by duanlei on 2016/5/27.
 */
public class ImageHelp {

    public static void display(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                //.placeholder(R.drawable.test)
                .error(R.drawable.test)
                .into(imageView);
    }

    public static void displayCircle(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .transform(new CircleTransform())
                .into(imageView);
    }


    public static void displayRound(Context context, int radius, int margin, String url, ImageView imageView) {
        Transformation transformation = new RoundedCornersTransformation(radius, margin);
        Picasso.with(context)
                .load(url)
                .transform(transformation)
                .into(imageView);
    }

    public static void displayRoundLocal(Context context, int radius, int margin, int resId, ImageView imageView) {
        Transformation transformation = new RoundedCornersTransformation(radius, margin);
        Picasso.with(context)
                .load(resId)
                .transform(transformation)
                .into(imageView);
    }

    public static void displayLocal(Context context, int id, ImageView imageView) {
        Picasso.with(context)
                .load(id)
                //.placeholder(R.drawable.test)
                .error(R.drawable.test)
                .into(imageView);
    }

    public static void displayLocalFile(Context context, File file, ImageView imageView) {
        Picasso.with(context)
                .load(file)
                //.placeholder(R.drawable.test)
                .error(R.drawable.test)
                .into(imageView);
    }

    public static void displayLocalFileCircle(Context context, File file, ImageView imageView) {
        Picasso.with(context)
                .load(file)
                //.placeholder(R.drawable.test)
                .error(R.drawable.test)
                .transform(new CircleTransform())
                .into(imageView);
    }

    static class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

}
