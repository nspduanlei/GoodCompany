package com.apec.android.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.apec.android.views.fragments.GoodsFragment;

/**
 * Created by duanlei on 2016/6/4.
 */
public class GoodsCAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = new String[]{"糖品", "米品", "油品", "面品", "调味品","糖品", "米品", "油品", "面品", "调味品"};
    protected static final int[] IDS = new int[] {
            12, 13, 11, 15, 46, 12, 13, 11, 15, 46
    };

    public GoodsCAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GoodsFragment.newInstance(IDS[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length].toUpperCase();
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }
}
