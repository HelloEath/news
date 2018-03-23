package com.glut.news.video.view.activity;

import com.glut.news.common.model.entity.Comment;
import com.glut.news.video.model.entity.VideoCommentsModel;

/**
 * Created by yy on 2018/3/13.
 */

public interface IVideoDetailView {
    void addCommentAdater(VideoCommentsModel v);
    void changeCommentAdater(VideoCommentsModel v);
    void onSendCommentSuccess(Comment c);
    void onSendCommentFail();
    void initListenter();
    void onStarSuccess();
    void onStarFali();
}
