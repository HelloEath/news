package com.glut.news.home.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yy on 2018/1/25.
 */

public class NewsTest {
    public String title;
    @SerializedName("thumbnail_pic_s")
    public String pic_src;
    @SerializedName("date")
    public String date;
    @SerializedName("author_name")
    public String author;
    @SerializedName("url")
    public String detail_url;

    public NewsTest(String title, String pic_src, String date, String author, String detail_url) {
        this.title = title;
        this.pic_src = pic_src;
        this.date = date;
        this.author = author;
        this.detail_url = detail_url;
    }
}

