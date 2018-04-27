package com.glut.news.my.view.fragment;

import com.glut.news.my.model.entity.HistoryWithStarModel;

/**
 * Created by yy on 2018/3/14.
 */

public interface HistoryFragmentView {


    void onLoadHistorySuccess(HistoryWithStarModel h);
    void onLoadHistoryFail();
    void  onLoadMoreHistorySuccess(HistoryWithStarModel h);
    void onNoMoreHistoryData();
}
