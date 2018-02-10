package com.glut.news.view.customview;

import android.content.Context;
import android.util.AttributeSet;

import com.glut.news.R;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by yy on 2018/2/7.
 */

public class VideoPlayer extends JZVideoPlayerStandard {
    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.jiecao;
    }
}
