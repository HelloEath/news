package com.glut.news.video.model.entity;

import java.util.List;

/**
 * Created by yy on 2018/2/28.
 */
public class VideoCommentsModel {
    private int nextpage;//	3
    private String stus;//	"ok"
    private List<VideoCommentListModel> data;

    public int getNextpage() {
        return nextpage;
    }

    public void setNextpage(int nextpage) {
        this.nextpage = nextpage;
    }

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public List<VideoCommentListModel> getData() {
        return data;
    }

    public void setData(List<VideoCommentListModel> data) {
        this.data = data;
    }
}


