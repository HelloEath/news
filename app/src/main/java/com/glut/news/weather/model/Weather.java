package com.glut.news.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yy on 2018/3/31.
 */

public class Weather {
    public String status;
    public Basic basic;
    /* public Aqi aqi;*/
    public Update update;
    public Now now;
    @SerializedName("lifestyle")
    public List<Suggestion> suggestionList;
    @SerializedName("daily_forecast")
    public List<Forecast> forecast;

    public class Basic {
        @SerializedName("parent_city")
        public  String cityName;
        @SerializedName("cid")
        public String weatherId;
        public String location;
    }

    public class Update {
        public String loc;
        public String utc;
    }

    public class Suggestion {

        public String brf;


        public String type;

        public String txt;
    }

    public class Forecast {
        public String date;
        public String tmp_max;
        public String tmp_min;
    }

    public class Now {
        @SerializedName("tmp")//添加注解：json属性映射到实体类属性
        public String temperature;
        public String cond_txt;
    }
}
