package com.apec.android.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.apec.android.R;
import com.apec.android.ui.fragment.goods.GoodsFragment;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * Created by duanlei on 2016/3/17.
 */
public class IconPageViewAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[]{"糖品", "米品", "油品", "面品", "调味品"};
    protected static final int[] ICONS = new int[]{
            R.drawable.sugar_icon,
            R.drawable.rice_icon,
            R.drawable.ail_icon,
            R.drawable.face_icon,
            R.drawable.condiment_icon
    };

    private int mCount = CONTENT.length;

    public IconPageViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GoodsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}