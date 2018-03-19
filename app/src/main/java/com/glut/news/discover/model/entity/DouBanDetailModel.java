package com.glut.news.discover.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yy on 2018/2/5.
 */
public class DouBanDetailModel {

    @Expose
    @SerializedName("display_style")
    private int displayStyle;


    @Expose
    @SerializedName("short_url")
    private String shortUrl;


    @Expose
    @SerializedName("abstract")
    private String abs;


    @Expose
    @SerializedName("app_css")
    private int appCss;


    @Expose
    @SerializedName("like_count")
    private int likeCount;


    @Expose
    @SerializedName("thumbs")
    private List<DouBanListModel.DoubanMomentNewsThumbs> thumbs;


    @Expose
    @SerializedName("created_time")
    private String createdTime;


    @Expose
    @SerializedName("id")
    private int id;


    @Expose
    @SerializedName("is_editor_choice")
    private boolean isEditorChoice;


    @Expose
    @SerializedName("original_url")
    private String originalUrl;


    @Expose
    @SerializedName("content")
    private String content;


    @Expose
    @SerializedName("share_pic_url")
    private String sharePicUrl;


    @Expose
    @SerializedName("type")
    private String type;


    @Expose
    @SerializedName("is_liked")
    private boolean isLiked;


    @Expose
    @SerializedName("photos")
    private List<DouBanListModel.DoubanMomentNewsThumbs> photos;


    @Expose
    @SerializedName("published_time")
    private String publishedTime;


    @Expose
    @SerializedName("url")
    private String url;


    @Expose
    @SerializedName("column")
    private String column;


    @Expose
    @SerializedName("comments_count")
    private int commentsCount;


    @Expose
    @SerializedName("title")
    private String title;

}
