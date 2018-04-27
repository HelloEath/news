package com.glut.news.home.model.entity;

import java.util.List;

/**
 * Created by yy on 2018/2/27.
 */
public class ArticleModel {
  private int   nextpage;//	2
  private List<ArticleList> data;
  private String stus;
  private boolean isHaveNextPage;

    public boolean isHaveNextPage() {
        return isHaveNextPage;
    }

    public void setHaveNextPage(boolean haveNextPage) {
        isHaveNextPage = haveNextPage;
    }

    public int getNextpage() {
        return nextpage;
    }

    public void setNextpage(int nextpage) {
        this.nextpage = nextpage;
    }

    public List<ArticleList> getData() {
        return data;
    }

    public void setData(List<ArticleList> data) {
        this.data = data;
    }

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public class ArticleList {

    private String     Article_Content;//	"<div id=\"content\"> <p>…size:10px;\"></p></div>"
        private String     userInfo	;//null
        private String   comment	;//null
        private int  article_Likes	;//0
        private String     article_Abstract	;//null
        private String    article_Content	;//"<div id=\"content\"> <p>…size:10px;\"></p></div>"
        private int  artilce_Comments	;//254
        private String    article_IsOrigina	;//null
        private String    article_IsEnable;//	null
        private String     article_Image	;//"http://zkres2.myzaker.co…f52e9811f000011_320.jpg"
        private int article_Id	;//226164624
        private String    article_Title	;//"360 除了更流氓，真是没有一点儿长进！"
        private String     article_Url	;//"http://www.myzaker.com/a…9273d29490cbc235000012/"
        private String    article_Type	;//"互联网"
        private int  article_Size	;//0
        private String    article_Time	;//"昨天"
        private int   article_Looks	;//0
        private int  article_Stars	;//0
        private String    article_IsTop	;//null
        private String    article_IsNew	;//null
        private String    article_KeyWords	;//"b站 快视频 周鸿祎"
        private String  article_Author_name	;//"酷玩实验室"
        private String   article_Author_logo	;//null
        private String  article_Author_desc;//	null

        public String getArticle_Content() {
            return Article_Content;
        }

        public void setArticle_Content(String article_Content) {
            Article_Content = article_Content;
        }

        public int getArtilce_Comments() {
            return artilce_Comments;
        }

        public void setArtilce_Comments(int artilce_Comments) {
            this.artilce_Comments = artilce_Comments;
        }

        public String getArticle_IsOrigina() {
            return article_IsOrigina;
        }

        public void setArticle_IsOrigina(String article_IsOrigina) {
            this.article_IsOrigina = article_IsOrigina;
        }

        public String getArticle_IsEnable() {
            return article_IsEnable;
        }

        public void setArticle_IsEnable(String article_IsEnable) {
            this.article_IsEnable = article_IsEnable;
        }

        public String getArticle_Image() {
            return article_Image;
        }

        public void setArticle_Image(String article_Image) {
            this.article_Image = article_Image;
        }

        public int getArticle_Id() {
            return article_Id;
        }

        public void setArticle_Id(int article_Id) {
            this.article_Id = article_Id;
        }

        public String getArticle_Title() {
            return article_Title;
        }

        public void setArticle_Title(String article_Title) {
            this.article_Title = article_Title;
        }

        public String getArticle_Url() {
            return article_Url;
        }

        public void setArticle_Url(String article_Url) {
            this.article_Url = article_Url;
        }

        public String getArticle_Type() {
            return article_Type;
        }

        public void setArticle_Type(String article_Type) {
            this.article_Type = article_Type;
        }

        public int getArticle_Size() {
            return article_Size;
        }

        public void setArticle_Size(int article_Size) {
            this.article_Size = article_Size;
        }

        public String getArticle_Time() {
            return article_Time;
        }

        public void setArticle_Time(String article_Time) {
            this.article_Time = article_Time;
        }

        public int getArticle_Looks() {
            return article_Looks;
        }

        public void setArticle_Looks(int article_Looks) {
            this.article_Looks = article_Looks;
        }

        public int getArticle_Stars() {
            return article_Stars;
        }

        public void setArticle_Stars(int article_Stars) {
            this.article_Stars = article_Stars;
        }

        public String getArticle_IsTop() {
            return article_IsTop;
        }

        public void setArticle_IsTop(String article_IsTop) {
            this.article_IsTop = article_IsTop;
        }

        public String getArticle_IsNew() {
            return article_IsNew;
        }

        public void setArticle_IsNew(String article_IsNew) {
            this.article_IsNew = article_IsNew;
        }

        public String getArticle_KeyWords() {
            return article_KeyWords;
        }

        public void setArticle_KeyWords(String article_KeyWords) {
            this.article_KeyWords = article_KeyWords;
        }

        public String getArticle_Author_name() {
            return article_Author_name;
        }

        public void setArticle_Author_name(String article_Author_name) {
            this.article_Author_name = article_Author_name;
        }

        public String getArticle_Author_logo() {
            return article_Author_logo;
        }

        public void setArticle_Author_logo(String article_Author_logo) {
            this.article_Author_logo = article_Author_logo;
        }

        public String getArticle_Author_desc() {
            return article_Author_desc;
        }

        public void setArticle_Author_desc(String article_Author_desc) {
            this.article_Author_desc = article_Author_desc;
        }

        public String getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(String userInfo) {
            this.userInfo = userInfo;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getArticle_Likes() {
            return article_Likes;
        }

        public void setArticle_Likes(int article_Likes) {
            this.article_Likes = article_Likes;
        }

        public String getArticle_Abstract() {
            return article_Abstract;
        }

        public void setArticle_Abstract(String article_Abstract) {
            this.article_Abstract = article_Abstract;
        }
    }



}
