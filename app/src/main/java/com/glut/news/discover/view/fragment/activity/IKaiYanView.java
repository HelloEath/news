package com.glut.news.discover.view.fragment.activity;

import com.glut.news.discover.model.entity.KaiYanModel;

/**
 * Created by yy on 2018/3/12.
 */

public interface IKaiYanView {
    void showLoading();
    void hideLoading();
    void showEmpty();
    void hideEnpty();
    void showLoadError();
    void hideLoadError();
    void addAdaterData(KaiYanModel guoKrListModel);
    void changeAdaterData(KaiYanModel guoKrListModel);
}
