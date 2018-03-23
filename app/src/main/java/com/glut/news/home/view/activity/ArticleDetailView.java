package com.glut.news.home.view.activity;

import com.glut.news.video.model.entity.VideoCommentsModel;

/**
 * Created by yy on 2018/3/13.
 */

public interface ArticleDetailView {
    void addAdater(VideoCommentsModel commonData);
    void changeAdater(VideoCommentsModel commonData);
    void onStarSuccess();
    void onStarFail();
}
