package com.glut.news.video.presenter;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by yy on 2018/3/28.
 */

public interface IVideoTypeFragmentPresenter {
    void loadVideoData(String type, RefreshLayout r);
    void loadMoreData(String type,RefreshLayout r);
}
