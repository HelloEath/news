package com.glut.news.video.model.entity;

/**
 * Created by yy on 2018/3/4.
 */

public class VideoCommentListModel {
    private String   userInfo;//	null
    private String    comment_Content;//	"话说中国的巨额美国债咋办？还有某些人在美的老婆孩子车子票子？"
    private int  comment_Author;//	0
    private int  comment_Article;//	226164540
    private String     comment_Enable;//	null
    private int   likes;//	0
    private String    author_name;//	"昵称不能有空格"
    private String    author_logo	;//"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erlfNxxRGRPIcn9IjWaeO6McfIueGRiaz079gFcQaWm41CVAXjwEX4kU6KGJ476CPUqXC7OYhiaEUJg/0"
    private int  comment_Id	;//59518
    private String    comment_Time;//	"2018-1-26-4:38"

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getComment_Content() {
        return comment_Content;
    }

    public void setComment_Content(String comment_Content) {
        this.comment_Content = comment_Content;
    }

    public int getComment_Author() {
        return comment_Author;
    }

    public void setComment_Author(int comment_Author) {
        this.comment_Author = comment_Author;
    }

    public int getComment_Article() {
        return comment_Article;
    }

    public void setComment_Article(int comment_Article) {
        this.comment_Article = comment_Article;
    }

    public String getComment_Enable() {
        return comment_Enable;
    }

    public void setComment_Enable(String comment_Enable) {
        this.comment_Enable = comment_Enable;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_logo() {
        return author_logo;
    }

    public void setAuthor_logo(String author_logo) {
        this.author_logo = author_logo;
    }

    public int getComment_Id() {
        return comment_Id;
    }

    public void setComment_Id(int comment_Id) {
        this.comment_Id = comment_Id;
    }

    public String getComment_Time() {
        return comment_Time;
    }

    public void setComment_Time(String comment_Time) {
        this.comment_Time = comment_Time;
    }

}
