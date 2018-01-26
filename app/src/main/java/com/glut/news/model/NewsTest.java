package com.glut.news.model;

/**
 * Created by yy on 2018/1/25.
 */

public class NewsTest {
    private String title;
    private String src;
    private String author;
    private String detail_url;

    public NewsTest(String title, String src, String author, String detail_url) {
        this.title = title;
        this.src = src;
        this.author = author;
        this.detail_url = detail_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }
}
