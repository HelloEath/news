package com.glut.news.video.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glut.news.R;

/**
 * Created by yy on 2018/1/22.
 */
public class AddVideoFragment extends android.support.v4.app.Fragment  {
    private ImageView user_logo;
    private ImageView user_bg;
    private LinearLayout btn_star;
    private LinearLayout btn_history;
    private LinearLayout btn_alter;
    private LinearLayout btn_setting;
    private LinearLayout l;

    private TextView user_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my,container,false);

        return v;
    }


}
