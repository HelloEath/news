package com.glut.news.entity;

import java.util.List;

/**
 * Created by yy on 2018/1/24.
 */

public class OneData {
   private String id;//	"4676"

    private String date;//	"2018-02-08 06:00:00"
    private List<OneList> content_list;//	[…]

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OneList> getContent_list() {
        return content_list;
    }

    public void setContent_list(List<OneList> content_list) {
        this.content_list = content_list;
    }

    public class OneList {
       private String id;//	"14545"
        private String category	;//"0"
        private String  display_category;//	6
        private String item_id;//	"1981"
        private String title	;//"摄影"
        private String forward	;//"将威士忌从酒瓶注入玻璃杯时，发出非常舒服的声音。就像亲近的人打开心房时那样的声音。"
        private String img_url	;//"http://image.wufazhuce.com/Fm5sThTD0su4EmMjNLtaWslTQUKe"
        private String like_count	;//1795
        private String post_date	;//"2018-02-08 06:00:00"
        private String last_update_date	;//"2018-02-03 19:25:11"
        private String  author;//	{}
        private String video_url;//	""
        private String audio_url	;//""
        private String audio_platform;//2
        private String start_video	;//""
        private String has_reading;//	0
        private String volume	;//"VOL.1951"
        private String pic_info	;//"Andrei Ianovskii"
        private String words_info	;//"《刺杀骑士团长》"

        private String content_id;//	"1981"
        private String content_type;//	"0"

        private String share_url	;//"http://m.wufazhuce.com/one/1981"
        private String share_info	;//{…}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDisplay_category() {
            return display_category;
        }

        public void setDisplay_category(String display_category) {
            this.display_category = display_category;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getPost_date() {
            return post_date;
        }

        public void setPost_date(String post_date) {
            this.post_date = post_date;
        }

        public String getLast_update_date() {
            return last_update_date;
        }

        public void setLast_update_date(String last_update_date) {
            this.last_update_date = last_update_date;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getAudio_url() {
            return audio_url;
        }

        public void setAudio_url(String audio_url) {
            this.audio_url = audio_url;
        }

        public String getAudio_platform() {
            return audio_platform;
        }

        public void setAudio_platform(String audio_platform) {
            this.audio_platform = audio_platform;
        }

        public String getStart_video() {
            return start_video;
        }

        public void setStart_video(String start_video) {
            this.start_video = start_video;
        }

        public String getHas_reading() {
            return has_reading;
        }

        public void setHas_reading(String has_reading) {
            this.has_reading = has_reading;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getPic_info() {
            return pic_info;
        }

        public void setPic_info(String pic_info) {
            this.pic_info = pic_info;
        }

        public String getWords_info() {
            return words_info;
        }

        public void setWords_info(String words_info) {
            this.words_info = words_info;
        }

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getShare_info() {
            return share_info;
        }

        public void setShare_info(String share_info) {
            this.share_info = share_info;
        }
    }
}
