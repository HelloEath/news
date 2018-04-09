package com.glut.news.discover.view.fragment;

import com.glut.news.discover.model.entity.OneModel;

/**
 * Created by yy on 2018/4/3.
 */

public interface IOneFragmentView {
    void loadDataSuccess(OneModel o);
    void loadMoreDataSuccess(OneModel o);
    void loadDataFail();
    void loadMoreDataFail();
}
