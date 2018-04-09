package com.glut.news.discover.view.fragment;

import com.glut.news.discover.model.entity.GuoKrListModel;

/**
 * Created by yy on 2018/3/12.
 */

public interface IGuoKrFragmentView {

    void showLoading();
    void hideLoading();
    void showEmpty();
    void hideEnpty();
    void showLoadError();
    void hideLoadError();
    void setAdaterData(GuoKrListModel guoKrListModel);

}
