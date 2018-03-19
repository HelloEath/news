package com.glut.news.my.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.glut.news.R;
import com.glut.news.my.view.fragment.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/2/11.
 */
public class StarWithHistoryActivity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager mViewPager;
    private List<String> titles=new ArrayList<>();
    private List<Fragment> fs=new ArrayList<>();
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        initView();
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        tab= (TabLayout) findViewById(R.id.tab);
        mViewPager= (ViewPager) findViewById(R.id.viewger);
        toolbar.setTitle("");
      setSupportActionBar(toolbar);
        ActionBar a=getSupportActionBar();
        if (a!=null){
            a.setDisplayHomeAsUpEnabled(true);
            a.setHomeAsUpIndicator(R.drawable.back);
        }

        titles.add("我的收藏");
        titles.add("历史记录");
        fs.add(new HistoryFragment(titles.get(0)));
        fs.add(new HistoryFragment(titles.get(1)));
        tab.addTab(tab.newTab().setText(titles.get(0)));
        tab.addTab(tab.newTab().setText(titles.get(1)));
        SettingsFragmentAdater sft=new SettingsFragmentAdater(getSupportFragmentManager());
        mViewPager.setAdapter(sft);
        String type=getIntent().getStringExtra("type");
        if ("1".equals(type)){
            mViewPager.setCurrentItem(0);

        }else {

            mViewPager.setCurrentItem(1);

        }


        tab.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    private class SettingsFragmentAdater  extends FragmentPagerAdapter{
        public SettingsFragmentAdater(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fs.get(position);
        }

        @Override
        public int getCount() {
            return fs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
