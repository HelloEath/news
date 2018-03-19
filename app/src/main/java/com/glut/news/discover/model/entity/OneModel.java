package com.glut.news.discover.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yy on 2018/2/8.
 */

public class OneModel {
    @SerializedName("res")
    private String res;
    @SerializedName("data")
    private OneDataModel data;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public OneDataModel getData() {
        return data;
    }

    public void setData(OneDataModel data) {
        this.data = data;
    }
}
