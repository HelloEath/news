package com.glut.news.my.model.entity;

import java.util.List;

/**
 * Created by yy on 2018/3/9.
 */

public class HistoryWithStarModel {
    private int   nextpage;//	2
    private List<CommonData> data;
    private String stus;

    public int getNextpage() {
        return nextpage;
    }

    public void setNextpage(int nextpage) {
        this.nextpage = nextpage;
    }

    public List<CommonData> getData() {
        return data;
    }

    public void setData(List<CommonData> data) {
        this.data = data;
    }

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }


}
