package com.glut.news.entity;

import java.util.List;

/**
 * Created by yyon 2018/2/8.
 */
public class OneDateList {
    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    private String res;
    private List<String> data;

}
