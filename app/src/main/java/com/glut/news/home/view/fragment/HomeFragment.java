package com.glut.news.home.view.fragment;

import android.content.Intent;
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
import com.bumptech.glide.request.RequestOptions;
import com.glut.news.R;
import com.glut.news.my.view.activity.SettingActivity;
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
    private ImageView btn_logo;
    private TabLayout tabLayout;
    private ImageView mAddVideo;
    private SearchView searchView;
    private ImageView mLogo;
    private List<String> titles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        initView(v);

        initData(v);
        return v;

    }

    private void initView(View v) {
        lf=new ArrayList<>();
        tabLayout=v.findViewById(R.id.tabLayout);
        mAddVideo=v.findViewById(R.id.addVideo);
        searchView=v.findViewById(R.id.search);
        mLogo=v.findViewById(R.id.circlrimage);
        viewpager=v.findViewById(R.id.viewger_home);

    }



    private void initData(View v) {
        titles.add("推荐");
        titles.add("互联网");
        titles.add("军事");
        titles.add("旅游");
        titles.add("国内");
        titles.add("科技");
        String url="https://upload.jianshu.io/users/upload_avatars/2581696/a4b480b2987d.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/120/h/120";
        Glide.with(getContext()).load(R.drawable.logo).apply(
                RequestOptions.circleCropTransform()).into(mLogo);
        Glide.with(getContext()).load(R.drawable.add).into(mAddVideo);

        for (int i=0;i<titles.size();i++){

            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
            lf.add(new HomeTypeFragment(titles.get(i)));
        }



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

        searchView.setOnClickSearch(new ICallBack() {
         @Override
         public void SearchAciton(String string) {

         }
     });

        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
            }
        });
        mAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewpager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewpager);
    }






}
