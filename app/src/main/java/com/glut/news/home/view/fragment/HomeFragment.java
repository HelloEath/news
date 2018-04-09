package com.glut.news.home.view.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.glut.news.R;
import com.glut.news.common.utils.ApiConstants;
import com.glut.news.common.utils.Base64CoderUtil;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.SetUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.UserUtil;
import com.glut.news.common.view.SearchActivity;
import com.glut.news.common.view.searchview.ICallBack;
import com.glut.news.common.view.searchview.SearchView;
import com.glut.news.home.presenter.impl.HomeFragmentImpl;
import com.glut.news.login.view.fragment.LoginActivity;
import com.glut.news.my.view.activity.OtherSettingActivity;
import com.glut.news.my.view.activity.StarWithHistoryActivity;
import com.glut.news.my.view.activity.UserAlterActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yy on 2018/1/22.
 */
public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener,IHomeFragmentView{
   private  List<Fragment> lf;
    private ViewPager viewpager;
    private ImageView btn_logo;
    private TabLayout tabLayout;
    private ImageView mAddVideo;
    private SearchView searchView;
    private ImageView mLogo;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView ilogo;
    private LinearLayout nacigation_header;
    private List<String> titles=new ArrayList<>();

    private TextView mHistoryNum;
    private TextView mStarNum;
    private TextView UserName;
    private Menu m;

    private HomeFragmentImpl homeFragment=new HomeFragmentImpl(this);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.fragment_home,container,false);
        initView(v);
        initData(v);
        if (SetUtil.getInstance().getIsFirstTime()){
            showTapTarget();
        }



        return v;

    }
    private void showTapTarget() {

        // 引导用户使用
        TapTargetSequence sequence = new TapTargetSequence(getActivity())
                .targets(


                      TapTarget.forView(mLogo, "点击头像管理个人信息")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.colorPrimary)
                                .drawShadow(true)
                              .targetRadius(60)
                .id(1)
                        /*TapTarget.forToolbarNavigationIcon(toolbar, "点击这里展开侧栏")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorPrimary)
                                .drawShadow(true)
                                .id(2),
                        TapTarget.forBounds(target, "点击这里切换新闻", "双击返回顶部\n再次双击刷新当前页面")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorPrimary)
                                .targetRadius(60)
                                .transparentTarget(true)
                                .drawShadow(true)
                                .id(3)*/
                ).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        SetUtil.getInstance().setIsFirstTime(false);
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        SetUtil.getInstance().setIsFirstTime(false);
                    }
                });
        sequence.start();
    }

    private void initView(View v) {
        lf=new ArrayList<>();

        //toolbar= (Toolbar) v.findViewById(R.id.toolbar);
        drawerLayout= (DrawerLayout)v. findViewById(R.id.drawerview);
        navigationView= (NavigationView) v.findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
         m= navigationView.getMenu();

        ilogo =  headerView.findViewById(R.id.logo);
        nacigation_header=headerView.findViewById(R.id.navigation_header_container);
        UserName=headerView.findViewById(R.id.UserName);
        toolbar=headerView.findViewById(R.id.toolbar);

        tabLayout=v.findViewById(R.id.tabLayout);
        mAddVideo=v.findViewById(R.id.addVideo);
        searchView=v.findViewById(R.id.search);
        mLogo=v.findViewById(R.id.circlrimage);
        viewpager=v.findViewById(R.id.viewger_home);
       // mLogo.setVisibility(View.GONE);

    }





    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getContext()).load(Base64CoderUtil.decodeLines(SpUtil.getUserFromSp("UserLogo"))).apply(new RequestOptions().circleCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(ilogo);
        Glide.with(getContext()).load(Base64CoderUtil.decodeLines(SpUtil.getUserFromSp("UserLogo"))).apply(
                RequestOptions.circleCropTransform().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(mLogo);
        UserName.setText(SpUtil.getUserFromSp("UserName"));

    }

    private void initData(View v) {

        if (UserUtil.isUserLogin()){

            UserName.setText(SpUtil.getUserFromSp("UserName"));
            homeFragment.loadHistoryCount();//获取历史记录数
            homeFragment.loadStarCount();//获取我的收藏数
            String ff=SpUtil.getUserFromSp("UserLogo");
            byte[] d=Base64CoderUtil.decodeLines(SpUtil.getUserFromSp("UserLogo"));
            Glide.with(getContext()).load(Base64CoderUtil.decodeLines(SpUtil.getUserFromSp("UserLogo"))).apply(new RequestOptions().circleCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(ilogo);
            Glide.with(getContext()).load(Base64CoderUtil.decodeLines(SpUtil.getUserFromSp("UserLogo"))).apply(
                    RequestOptions.circleCropTransform().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(mLogo);


        }else {
            UserName.setText("我是谁");
            m.findItem(R.id.navigation_item_info).setVisible(false);
            Glide.with(getContext()).load(R.drawable.unlogin_logo).apply(new RequestOptions().circleCrop()).into(ilogo);
            Glide.with(getContext()).load(R.drawable.unlogin_logo).apply(
                    RequestOptions.circleCropTransform()).into(mLogo);
        }

        navigationView.setItemIconTintList(null);
        titles.add("推荐");
        titles.add("互联网");
        titles.add("军事");
        titles.add("旅游");
        titles.add("国内");
        titles.add("科技");
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                nacigation_header.setBackground(resource);
            }
        };
        if (NetUtil.isWifiConnected()){

            Glide.with(getContext()).load(ApiConstants.welcomeImageAPi).apply(new RequestOptions().fitCenter()).into(simpleTarget);

        }else {
            nacigation_header.setBackgroundColor(getResources().getColor(R.color.side_1));
        }
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

             Intent intent=new Intent(getActivity(), SearchActivity.class);
             intent.putExtra("v",string);
             startActivity(intent);
         }
     });

        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeFragment.loadHistoryCount();//获取历史记录数
                homeFragment.loadStarCount();//获取我的收藏数
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });
        mAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {

                            case R.id.navigation_item_star:
                                Intent i=null;
                                if (UserUtil.isUserLogin()){
i
                                    =new Intent(getActivity(),StarWithHistoryActivity.class);
                                    i.putExtra("type","2");
                                    startActivity(i);
                                }else {
                                    Snackbar s= Snackbar.make(getView(),"登录查看的你收藏记录",Snackbar.LENGTH_LONG).setAction("点我立即登录", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(getActivity(), LoginActivity.class));

                                        }
                                    });
                                    View sv=s.getView();
