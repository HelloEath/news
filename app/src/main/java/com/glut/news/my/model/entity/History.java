package com.glut.news.my.model.entity;

/**
 * Created by yy on 2018/2/12.
 */
public class History {
    private int History_Id;
    private int History_Article;
    private int History_Persion;
    private String History_Time;
    private String Content_type;
   private int History_Type;

    public String getContent_type() {
        return Content_type;
    }

    public void setContent_type(String content_type) {
        Content_type = content_type;
    }

/* private ArticleModel.ArticleList article;
    private VideoModel.VideoList video;*/

   /* public ArticleModel.ArticleList getArticle() {
        return article;
    }

    public void setArticle(ArticleModel.ArticleList article) {
        this.article = article;
    }

    public VideoModel.VideoList getVideo() {
        return video;
    }

    public void setVideo(VideoModel.VideoList video) {
        this.video = video;
    }*/

    public int getHistory_Id() {
        return History_Id;
    }

    public void setHistory_Id(int history_Id) {
        History_Id = history_Id;
    }

    public int getHistory_Article() {
        return History_Article;
    }

    public void setHistory_Article(int history_Article) {
        History_Article = history_Article;
    }

    public int getHistory_Persion() {
        return History_Persion;
    }

    public void setHistory_Persion(int history_Persion) {
        History_Persion = history_Persion;
    }

    public String getHistory_Time() {
        return History_Time;
    }

    public void setHistory_Time(String history_Time) {
        History_Time = history_Time;
    }

    public int getHistory_Type() {
        return History_Type;
    }

    public void setHistory_Type(int history_Type) {
        History_Type = history_Type;
    }
}
