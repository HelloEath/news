package com.glut.news.entity;

import java.util.List;

/**
 * Created by yy on 2018/2/9.
 */
public class Comments {
  private String  res;//	0
  private CommentsData data;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public CommentsData getData() {
        return data;
    }

    public void setData(CommentsData data) {
        this.data = data;
    }

    public class CommentsData {
        private String count;
        private List<CommentsDataList> data;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<CommentsDataList> getData() {
            return data;
        }

        public void setData(List<CommentsDataList> data) {
            this.data = data;
        }
    }

    public class CommentsDataList {

        private String   id	;//"45133"
        private String  quote;//	""
        private String  content;//	"2017“活下去”，简单，却隐含千钧之力。"
        private String  praisenum	;//228
        private String   device_token	;//""
        private String  del_flag;//	"0"
        private String  reviewed	;//"1"
        private String user_info_id;//	"10093"
        private String  input_date;//	"2017-01-09 11:02:16"
        private String  created_at	;//"2017-01-09 11:02:16"
        private String  updated_at;//	"2017-01-09 14:32:57"
        private CommentUser user;;//	{…}
        //private String  touser;//	null
        private String type	;//

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPraisenum() {
            return praisenum;
        }

        public void setPraisenum(String praisenum) {
            this.praisenum = praisenum;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(String del_flag) {
            this.del_flag = del_flag;
        }

        public String getReviewed() {
            return reviewed;
        }

        public void setReviewed(String reviewed) {
            this.reviewed = reviewed;
        }

        public String getUser_info_id() {
            return user_info_id;
        }

        public void setUser_info_id(String user_info_id) {
            this.user_info_id = user_info_id;
        }

        public String getInput_date() {
            return input_date;
        }

        public void setInput_date(String input_date) {
            this.input_date = input_date;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

       public CommentUser getUser() {
            return user;
        }

        public void setUser(CommentUser user) {
            this.user = user;
        }
/*
        public String getTouser() {
            return touser;
        }

        public void setTouser(String touser) {
            this.touser = touser;
        }*/

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class CommentUser {
        private String user_id	;//"7477777"
        private String user_name;//	"泰兰德"
        private String  web_url	;//"http://image.wufazhuce.com/placeholder-user_avatar.png?imageView2/1/w/80/h/80/q/75"

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }
}
