package com.glut.news.my.model.entity;

import java.util.List;

/**
 * Created by yy on 2018/3/9.
 */

public class HistoryWithStarModel {
    private boolean isHaveNextPage;
    private int   nextPage;//	2
    private List<CommonData> data;
    private String stus;

    public boolean isHaveNextPage() {
        return isHaveNextPage;
    }

    public void setHaveNextPage(boolean haveNextPage) {
        isHaveNextPage = haveNextPage;
    }

    public int getNextpage() {
        return nextPage;
    }

    public void setNextpage(int nextPage) {
        this.nextPage = nextPage;
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
