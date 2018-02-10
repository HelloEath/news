package com.glut.news.entity;

import com.glut.news.model.NewsTest;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yy on 2018/1/24.
 */

public class News {
    public Result result;

    public class Result {
        @SerializedName("data")
        public List<NewsTest> datalist;
        public String stat;


    }
}