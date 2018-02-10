package com.glut.news.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.glut.news.R;

/**
 * Created by yy on 2018/1/27.
 */

public class WelcomeActivity extends Activity {

    private AlphaAnimation start_anima;
    private Context context;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_welcome, null);
     /*   ImageView imageView=findViewById(R.id.welcomemage);*/
        /*Glide.with(context).load(ApiConstants.welcomeImageAPi)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageViewTarget);*/
        setContentView(view);
        initView();
        initData();
    }
    private void initData() {
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(2000);
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                redirectTo();
            }
        });
    }

    private void initView() {

    }

    private void redirectTo() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
