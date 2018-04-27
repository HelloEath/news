package com.glut.news.video.presenter;

import com.glut.news.common.model.entity.Comment;
import com.glut.news.my.model.entity.Star;

/**
 * Created by yy on 2018/3/13.
 */

public interface IVideoDetailActivityPresenter {
    void loadComment(String  fp);
    void sendComment(Comment c);
    void recordStar(Star s);
    void updateComments();
    void getVideoDetailInfo(int id);
    void loadRecommenData(String videoType);
}
