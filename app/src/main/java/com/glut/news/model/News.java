package com.glut.news.model;

import com.glut.news.entity.Data;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yy on 2018/1/24.
 */

public class News {
    public Result result;

    public class Result {
        @SerializedName("data")
        public List<Data> datalist;
        public String stat;


    }
}