package com.glut.news.view.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.glut.news.R;
import com.glut.news.view.CircleImage;
import com.glut.news.view.searchview.ICallBack;
import com.glut.news.view.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;






/**
 * Created by yy on 2018/1/22.
 */
public class HomeFragment extends android.support.v4.app.Fragment {
   private  List<Fragment> lf;
    private ViewPager viewpager;

    private TabLayout tabLayout;
    private List<String> titles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        initViewPager(v);

        init(v);
        return v;

    }

    private void initViewPager(View v) {
        lf=new ArrayList<>();
        tabLayout=v.findViewById(R.id.tabLayout);

        titles.add("推荐");
        titles.add("当前");
        titles.add("热点");
        titles.add("社会");
        titles.add("科技");
        titles.add("娱乐");
        viewpager=v.findViewById(R.id.viewger_home);

        tabLayout.addTab(tabLayout.newTab().setText("推荐"));
        tabLayout.addTab(tabLayout.newTab().setText("当前"));
        tabLayout.addTab(tabLayout.newTab().setText("热点"));
        tabLayout.addTab(tabLayout.newTab().setText("社会"));
        tabLayout.addTab(tabLayout.newTab().setText("科技"));
        tabLayout.addTab(tabLayout.newTab().setText("娱乐"));
        lf.add(new HomeTypeFragment());
        lf.add(new HomeTypeFragment());
        lf.add(new HomeTypeFragment());
        lf.add(new HomeTypeFragment());
        lf.add(new HomeTypeFragment());
        lf.add(new HomeTypeFragment());
        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lf.get(position);
            }

            @Override
            public int getCount() {
                return lf.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });

        viewpager.setCurrentItem(0);
       tabLayout.setupWithViewPager(viewpager);
    }



    private void init(View v) {
        SearchView s=v.findViewById(R.id.search);
        ImageView i=v.findViewById(R.id.circlrimage);
        String url="https://upload.jianshu.io/users/upload_avatars/2581696/a4b480b2987d.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/120/h/120";
        Glide.with(getContext()).load(R.drawable.logo).transform(new CircleImage(getContext())).into(i);
     s.setOnClickSearch(new ICallBack() {
         @Override
         public void SearchAciton(String string) {

         }
     });


    }






}
