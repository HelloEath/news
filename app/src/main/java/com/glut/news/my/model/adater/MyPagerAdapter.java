package com.glut.news.my.model.adater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yy on 2018/1/26.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> fragment;
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragment = fragments;
    }
    @Override
    public int getCount() {
        return fragment.size();
    }
    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }
}
