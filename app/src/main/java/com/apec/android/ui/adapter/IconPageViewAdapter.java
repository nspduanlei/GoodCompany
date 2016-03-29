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
    //protected static final String[] CONTENT = new String[]{"糖品", "米品", "油品", "面品", "调味品"};
    protected static final int[] IDS = new int[] {
            12, 15, 11, 13, 46
    };
    protected static final int[] ICONS = new int[]{
            R.drawable.top_icon_selector_1,
            R.drawable.top_icon_selector_2,
            R.drawable.top_icon_selector_3,
            R.drawable.top_icon_selector_4,
            R.drawable.top_icon_selector_5
    };

    private int mCount = ICONS.length;

    public IconPageViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GoodsFragment.newInstance(IDS[position]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return CONTENT[position % CONTENT.length];
//    }

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