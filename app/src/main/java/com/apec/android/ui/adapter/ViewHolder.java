package com.apec.android.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.domain.goods.SkuAttrValue;
import com.apec.android.util.DensityUtils;
import com.apec.android.util.L;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Author: duanlei
 * Date: 2015-08-04
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, ViewGroup parent,
                      int layoutId, int position) {

        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);

        mConvertView.setTag(this);
        mContext = context;
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position
    ) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public int getMPosition() {
        return mPosition;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setTextColor(mContext.getResources().getColor(resId));
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView iv = getView(viewId);
        Picasso.with(mContext).load(url).into(iv);
        return this;
    }


    public ViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public ViewHolder setOnClickLister(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnCheckChangeLister(int checkId, CheckBox.OnCheckedChangeListener listener) {
        CheckBox checkBox = getView(checkId);
        checkBox.setOnCheckedChangeListener(listener);
        return this;
    }

    public ViewHolder setChecked(int checkId, boolean checked) {
        CheckBox checkBox = getView(checkId);
        checkBox.setChecked(checked);
        return this;
    }

    public ViewHolder setTextTow(int tv_add_count, String price, int count) {
        TextView tv = getView(tv_add_count);
        tv.setText(price + " X " + count);
        return this;
    }
}
