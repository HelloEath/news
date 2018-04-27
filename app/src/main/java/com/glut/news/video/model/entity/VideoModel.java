package com.glut.news.video.model.entity;

import java.util.List;

/**
 * Created by yy on 2018/1/27.
 */
public class VideoModel {
    private int nextpage;



    private boolean isHaveNextPage;
    private String stus;
    private List<VideoList> data;
    public int getNextpage() {
        return nextpage;
    }

    public boolean isHaveNextPage() {
        return isHaveNextPage;
    }

    public void setHaveNextPage(boolean haveNextPage) {
        isHaveNextPage = haveNextPage;
    }

    public void setNextpage(int nextpage) {
        this.nextpage = nextpage;
    }
    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public List<VideoList> getData() {
        return data;
    }

    public void setData(List<VideoList> data) {
        this.data = data;
    }

    public  class VideoList {
        private String  video_KeyWord;//	null
        private String   video_From;//	"来源：澎湃新闻"
        private String  video_time;//	"09:03"
        private String   video_Id	;//2009615
        private String  video_Title;//	"“北京八分钟”亮相：高科技融入中国元素"
        private String   video_Image;//	"http://image1.thepaper.cn/image/6/911/842.jpg"
        private String   video_Player;//	"http://cloudvideo.thepaper.cn/video/9acc9b5053084a998178206f9839fbce/hd/c58069fe-8ef1-42e6-8dfe-043cb61728af-cf32b4b6-9384-5751-340b-d4c266f1c600.mp4"
        private String   video_Type;//	null
        private int   video_Size;//	0
        private String  video_PutTime;//	"2018-02-25 21:43"
        private int  video_Author;//	0
        private int   video_Players;//	0
        private int  video_Stars;//	0
        private int  video_Likes	;//0
        private String  video_IsNew;//	null
        private String   video_Abstract	;//"2月25日晚，“北京八分钟”亮相平昌冬奥会闭幕式，主题为“2022，相约北京”。24名演员与智能机器人相映成趣，带来了一场融合科技与文化的视听盛宴。"
        private String   video_Download;//	null
        private String  video_Author_Name	;//"World湃"
        private String  video_Author_Logo	;//"http://image.thepaper.cn/image/5/746/155.jpg"
        private int   video_Comments	;//0
        private String  video_IsOrigina	;//null
        private String  video_IsEnable;//	null

        public String getVideo_KeyWord() {
            return video_KeyWord;
        }

        public void setVideo_KeyWord(String video_KeyWord) {
            this.video_KeyWord = video_KeyWord;
        }

        public String getVideo_From() {
            return video_From;
        }

        public void setVideo_From(String video_From) {
            this.video_From = video_From;
        }

        public String getVideo_time() {
            return video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getVideo_Id() {
            return video_Id;
        }

        public void setVideo_Id(String video_Id) {
            this.video_Id = video_Id;
        }

        public String getVideo_Title() {
            return video_Title;
        }

        public void setVideo_Title(String video_Title) {
            this.video_Title = video_Title;
        }

        public String getVideo_Image() {
            return video_Image;
        }

        public void setVideo_Image(String video_Image) {
            this.video_Image = video_Image;
        }

        public String getVideo_Player() {
            return video_Player;
        }

        public void setVideo_Player(String video_Player) {
            this.video_Player = video_Player;
        }

        public String getVideo_Type() {
            return video_Type;
        }

        public void setVideo_Type(String video_Type) {
            this.video_Type = video_Type;
        }

        public int getVideo_Size() {
            return video_Size;
        }

        public void setVideo_Size(int video_Size) {
            this.video_Size = video_Size;
        }

        public String getVideo_PutTime() {
            return video_PutTime;
        }

        public void setVideo_PutTime(String video_PutTime) {
            this.video_PutTime = video_PutTime;
        }

        public int getVideo_Author() {
            return video_Author;
        }

        public void setVideo_Author(int video_Author) {
            this.video_Author = video_Author;
        }

        public int getVideo_Players() {
            return video_Players;
        }

        public void setVideo_Players(int video_Players) {
            this.video_Players = video_Players;
        }

        public int getVideo_Stars() {
            return video_Stars;
        }

        public void setVideo_Stars(int video_Stars) {
            this.video_Stars = video_Stars;
        }

        public int getVideo_Likes() {
            return video_Likes;
        }

        public void setVideo_Likes(int video_Likes) {
            this.video_Likes = video_Likes;
        }

        public String getVideo_IsNew() {
            return video_IsNew;
        }

        public void setVideo_IsNew(String video_IsNew) {
            this.video_IsNew = video_IsNew;
        }

        public String getVideo_Abstract() {
            return video_Abstract;
        }

        public void setVideo_Abstract(String video_Abstract) {
            this.video_Abstract = video_Abstract;
        }

        public String getVideo_Download() {
            return video_Download;
        }

        public void setVideo_Download(String video_Download) {
            this.video_Download = video_Download;
        }

        public String getVideo_Author_Name() {
            return video_Author_Name;
        }

        public void setVideo_Author_Name(String video_Author_Name) {
            this.video_Author_Name = video_Author_Name;
        }

        public String getVideo_Author_Logo() {
            return video_Author_Logo;
        }

        public void setVideo_Author_Logo(String video_Author_Logo) {
            this.video_Author_Logo = video_Author_Logo;
        }

        public int getVideo_Comments() {
            return video_Comments;
        }

        public void setVideo_Comments(int video_Comments) {
            this.video_Comments = video_Comments;
        }

        public String getVideo_IsOrigina() {
            return video_IsOrigina;
        }

        public void setVideo_IsOrigina(String video_IsOrigina) {
            this.video_IsOrigina = video_IsOrigina;
        }

        public String getVideo_IsEnable() {
            return video_IsEnable;
        }

        public void setVideo_IsEnable(String video_IsEnable) {
            this.video_IsEnable = video_IsEnable;
        }

    }
}
