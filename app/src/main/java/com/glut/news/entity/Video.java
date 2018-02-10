package com.glut.news.entity;

/**
 * Created by yy on 2018/1/27.
 */
public class Video {
    private String title;
    private String img_url;
    private String author;
    private String watchs;
    private String commits;

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWatchs() {
        return watchs;
    }

    public void setWatchs(String watchs) {
        this.watchs = watchs;
    }
}
