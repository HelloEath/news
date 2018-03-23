package com.glut.news.home.presenter;

import com.glut.news.my.model.entity.History;
import com.glut.news.my.model.entity.Star;

/**
 * Created by yy on 2018/3/13.
 */

public interface IArticleDetailPresenter {
    void star(Star s);
    void onHistory(History s);
    void loadComment(int Id);
    void loadMoreComment();
}
