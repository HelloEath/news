package com.glut.news.common.view;

import com.glut.news.my.model.entity.HistoryWithStarModel;

/**
 * Created by yy on 2018/3/19.
 */

public interface ISearchActivityView {
    void onSearchSuccess(HistoryWithStarModel h);
    void onMoreSearchSuccess(HistoryWithStarModel h);
    void onSearchFail();
}
