package com.apec.android.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.apec.android.views.fragments.GoodsFragment;
import com.apec.android.views.fragments.OrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlei on 2016/6/4.
 */
public class GoodsCAdapter extends FragmentPagerAdapter {

    public final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public GoodsCAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof GoodsFragment) {
            ((GoodsFragment) object).updateData();
        } else if (object instanceof OrderFragment) {
            ((OrderFragment) object).updateData();
        }
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
