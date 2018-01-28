package com.glut.news.fragment.video;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.glut.news.R;
import com.glut.news.model.video.VideoTypeFragment;
import com.glut.news.view.customview.VideoTitleHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/1/22.
 */
public class VideoFragment extends android.support.v4.app.Fragment {

    private VideoTitleHorizontalScrollView titleScroll;
    private ViewPager viewPager;
    private LinearLayout addTitleLayout;
    private List<Fragment> fragmentList;
    private VideoAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_video,container,false);


        //初始化viewPager代码段
        this.titleScroll=(VideoTitleHorizontalScrollView)view.findViewById(R.id.myHorizeontal);
        this.viewPager=(ViewPager)view.findViewById(R.id.vPager);
        this.addTitleLayout=(LinearLayout)view.findViewById(R.id.news_fragment_main_layout_addtitle);
        initViewPager();
//添加标题
        this.addTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleScroll.addTextViewTitle("美女", getActivity());
                fragmentList.add(new VideoTypeFragment("http://news.163.com/mobile/"));
                pagerAdapter = new VideoAdapter(getFragmentManager(),fragmentList);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
                new Handler().post(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(10);
                            viewPager.setCurrentItem(fragmentList.size() - 1);//定位到最后一个ViewPager的item，并滑动菜单到最后一个刚添加的美女标题
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
//实现监听
        this.titleScroll.setOnItemClickListener(new VideoTitleHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                viewPager.setCurrentItem(pos);//设置标题栏TextView的点击事件
            }
        });

        return view;
    }


    /***
     * 初始化ViewPager
     */
    private void initViewPager(){
        this.fragmentList=new ArrayList<>();
        fragmentList.add(new VideoTypeFragment("http://news.163.com/mobile/"));
        fragmentList.add(new VideoTypeFragment("http://news.163.com/mobile/"));
        fragmentList.add(new VideoTypeFragment("http://3g.163.com/ntes/special/00340D52/3gtouchlist.html?docid=A9O2HAB6jiying&amp;title=%E8%BD%BB%E6%9D%BE%E4%B8%80%E5%88%BB"));
        fragmentList.add(new VideoTypeFragment("http://3g.163.com/touch/money/"));
        fragmentList.add(new VideoTypeFragment("http://3g.163.com/touch/tech/"));
        pagerAdapter=new VideoAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        this.viewPager.setAdapter(pagerAdapter);//设置adapter
        this.viewPager.setCurrentItem(0);//设置当前显示的页面
        this.viewPager.addOnPageChangeListener(new OnPagerChangeListener());//设置监听函数
    }

    private class OnPagerChangeListener implements ViewPager.OnPageChangeListener{
        //滑动中执行
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // titleScroll.scrollTo(200,0);

            Log.d("position",position+"");
            Log.d("positionOffset",positionOffset+"");
            Log.d("positionOffsetPixels",positionOffsetPixels+"");
        }
        //滑动到某个页面执行
        @Override
        public void onPageSelected(int position) {
            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();

            titleScroll.setPagerChangeListenerToTextView(position);//联动HorizontalScrollView

        }
        //状态改变时候调用
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class VideoAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;


      public   VideoAdapter(FragmentManager fm, List<Fragment> fragmentList){
          super(fm);
          this.fragmentList=fragmentList;

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
