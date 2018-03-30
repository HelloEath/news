package com.glut.news.my.view.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.utils.SetUtil;
import com.glut.news.my.view.fragment.SettingsFragment;

/**
 * Created by yy on 2018/2/11.
 */
public class OtherSettingActivity  extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//状态栏半透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/

        SetUtil.getInstance().setStatusColor(getResources().getColor(R.color.side_1),getWindow());
        setContentView(R.layout.activity_othersetting);
        initView();
        AppApplication.getInstance().addActivity(this);

    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //动态改变Toolbar返回按钮颜色：改为灰色
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);

        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        ActionBar a=getSupportActionBar();
        if (a!=null){
            a.setDisplayHomeAsUpEnabled(true);
           // a.setHomeAsUpIndicator(R.drawable.back);
        }
        SettingsFragment sf=new SettingsFragment();
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.add(R.id.otherSetting,sf);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return  true;

        }
        return false;
    }
}
