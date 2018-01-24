package com.glut.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;



/**
 * Created by yy on 2018/1/22.
 */
public class HomeFragment extends android.support.v4.app.Fragment implements  RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
   private  List<Fragment> lf;
    private ViewPager viewpager;
    private  RadioButton btn_recomend;
    private  RadioButton btn_location;
    private  RadioButton btn_it;
    private  RadioButton btn_hot;
    private RadioButton btn_international;
    private RadioButton btn_fun;
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
        RadioGroup rg=v.findViewById(R.id.type_rg);
        rg.setOnCheckedChangeListener(this);
        btn_recomend=v.findViewById(R.id.recommended);
        btn_location=v.findViewById(R.id.location);
         btn_it=v.findViewById(R.id.it);
         btn_hot=v.findViewById(R.id.hot);
        btn_international=v.findViewById(R.id.international);
        btn_fun=v.findViewById(R.id.fun);
        viewpager=v.findViewById(R.id.viewger_home);

        lf.add(new Fragment1());
        lf.add(new Fragment1());
        lf.add(new Fragment1());
        lf.add(new Fragment1());
        lf.add(new Fragment1());
        lf.add(new Fragment1());
        viewpager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lf.get(position);
            }

            @Override
            public int getCount() {
                return lf.size();
            }
        });

        viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(this);
        btn_recomend.setChecked(true);
    }



    private void init(View v) {
        SearchView s=v.findViewById(R.id.search);
        ImageView i=v.findViewById(R.id.circlrimage);
        String url="https://upload.jianshu.io/users/upload_avatars/2581696/a4b480b2987d.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/120/h/120";
        Glide.with(getContext()).load(R.drawable.logo).transform(new CircleImage(getContext())).into(i);
s.setOnClickSearch(new ICallBack() {
    @Override
    public void SearchAciton(String string) {
        Toast.makeText(getContext(),"wosdd",Toast.LENGTH_SHORT).show();
    }
});


    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.recommended:
                viewpager.setCurrentItem(0);
                break;
            case R.id.location:
                viewpager.setCurrentItem(1);
                break;
            case R.id.it:
                viewpager.setCurrentItem(2);
                break;

            case R.id.hot:
                viewpager.setCurrentItem(3);
                break;
            case R.id.international:
                viewpager.setCurrentItem(4);
                break;
            case R.id.fun:
                viewpager.setCurrentItem(5);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (state==2){
            switch (viewpager.getCurrentItem()){
                case 0:
                    btn_recomend.setChecked(true);

                    break;
                case 1:
                    btn_location.setChecked(true);
                    break;
                case 2:
                    btn_it.setChecked(true);
                    break;
                case 3:
                    btn_hot.setChecked(true);
                    break;
                case 4:
                    btn_international.setChecked(true);
                    break;
                case 5:
                    btn_fun.setChecked(true);
                    break;


            }

        }
    }


}
