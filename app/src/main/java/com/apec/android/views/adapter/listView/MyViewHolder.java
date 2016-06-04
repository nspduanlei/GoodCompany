package com.apec.android.views.adapter.listView;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Author: duanlei
 * Date: 2015-08-04
 */
public class MyViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;

    public MyViewHolder(Context context, ViewGroup parent,
                        int layoutId, int position) {

        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);

        mConvertView.setTag(this);
        mContext = context;
    }

    public static MyViewHolder get(Context context, View convertView,
                                   ViewGroup parent, int layoutId, int position
    ) {
        if (convertView == null) {
            return new MyViewHolder(context, parent, layoutId, position);
        } else {
            MyViewHolder holder = (MyViewHolder) convertView.getTag();
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
    public MyViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public MyViewHolder setTextColor(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setTextColor(mContext.getResources().getColor(resId));
        return this;
    }

    public MyViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public MyViewHolder setImageUrl(int viewId, String url) {
        ImageView iv = getView(viewId);
        Picasso.with(mContext).load(url).into(iv);
        return this;
    }


    public MyViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public MyViewHolder setOnClickLister(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public MyViewHolder setOnCheckChangeLister(int checkId, CheckBox.OnCheckedChangeListener listener) {
        CheckBox checkBox = getView(checkId);
        checkBox.setOnCheckedChangeListener(listener);
        return this;
    }

    public MyViewHolder setOnCheckChangeListerRadio(int checkId, CheckBox.OnCheckedChangeListener listener) {
        RadioButton radioButton = getView(checkId);
        radioButton.setOnCheckedChangeListener(listener);
        return this;
    }

    public MyViewHolder setChecked(int checkId, boolean checked) {
        CheckBox checkBox = getView(checkId);
        checkBox.setChecked(checked);
        return this;
    }

    public MyViewHolder setSelected(int radioId, boolean selected) {
        RadioButton radioButton = getView(radioId);
        radioButton.setChecked(selected);
        return this;
    }


    public MyViewHolder setTextTow(int tv_add_count, String price, int count) {
        TextView tv = getView(tv_add_count);
        tv.setText(count + " x ￥" + price);
        return this;
    }

    public MyViewHolder setListView(int lv_sku, BaseAdapter itemAdapter) {
        ListView listView = getView(lv_sku);
        listView.setAdapter(itemAdapter);
        return this;
    }

    public MyViewHolder setListViewItemClick(int lv_sku,
                                             AdapterView.OnItemClickListener itemClickListener) {
        ListView listView = getView(lv_sku);
        listView.setOnItemClickListener(itemClickListener);
        return this;
    }

    public MyViewHolder setTextBackground(int tv_order_pro, int order_pro_bg_1) {
        TextView textView = getView(tv_order_pro);
        textView.setBackgroundResource(order_pro_bg_1);
        return this;
    }

    public MyViewHolder setTextChar(int tv_total_price, CharSequence chars) {
        TextView textView = getView(tv_total_price);
        textView.setText(chars);
        return this;
    }
}
