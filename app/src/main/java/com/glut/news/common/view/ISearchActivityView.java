package com.glut.news.common.view;

import com.glut.news.home.model.entity.ArticleModel;

/**
 * Created by yy on 2018/3/19.
 */

public interface ISearchActivityView {
    void onSearchSuccess(ArticleModel h);
    void onMoreSearchSuccess(ArticleModel h);
    void onSearchFail();

    void noMoreData();
}
