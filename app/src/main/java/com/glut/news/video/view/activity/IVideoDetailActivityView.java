package com.glut.news.video.view.activity;

import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.video.model.entity.VideoModel;

import java.util.List;

/**
 * Created by yy on 2018/3/13.
 */

public interface IVideoDetailActivityView {
    void addCommentAdater(VideoCommentsModel v);
    void changeCommentAdater(VideoCommentsModel v);
    void onSendCommentSuccess();
    void onSendCommentFail();
    void onStarSuccess();
    void onStarFali();
    void onLoadVideoDetailDataSuccess();
    void onLoadVideoDetailDataFail();

    void addRecommentData(List<VideoModel.VideoList> data);

    void onloadVideoDetailInfoFail();

    void onloadVideoDetailInfoSuccess(VideoModel videoModel);

    void onloadVideoCommentFail();

    void noMoreVideoComment();

    void onDeleteCommentSuccess();

    void onDeleteCommentFail();
}
