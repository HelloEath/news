package com.glut.news.common.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.login.view.fragment.LoginActivity;

/**
 * Created by yy on 2018/3/21.
 */

public class IntroActivity extends AppIntro implements ISlideBackgroundColorHolder {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppApplication.getInstance().addActivity(this);
        //隐藏状态栏
        Window window=getWindow();
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setFlags(flag,flag);
        //askForPermissions(new String[]{Manifest.permission.CAMERA}, 2); // OR
        // Note here that we DO NOT use setContentView();
        //状态栏半透明
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.

        addSlide(FirstFragment.newInstance("首页","der",R.drawable.login_bg,getSupportFragmentManager()));
        addSlide(FirstFragment.newInstance("视频","der",R.drawable.login_bg,getSupportFragmentManager()));
        addSlide(FirstFragment.newInstance("发现","der",R.drawable.login_bg,getSupportFragmentManager()));
        addSlide(FirstFragment.newInstance("天气","der",R.drawable.login_bg,getSupportFragmentManager()));

        addSlide(AppIntroFragment.newInstance("视频", "dd", R.drawable.login_bg,getResources().getColor(R.color.side_2)));

        /* addSlide(secondFragment);
        addSlide(thirdFragment);
        addSlide(fourthFragment);*/

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
/*       *//* addSlide(AppIntroFragment.newInstance("首页", "dd", R.drawable.login_bg, getResources().getColor(R.color.side_1)));
        addSlide(AppIntroFragment.newInstance("视频", "dd", R.drawable.login_bg,getResources().getColor(R.color.side_2)));
        addSlide(AppIntroFragment.newInstance("发现", "dd", R.drawable.login_bg,getResources().getColor( R.color.side_3)));
        addSlide(AppIntroFragment.newInstance("天气", *//*"dd", R.drawable.login_bg,getResources().getColor(R.color.side_4)));*/

        // OPTIONAL METHODS
        // Override bar/separator color.
      /*  setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));*/

        //切换动画
        setZoomAnimation();
        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent=new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent=new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#000000");
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
// Set the background color of the view within your slide to which the transition should be applied.
      /*  if (layoutContainer != null) {
            layoutContainer.setBackgroundColor(backgroundColor);
        }*/
    }
    }