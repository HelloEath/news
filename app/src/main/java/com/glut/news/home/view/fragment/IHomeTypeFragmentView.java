package com.glut.news.home.view.fragment;

import com.glut.news.home.model.entity.ArticleModel;

import java.util.List;

/**
 * Created by yy on 2018/4/20.
 */

public interface IHomeTypeFragmentView {
    void onloadDataSuccess(List<ArticleModel.ArticleList> data);
    void onLoadDataFail();
    void onLoadMoreDataFail();

    void onloadMoreDataSuccess(List<ArticleModel.ArticleList> data);

    void onLoadEmptyData();
}
