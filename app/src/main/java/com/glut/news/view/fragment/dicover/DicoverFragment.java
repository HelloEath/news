package com.glut.news.view.fragment.dicover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glut.news.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by yy on 2018/2/4.
 */

public class DicoverFragment extends android.support.v4.app.Fragment{

    private ViewPager mViewPager;
    private TabLayout mpager;
    private List<Fragment> flist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dicover, container, false);


        initView(view);
        return view;
    }

    private void initView(View view) {
        flist=new ArrayList<>();
        flist.add(new ZhiHuFragment());
        flist.add(new GuoKrFragment());
        flist.add(new DouBanFragment());
        mViewPager=view.findViewById(R.id.viewger);
        mpager=view.findViewById(R.id.tabs);


        mpager.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            private String titles[]={"知乎日报","果壳精选","豆瓣一刻"};
            @Override
            public Fragment getItem(int position) {
                return flist.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return titles[position];
            }

            @Override
            public int getCount() {
                return flist.size();
            }
        });
        mViewPager.setCurrentItem(0);



    }


}