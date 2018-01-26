package com.glut.news.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.glut.news.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.roughike.bottombar.TabSelectionInterceptor;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private List<Fragment> fragmentList;

private ViewPager v;
    private BottomBarTab nearby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        initBottomBar();


    }

    private void initViewPager() {
    fragmentList=new ArrayList<>();
        v= (ViewPager) findViewById(R.id.viewger);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new AddVideoFragment());
        fragmentList.add(new MyFragment());
        v.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            /**
             * 获取给定位置的项Id，用于生成Fragment名称
             *
             * @param position 给定的位置
             * @return 项Id
             */
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        //v.addOnAdapterChangeListener(new );

    }


    private void initBottomBar() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    // 选择指定 id 的标签
                    nearby = bottomBar.getTabWithId(R.id.tab_home);
                    v.setCurrentItem(0);
                    nearby.setBadgeCount(5);
                }
                if (tabId == R.id.tab_video) {
                    // 选择指定 id 的标签
                     nearby = bottomBar.getTabWithId(R.id.tab_video);
                    v.setCurrentItem(1);
                    nearby.setBadgeCount(5);
                }
                if (tabId == R.id.tab_add_video) {
                    // 选择指定 id 的标签
                    // nearby = bottomBar.getTabWithId(R.id.tab_add_video);
                    v.setCurrentItem(2);
                    //nearby.setBadgeCount(5);
                }
                if (tabId == R.id.tab_my) {
                    // 选择指定 id 的标签
                    // nearby = bottomBar.getTabWithId(R.id.tab_nearby);
                    v.setCurrentItem(3);
                    //nearby.setBadgeCount(5);
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    // 已经选择了这个标签，又点击一次。即重选。
                    nearby.removeBadge();
                }

                if (tabId == R.id.tab_video) {
                    // 已经选择了这个标签，又点击一次。即重选。
                    nearby.removeBadge();
                }

                if (tabId == R.id.tab_add_video) {
                    // 已经选择了这个标签，又点击一次。即重选。
                    nearby.removeBadge();
                }

                if (tabId == R.id.tab_my) {
                    // 已经选择了这个标签，又点击一次。即重选。
                    nearby.removeBadge();
                }
            }
        });

        bottomBar.setTabSelectionInterceptor(new TabSelectionInterceptor() {
            @Override
            public boolean shouldInterceptTabSelection(@IdRes int oldTabId, @IdRes int newTabId) {
                // 点击无效
              /*  if (newTabId == R.id.tab_restaurants ) {
                    // ......
                    // 返回 true 。代表：这里我处理了，你不用管了。
                    return true;
                }
*/
                return false;
            }
        });

    }
}