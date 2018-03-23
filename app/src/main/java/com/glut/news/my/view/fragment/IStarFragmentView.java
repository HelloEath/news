package com.glut.news.my.view.fragment;

import com.glut.news.my.model.entity.HistoryWithStarModel;

/**
 * Created by yy on 2018/3/18.
 */

public interface IStarFragmentView {
    void onLoadStarSuccess(HistoryWithStarModel h);
    void onLoadSMoretarSuccess(HistoryWithStarModel h);
    void onLoadStarFail();
    void onLoadSMoretarFail();
}
