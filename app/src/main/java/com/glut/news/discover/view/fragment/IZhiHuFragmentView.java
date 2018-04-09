package com.glut.news.discover.view.fragment;

import com.glut.news.discover.model.entity.ZhiHuList;

/**
 * Created by yy on 2018/4/3.
 */

public interface IZhiHuFragmentView {
    void onLoadDataSuccess(ZhiHuList zhiHuList);
    void onLoadMoreDataSuccess(ZhiHuList zhiHuList);
    void onLoadDataFail();
    void onLoadMoreDataFail();
}
