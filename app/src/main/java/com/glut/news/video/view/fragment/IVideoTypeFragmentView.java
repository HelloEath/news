package com.glut.news.video.view.fragment;

import com.glut.news.video.model.entity.VideoModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by yy on 2018/3/28.
 */

public interface IVideoTypeFragmentView {
    void loadVideoDataSuccess(VideoModel v, RefreshLayout r);
    void loadVideoDataFail(RefreshLayout r);
    void loadVideoMoreVideoDataSuccesss(VideoModel v,RefreshLayout r);
    void loadVideoMoreVideoDataFail(RefreshLayout r);


}
