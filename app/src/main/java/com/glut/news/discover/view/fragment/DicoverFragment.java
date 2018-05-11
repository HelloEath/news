package com.glut.news.discover.view.fragment;

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
import com.glut.news.common.view.customview.VideoTitleHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yy on 2018/2/4.
 */

public class DicoverFragment extends android.support.v4.app.Fragment{

    private ViewPager mViewPager;
    private VideoTitleHorizontalScrollView mpager;
    private List<Fragment> flist;
    private TabLayout tabLayout;
    private List<String> titles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dicover, container, false);
       /* //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/

        initView(view);
        return view;
    }

    private void initView(View view) {

        titles.add("知乎日报");
        titles.add("One轻阅");
        titles.add("每日开眼");
        titles.add("果壳精选");
        flist=new ArrayList<>();
        flist.add(new ZhiHuFragment());
        flist.add(new OneFragment());
        flist.add(new KaiYanFragment());
        flist.add(new GuoKrFragment());
        mViewPager=view.findViewById(R.id.dviewpager);
        mpager=view.findViewById(R.id.myHorizeontal);
        tabLayout= view.findViewById(R.id.tabLayout);

        //给tab添加title
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(3)));

FragmentPagerAdapter f=new FragmentPagerAdapter(getChildFragmentManager()) {

    @Override
    public Fragment getItem(int position) {
        return flist.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return flist.size();
    }
};
        mViewPager.setAdapter(f);
        mViewPager.setCurrentItem(0);

        mViewPager.setOffscreenPageLimit(3);

        //把tablayout和viewpager联系起来
        tabLayout.setupWithViewPager(mViewPager);
        //mViewPager.addOnPageChangeListener(new OnPagerChangeListener());//设置监听函数
/*

        mpager.setOnItemClickListener(new VideoTitleHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mViewPager.setCurrentItem(position);

            }
        });
*/

    }


    private class OnPagerChangeListener implements ViewPager.OnPageChangeListener{


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //标题下划线联动
        mpager.setPagerChangeListenerToTextView(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}