//文字的颜色
                                    ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                                    sv.setBackgroundColor(0xffffffff);
                                    s.show();


                                }

                                break;
                            case R.id.navigation_item_history:

                                if (UserUtil.isUserLogin()){

                                    i=new Intent(getActivity(),StarWithHistoryActivity.class);
                                    i.putExtra("type","1");
                                    startActivity(i);
                                }else {

                                    Snackbar s= Snackbar.make(getView(),"登录查看你的历史记录",Snackbar.LENGTH_LONG).setAction("点我立即登录", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(getActivity(), LoginActivity.class));

                                        }
                                    });
                                    View sv=s.getView();
//文字的颜色
                                    ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                                    sv.setBackgroundColor(0xffffffff);
                                    s.show();

                                }


                                break;
                            case R.id.navigation_item_info:
                                startActivity(new Intent(getContext(),UserAlterActivity.class));
                                break;
                            case R.id.nav_setting:
                                    i=new Intent(getActivity(),OtherSettingActivity.class);
                                startActivity(i);
                                break;


                        }
                        //menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

        viewpager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewpager);
    }


    @Override
    public void onClick(View v) {

    }



    @Override
    public void onLoadStarCountSuccess(int v) {
       m.findItem(R.id.navigation_item_history).setTitle("我的收藏 | "+v);
    }

    @Override
    public void onLoadHistoryCountSuccess(int v) {
        m.findItem(R.id.navigation_item_star).setTitle("历史记录 | "+v);
    }


}
