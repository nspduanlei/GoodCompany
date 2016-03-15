package com.apec.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.apec.android.ui.fragment.user.SelectCityFragment;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/15.
 */
public class PageViewAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mData;

    public PageViewAdapter(FragmentManager fm, ArrayList<String> data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return SelectCityFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position % mData.size()).toUpperCase();
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
