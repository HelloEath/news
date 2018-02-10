package com.glut.news.model;

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
}

