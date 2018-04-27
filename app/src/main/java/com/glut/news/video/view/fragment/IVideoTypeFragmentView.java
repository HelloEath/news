package com.glut.news.video.view.fragment;

import com.glut.news.video.model.entity.VideoModel;

/**
 * Created by yy on 2018/3/28.
 */

public interface IVideoTypeFragmentView {
    void loadVideoDataSuccess(VideoModel v);
    void loadVideoDataFail();
    void loadVideoMoreVideoDataSuccesss(VideoModel v);
    void loadVideoMoreVideoDataFail();


    void noMoreData();
}
