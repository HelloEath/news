package com.glut.news.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yy on 2018/2/8.
 */

public class One {
    @SerializedName("res")
    private String res;
    @SerializedName("data")
    private OneData data;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public  OneData getData() {
        return data;
    }

    public void setData(OneData data) {
        this.data = data;
    }
}
