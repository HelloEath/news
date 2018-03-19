package com.glut.news.video.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.glut.news.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/1/22.
 */
public class VideoFragment extends android.support.v4.app.Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout addTitleLayout;
    private List<Fragment> fragmentList;
    private VideoAdapter pagerAdapter;

    private List<String> titles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_video,container,false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        this.viewPager=(ViewPager)view.findViewById(R.id.vPager);
        tabLayout=view.findViewById(R.id.tabLayout);
        initViewPager();

    }


    /***
     * 初始化ViewPager
     */
    private void initViewPager(){
        this.fragmentList=new ArrayList<>();
        titles.add("推荐");
        titles.add("娱乐");
        titles.add("搞笑");
        titles.add("旅游");
        titles.add("财经");
        titles.add("美食");
        titles.add("汽车");

        for (int i=0;i<titles.size();i++){
            fragmentList.add(new VideoTypeFragment(titles.get(i)));

            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));

        }

       /* fragmentList.add(new VideoTypeFragment("http://news.163.com/mobile/"));
        fragmentList.add(new VideoTypeFragment("http://news.163.com/mobile/"));
        fragmentList.add(new VideoTypeFragment("http://3g.163.com/ntes/special/00340D52/3gtouchlist.html?docid=A9O2HAB6jiying&amp;title=%E8%BD%BB%E6%9D%BE%E4%B8%80%E5%88%BB"));
        fragmentList.add(new VideoTypeFragment("http://3g.163.com/touch/money/"));
        fragmentList.add(new VideoTypeFragment("http://3g.163.com/touch/tech/"));*/



        pagerAdapter=new VideoAdapter(getChildFragmentManager(), fragmentList);
        this.viewPager.setAdapter(pagerAdapter);//设置adapter
        this.viewPager.setCurrentItem(0);//设置当前显示的页面
        tabLayout.setupWithViewPager(viewPager);
    }



    private class VideoAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;


      public   VideoAdapter(FragmentManager fm, List<Fragment> fragmentList){
          super(fm);
          this.fragmentList=fragmentList;

      }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
